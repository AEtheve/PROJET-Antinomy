package Serveur;

import java.util.*;
import java.net.*;
import java.io.*;

class Message {
    private int taille;
    String type;

    private byte contenu[];

    public Message() {
    }

    public int getTaille() {
        if (contenu != null) {
            return contenu.length;
        } else {
            return 0;
        }
    }

    public String getType() {
        return type;
    }

    public byte[] getContenu() {
        return contenu;
    }

    public void initDepuisLectureSocket(DataInputStream in) throws IOException {
        // System.out.println("initDepuisLectureSocket");
        while (true) {
            try {
                taille = in.readInt();
                break;
            } catch (EOFException e) {
            }
        }
        type = in.readUTF();
        if (taille > 0) {
            contenu = new byte[taille];
            in.readFully(contenu);
        }
    }

    public void initDepuisMessage(int taille, String type, byte[] contenu) {
        this.taille = taille;
        this.type = type;
        this.contenu = contenu;
    }

    public void initDepuisMessage(String type) {
        this.taille = 0;
        this.type = type;
    }
}

class FileMessages {
    private List<Message> file = Collections.synchronizedList(new LinkedList<Message>());

    public void ajouterMessage(Message message) {
        file.add(message);
    }

    public Message recupererMessage() {
        Message message = file.get(0);
        file.remove(0);
        return message;
    }

    public boolean fileVide() {
        return file.isEmpty();
    }
}

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
                file.ajouterMessage(message);
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
        out.writeInt(message.getTaille());
        out.writeUTF(message.getType());
        if (message.getTaille() > 0) {
            out.write(message.getContenu());
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

                // Serialisation
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(PartiesObject);
                oos.close();

                message.initDepuisMessage(baos.size(), "parties", baos.toByteArray());
                file_attente.ajouterMessage(message);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}