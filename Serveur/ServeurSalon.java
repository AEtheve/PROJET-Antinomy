package Serveur;

import java.util.*;
import java.net.*;
import java.io.*;
import java.util.concurrent.Semaphore;

class ThreadProducteurMessage implements Runnable {
    private Socket socket = null;
    private FileMessages file = null;
    private Semaphore semaphore = null;

    public ThreadProducteurMessage(Socket socket, FileMessages file, Semaphore semaphore) {
        this.socket = socket;
        this.file = file;
        this.semaphore = semaphore;
    }

    public void run() {
        try {
            while (true) {
                DataInputStream in = new DataInputStream(socket.getInputStream());
                Message message = new Message();
                Boolean ok = message.initDepuisLectureSocket(in);
                if (!ok) {
                    System.out.println("Client déconnecté");
                    Thread.currentThread().interrupt();
                    break;
                }
                ServeurSalon.MessageHandler(message, socket, file);
                semaphore.release();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ThreadConsommateurMessage implements Runnable {
    private Socket socket = null;
    private DataOutputStream out = null;
    private FileMessages file = null;
    private Semaphore semaphore = null;

    public ThreadConsommateurMessage(Socket socket, FileMessages file, Semaphore semaphore) {
        this.socket = socket;
        this.file = file;
        this.semaphore = semaphore;
    }

    public void run() {
        try {
            out = new DataOutputStream(socket.getOutputStream());
            while (true) {
                semaphore.acquire();
                if (!file.fileVide()) {
                    Message message = file.recupererMessage();
                    sendMessage(message, out);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(Message message, DataOutputStream out) throws IOException {
        try {
            out.writeInt(message.getTaille());
            out.writeUTF(message.getType());
            if (message.getTaille() > 0) {
                out.write(message.getContenu());
            }
        } catch (SocketException e) {
            System.out.println("Client déconnecté");
        }
    }
}

class ThreadDialogue implements Runnable {
    private Socket socket = null;
    private FileMessages file_attente = null;
    private Semaphore semaphore = null;

    public ThreadDialogue(Socket socket, FileMessages file_attente) {
        this.socket = socket;
        this.file_attente = file_attente;
        this.semaphore = new Semaphore(0);
    }

    public void run() {
        Thread t1 = new Thread(new ThreadProducteurMessage(socket, file_attente, semaphore));
        Thread t2 = new Thread(new ThreadConsommateurMessage(socket, file_attente, semaphore));
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void release() {
        semaphore.release();
    }

    public void postMessage(Message message) {
        file_attente.ajouterMessage(message);
        semaphore.release();
    }
}

class ServeurSalon {
    static Parties parties = new Parties();
    static HashMap<Socket, ThreadDialogue> clients = new HashMap<Socket, ThreadDialogue>();

    public static void main(String[] args) {
        Partie partieTEST1 = new Partie(0, "Theodora", "pass");
        Partie partieTEST2 = new Partie(1, "Alexis", "");
        partieTEST2.setServeurCentral(new ServeurCentral());

        parties.ajouterPartie(partieTEST1);
        parties.ajouterPartie(partieTEST2);

        int port = 8080;

        try (ServerSocket ServeurSocket = new ServerSocket(port)) {
            System.out.println("Serveur lancé sur le port " + port);

            while (true) {
                Socket socket = ServeurSocket.accept();
                System.out.println("Un client s'est connecté");

                FileMessages file_attente = new FileMessages();
                ThreadDialogue tDialogue = new ThreadDialogue(socket, file_attente);
                Thread t1 = new Thread(tDialogue);
                t1.start();

                clients.put(socket, tDialogue);

                Message message = new Message();

                HashMap<Integer, Object> PartiesObject = new HashMap<Integer, Object>();
                for (int i = 0; i < parties.getNbParties(); i++) {
                    HashMap<String, Object> partie = new HashMap<String, Object>();
                    partie.put("id", parties.getPartie(i).getId());
                    partie.put("hote", parties.getPartie(i).getHote());
                    partie.put("motDePasseRequis", parties.getPartie(i).MotDePasseRequis());
                    PartiesObject.put(i, partie);
                }

                message.initDepuisMessage("parties", Message.Serialization(PartiesObject));
                file_attente.ajouterMessage(message);
                tDialogue.release();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void MessageHandler(Message message, Socket socket, FileMessages file)
            throws IOException {

        switch (message.getType()) {
            case "CreerPartie":
                System.out.println("Demande de création de partie");
                try {
                    HashMap<Integer, Object> PartiesObject = (HashMap<Integer, Object>) Message
                            .Deserialization(message.getContenu());

                    String hote = (String) PartiesObject.get("hote");
                    String motDePasse = (String) PartiesObject.get("motDePasse");
                    System.out.println("Hote : " + hote);
                    System.out.println("Mot de passe : " + motDePasse);

                    Partie partie = new Partie(parties.getNbParties(), hote, motDePasse);
                    parties.ajouterPartie(partie);

                    HashMap<Integer, Object> PartiesObject2 = new HashMap<Integer, Object>();
                    for (int i = 0; i < parties.getNbParties(); i++) {
                        HashMap<String, Object> partie2 = new HashMap<String, Object>();
                        partie2.put("id", parties.getPartie(i).getId());
                        partie2.put("hote", parties.getPartie(i).getHote());
                        partie2.put("motDePasseRequis", parties.getPartie(i).MotDePasseRequis());
                        PartiesObject2.put(i, partie2);
                    }

                    Message message2 = new Message();
                    message2.initDepuisMessage("parties", Message.Serialization(PartiesObject2));
                    file.ajouterMessage(message2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "RejoindrePartie":
                System.out.println("Demande de rejoindre une partie");
                try {

                    HashMap<Integer, Object> PartiesObject3 = (HashMap<Integer, Object>) Message
                            .Deserialization(message.getContenu());

                    int id = (int) PartiesObject3.get("id");
                    String motDePasse2 = (String) PartiesObject3.get("motDePasse");

                    HashMap<String, Object> rep = new HashMap<String, Object>();
                    if (parties.getPartie(id).MotDePasseRequis()) {
                        if (parties.getPartie(id).getMotDePasse().equals(motDePasse2)) {
                            rep.put("id", id);
                        } else {
                            rep.put("error", "Mot de passe incorrect");
                        }
                    } else {
                        rep.put("id", id);
                    }

                    parties.rejoindrePartie(id, clients.get(socket));

                    Message message3 = new Message();
                    message3.initDepuisMessage("reponseRejoindrePartie", Message.Serialization(rep));

                    file.ajouterMessage(message3);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            default:
                System.out.println("Message inconnu : " + message.getType());
                break;
        }
    }

}