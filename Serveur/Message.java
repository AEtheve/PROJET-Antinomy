package Serveur;

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

    public void initDepuisMessage(String type, ByteArrayOutputStream out) {
        this.taille = out.size();
        this.type = type;
        this.contenu = out.toByteArray();
    }

    public void initDepuisMessage(String type) {
        this.taille = 0;
        this.type = type;
    }
}