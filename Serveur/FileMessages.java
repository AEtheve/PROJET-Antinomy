package Serveur;

import java.util.*;

public class FileMessages {
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
