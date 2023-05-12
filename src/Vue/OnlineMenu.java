package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.List;

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
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                Message message = new Message();
                message.initDepuisLectureSocket(in);

                OnlineMenu.MessageHandler(message, in, out, file);
                OnlineMenu.reafficherParties();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

class ThreadConsommateurMessage implements Runnable {
    private Socket socket = null;
    private static DataOutputStream out = null;
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
                    sendMessage(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(Message message) throws IOException {
        out.writeInt(message.getTaille());
        out.writeUTF(message.getType());
        if (message.getTaille() > 0) {
            out.write(message.getContenu());
        }
    }
}

class ThreadDialogue implements Runnable {
    private Socket socket = null;

    public ThreadDialogue() {
    }

    public void run() {

        FileMessages file_attente = new FileMessages();
        try {
            socket = new Socket("localhost", 8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread t1 = new Thread(new ThreadProducteurMessage(socket, file_attente));
        Thread t2 = new Thread(new ThreadConsommateurMessage(socket, file_attente));
        t1.start();
        t2.start();
    }
}

public class OnlineMenu extends JPanel {

    static ArrayList<String[]> parties = new ArrayList<String[]>();
    static JPanel partiesPanel;
    static JFrame fenetre;
    static DataOutputStream out = null;

    OnlineMenu(JFrame fenetre, ContinuumGraphique continuumGraphique) {
        super(new BorderLayout());
        this.fenetre = fenetre;

        // Bouton "Créer une partie"
        JButton creerPartieButton = new JButton("Créer une partie");
        creerPartieButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        creerPartieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField nomPartieField = new JTextField();
                JPasswordField motDePasseField = new JPasswordField();
                Object[] popupContent = { "Nom de la partie:", nomPartieField, "Mot de passe:", motDePasseField };
                String popupTitle = "Créer une partie";
                int option = JOptionPane.showOptionDialog(
                        fenetre,
                        popupContent,
                        popupTitle,
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        null,
                        null);

                if (option == JOptionPane.OK_OPTION) {

                    HashMap<String, Object> CreerPartie = new HashMap<String, Object>();
                    CreerPartie.put("hote", nomPartieField.getText());
                    CreerPartie.put("motDePasse", motDePasseField.getText());

                    // Serialisation
                    try {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ObjectOutputStream oos = new ObjectOutputStream(baos);
                        oos.writeObject(CreerPartie);
                        oos.close();
                        Message message = new Message();

                        message.initDepuisMessage(baos.size(), "CreerPartie", baos.toByteArray());

                        ThreadConsommateurMessage.sendMessage(message);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                }
            }
        });

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tryLoginServer();
            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.add(creerPartieButton);
        buttonsPanel.add(Box.createHorizontalStrut(10));
        buttonsPanel.add(refreshButton);

        add(buttonsPanel, BorderLayout.NORTH);

        partiesPanel = new JPanel(new GridLayout(3, 5, 10, 10));
        partiesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(partiesPanel, BorderLayout.CENTER);

        tryLoginServer();
    }

    private void tryLoginServer() {
        parties.clear();

        Thread t1 = new Thread(new ThreadDialogue());
        t1.start();
    }

    public static void reafficherParties() {
        partiesPanel.removeAll();

        for (int i = 0; i < parties.size(); i++) {
            String nomPartie = parties.get(i)[0];
            String infoPartie = parties.get(i)[1];
            int idPartie = Integer.parseInt(parties.get(i)[2]);
            JPanel partiePanel = new JPanel();
            partiePanel.setLayout(new BoxLayout(partiePanel, BoxLayout.Y_AXIS));
            partiePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            partiePanel.setPreferredSize(new Dimension(200, 200));
            partiePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            partiePanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (infoPartie.equals("Mot de passe requis")) {
                        JPasswordField passwordField = new JPasswordField();
                        Object[] popupContent = { "Mot de passe:", passwordField };
                        String popupTitle = "Mot de passe requis";
                        int option = JOptionPane.showOptionDialog(
                                fenetre,
                                popupContent,
                                popupTitle,
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                null,
                                null);

                        if (option == JOptionPane.OK_OPTION) {
                            Message message = new Message();
                            HashMap<String, Object> RejoindrePartie = new HashMap<String, Object>();
                            RejoindrePartie.put("id", idPartie);
                            RejoindrePartie.put("motDePasse", passwordField.getText());
                            try {
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                ObjectOutputStream oos = new ObjectOutputStream(baos);
                                oos.writeObject(RejoindrePartie);
                                oos.close();
                                message.initDepuisMessage(baos.size(), "RejoindrePartie", baos.toByteArray());
                                ThreadConsommateurMessage.sendMessage(message);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    } else {
                        Message message = new Message();
                        HashMap<String, Object> RejoindrePartie = new HashMap<String, Object>();
                        RejoindrePartie.put("id", idPartie);
                        RejoindrePartie.put("motDePasse", "");
                        try {
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            ObjectOutputStream oos = new ObjectOutputStream(baos);
                            oos.writeObject(RejoindrePartie);
                            oos.close();
                            message.initDepuisMessage(baos.size(), "RejoindrePartie", baos.toByteArray());
                            ThreadConsommateurMessage.sendMessage(message);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            });

            JLabel nomLabel = new JLabel(nomPartie);
            nomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            partiePanel.add(nomLabel);

            JLabel infoLabel = new JLabel(infoPartie);
            infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            partiePanel.add(infoLabel);

            partiesPanel.add(partiePanel);
        }

        // Rafraîchir l'affichage
        partiesPanel.revalidate();
        partiesPanel.repaint();
    }

    public static void MessageHandler(Message message, DataInputStream in, DataOutputStream out, FileMessages file)
            throws IOException {

        switch (message.getType()) {
            case "parties":
                System.out.println("Parties disponibles:");
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
                parties.clear();
                for (Map.Entry<Integer, Object> entry : PartiesObject.entrySet()) {
                    HashMap<String, Object> partie = (HashMap<String, Object>) entry.getValue();
                    Object hote = partie.get("hote");
                    Object id = partie.get("id");
                    String motDePasseRequis = partie.get("motDePasseRequis").toString();
                    System.out.println("[" + id + "] " + hote + " (partie "
                            + (motDePasseRequis == "true" ? "protégée par mot de passe)" : "publique)"));

                    parties.add(new String[] { hote.toString(),
                            motDePasseRequis == "true" ? "Mot de passe requis" : "Pas de mot de passe",
                            id.toString() });
                }
                break;
            case "reponseRejoindrePartie":
                System.out.println("Rejoindre partie:");

                HashMap<String, Object> ReponseRejoindrePartie = new HashMap<String, Object>();
                byte[] data2 = message.getContenu();
                ByteArrayInputStream inStream2 = new ByteArrayInputStream(data2);
                ObjectInputStream ois2 = new ObjectInputStream(inStream2);
                try {
                    ReponseRejoindrePartie = (HashMap<String, Object>) ois2.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                ois2.close();

                if (ReponseRejoindrePartie.get("error") != null) {
                    JOptionPane.showMessageDialog(fenetre, ReponseRejoindrePartie.get("error"));
                } else {
                    System.out.println("Partie rejointe");
                    // TODO
                }
                break;
            default:
                System.out.println("Message inconnu : " + message);
                break;
        }
    }
}