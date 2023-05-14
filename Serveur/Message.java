package Serveur;

import java.io.*;
import java.util.HashMap;

public class Message {
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

    public Boolean initDepuisLectureSocket(DataInputStream in) throws IOException {
        while (true) {
            try {
                taille = in.readInt();
                break;
            } catch (EOFException e) {
                return false;
            }
        }
        type = in.readUTF();
        if (taille > 0) {
            contenu = new byte[taille];
            in.readFully(contenu);
        }
        return true;
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

    public static ByteArrayOutputStream Serialization(HashMap<?, ?> object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        oos.close();
        return baos;
    }

    public static HashMap<?, ?> Deserialization(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        HashMap<?, ?> object = (HashMap<?, ?>) ois.readObject();
        ois.close();
        return object;
    }
}