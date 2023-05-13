package Serveur;

import java.util.*;
import java.net.*;
import java.io.*;

class ThreadProducteurMessage implements Runnable {
    private Socket socket = null;
    private FileMessages file = null;

    public ThreadProducteurMessage(Socket socket, FileMessages file) {
        this.socket = socket;
        this.file = file;
    }

    public void run() {
        try {
            while (true) {
                DataInputStream in = new DataInputStream(socket.getInputStream());
                Message message = new Message();
                message.initDepuisLectureSocket(in);
                ServeurSalon.MessageHandler(message, in, file);
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

    public ThreadConsommateurMessage(Socket socket, FileMessages file) {
        this.socket = socket;
        this.file = file;
    }

    public void run() {
        try {
            out = new DataOutputStream(socket.getOutputStream());
            while (true) {
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

    public ThreadDialogue(Socket socket, FileMessages file_attente) {
        this.socket = socket;
        this.file_attente = file_attente;
    }

    public void run() {
        Thread t1 = new Thread(new ThreadProducteurMessage(socket, file_attente));
        Thread t2 = new Thread(new ThreadConsommateurMessage(socket, file_attente));
        t1.start();
        t2.start();

    }
}

class ServeurSalon {
    static Parties parties = new Parties();

    public static void main(String[] args) {
        Partie partieTEST1 = new Partie(0, "Theodora", "pass");
        Partie partieTEST2 = new Partie(1, "Alexis", "");

        parties.ajouterPartie(partieTEST1);
        parties.ajouterPartie(partieTEST2);

        int port = 8080;

        try (ServerSocket ServeurSocket = new ServerSocket(port)) {
            System.out.println("Serveur lancé sur le port " + port);

            while (true) {
                Socket socket = ServeurSocket.accept();
                System.out.println("Un client s'est connecté");

                FileMessages file_attente = new FileMessages();
                Thread t1 = new Thread(new ThreadDialogue(socket, file_attente));
                t1.start();

                Message message = new Message();

                HashMap<Integer, Object> PartiesObject = new HashMap<Integer, Object>();
                for (int i = 0; i < parties.getNbParties(); i++) {
                    HashMap<String, Object> partie = new HashMap<String, Object>();
                    partie.put("id", parties.getPartie(i).getId());
                    partie.put("hote", parties.getPartie(i).getHote());
                    partie.put("motDePasseRequis", parties.getPartie(i).MotDePasseRequis());
                    PartiesObject.put(i, partie);
                }

                message.initDepuisMessage("parties", Serialization(PartiesObject));
                file_attente.ajouterMessage(message);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ByteArrayOutputStream Serialization(HashMap<?, ?> PartiesObject) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(PartiesObject);
        oos.close();
        return baos;
    }

    public static void MessageHandler(Message message, DataInputStream in, FileMessages file)
            throws IOException {

        switch (message.getType()) {
            case "CreerPartie":
                System.out.println("Demande de création de partie");

                HashMap<Integer, Object> PartiesObject = new HashMap<Integer, Object>();
                byte[] data = message.getContenu();
                ByteArrayInputStream inStream = new ByteArrayInputStream(data);
                ObjectInputStream ois = new ObjectInputStream(inStream);
                try {
                    PartiesObject = (HashMap<Integer, Object>) ois.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                ois.close();

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
                message2.initDepuisMessage("parties", Serialization(PartiesObject2));
                file.ajouterMessage(message2);
                break;

            case "RejoindrePartie":
                System.out.println("Demande de rejoindre une partie");

                HashMap<Integer, Object> PartiesObject3 = new HashMap<Integer, Object>();
                byte[] data2 = message.getContenu();
                ByteArrayInputStream inStream2 = new ByteArrayInputStream(data2);
                ObjectInputStream ois2 = new ObjectInputStream(inStream2);
                try {
                    PartiesObject3 = (HashMap<Integer, Object>) ois2.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                ois2.close();

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

                Message message3 = new Message();
                message3.initDepuisMessage("reponseRejoindrePartie", Serialization(rep));

                file.ajouterMessage(message3);

                break;

            default:
                System.out.println("Message inconnu : " + message.getType());
                break;
        }
    }

}