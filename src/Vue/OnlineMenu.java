package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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


public class OnlineMenu extends JPanel {

    static ArrayList<String[]> parties = new ArrayList<String[]>();

    OnlineMenu(JFrame fenetre, ContinuumGraphique continuumGraphique) {
        super(new BorderLayout());


        try(
        Socket socket = new Socket("alexisetheve.com", 8080);
        InputStream is = socket.getInputStream();
        DataInputStream in = new DataInputStream(is);
        OutputStream os = socket.getOutputStream();
        DataOutputStream out = new DataOutputStream(os);
        )
        {
            System.out.println("Connecté au serveur");
            Message message = new Message();
            message.initDepuisLectureSocket(in);
            MessageHandler(message, in, out);
        } catch (Exception e) {
            System.out.println("Serveur non disponible");
        }

        // Bouton "Créer une partie"
        JButton creerPartieButton = new JButton("Créer une partie");
        creerPartieButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        creerPartieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.add(creerPartieButton);
        buttonsPanel.add(Box.createHorizontalStrut(10));

        buttonsPanel.add(refreshButton);

        add(buttonsPanel, BorderLayout.NORTH);

        // parties.add(new String[] { "Partie 1", "Mot de passe requis" });

        JPanel partiesPanel = new JPanel(new GridLayout(3, 5, 10, 10));
        partiesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < parties.size(); i++) {
            String nomPartie = parties.get(i)[0];
            String infoPartie = parties.get(i)[1];
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
                        }
                    } else {
                        // Rejoindre la partie sans mot de passe
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

        add(partiesPanel, BorderLayout.CENTER);
    }

    public static void MessageHandler(Message message, DataInputStream in, DataOutputStream out) throws IOException {
        
        switch (message.getType()) {
            case "parties":
                System.out.println("Parties disponibles:");
                
                // on deserialize le message:
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

                for (Map.Entry<Integer, Object> entry : PartiesObject.entrySet()) {
                    HashMap<String, Object> partie = (HashMap<String, Object>) entry.getValue();
                    Object hote = partie.get("hote");
                    Object id = partie.get("id");
                    String motDePasseRequis = partie.get("motDePasseRequis").toString();
                    System.out.println("["+id+"] "+hote + " (partie " + (motDePasseRequis == "true" ? "protégée par mot de passe)" : "publique)"));

                    parties.add(new String[] { hote.toString(), motDePasseRequis == "true" ? "Mot de passe requis" : "Pas de mot de passe" });
                }


                break;
            default:
                System.out.println("Message inconnu : " + message);
                break;
        }
    }
}
