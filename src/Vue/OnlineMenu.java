package Vue;

import javax.swing.*;

import Modele.Carte;
import Modele.Deck;
import Serveur.FileMessages;
import Serveur.Message;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.*;
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
        if (socket == null) {
            OnlineMenu.connected = false;
            OnlineMenu.reafficherParties();
            return;
        }
        try {

            while (true) {
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                Message message = new Message();
                Boolean ok = message.initDepuisLectureSocket(in);
                if (!ok) {
                    System.out.println("Client déconnecté");
                    Thread.currentThread().interrupt();
                    break;
                }
                OnlineMenu.MessageHandler(message, in, out, file);
                semaphore.release();
            }

        } catch (Exception e) {
        }
    }

}

class ThreadConsommateurMessage implements Runnable {
    private Socket socket = null;
    private static DataOutputStream out = null;
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
    private Semaphore semaphore = null;
    private FileMessages file_attente = null;

    public ThreadDialogue() {
        this.semaphore = new Semaphore(0);
    }

    public void run() {
        file_attente = new FileMessages();
        try {
            socket = new Socket("localhost", 8080);
            OnlineMenu.connected = true;
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

        } catch (IOException e) {
        }
    }

    public void release() {
        semaphore.release();
    }

    public void closeSocket() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
        }
    }

    public void postMessage(Message message) {
        file_attente.ajouterMessage(message);
        semaphore.release();
    }

}

public class OnlineMenu extends JPanel {

    static ArrayList<String[]> parties = new ArrayList<String[]>();
    static JPanel partiesPanel;
    static JFrame fenetre;
    static DataOutputStream out = null;
    static ContinuumGraphique continuumGraphique;
    static InterfaceGraphique vue;
    static boolean connected = false;
    static ThreadDialogue threadReseau;

    OnlineMenu(JFrame fenetre, InterfaceGraphique vue, ContinuumGraphique continuumGraphique) {
        super(new BorderLayout());
        OnlineMenu.fenetre = fenetre;
        OnlineMenu.continuumGraphique = continuumGraphique;
        OnlineMenu.vue = vue;
        
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

                    try {
                        Message message = new Message();
                        message.initDepuisMessage("CreerPartie", Message.Serialization(CreerPartie));

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
        if (threadReseau != null) {
            threadReseau.closeSocket();
        }

        threadReseau = new ThreadDialogue();
        Thread t1 = new Thread(threadReseau);
        t1.start();
        reafficherParties();
    }

    public static void reafficherParties() {
        partiesPanel.removeAll();

        if (!connected) {
            JPanel partiePanel = new JPanel();
            partiePanel.setLayout(new BoxLayout(partiePanel, BoxLayout.Y_AXIS));
            partiePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            partiePanel.setPreferredSize(new Dimension(200, 200));
            partiePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel nomLabel = new JLabel("Serveur hors ligne");
            nomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            partiePanel.add(nomLabel);
            partiesPanel.add(partiePanel);
        }

        for (int i = 0; i < parties.size(); i++) {
            String nomPartie = parties.get(i)[0];
            String infoPartie = parties.get(i)[1];
            int idPartie = Integer.parseInt(parties.get(i)[2]);
            JPanel partiePanel = new JPanel();
            partiePanel.setLayout(new BoxLayout(partiePanel, BoxLayout.Y_AXIS));
            partiePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            partiePanel.setPreferredSize(new Dimension(200, 200));
            partiePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            partiePanel.setBackground(Color.WHITE);
            partiePanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
                                message.initDepuisMessage("RejoindrePartie", Message.Serialization(RejoindrePartie));
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
                            message.initDepuisMessage("RejoindrePartie", Message.Serialization(RejoindrePartie));
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
                reafficherParties();
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
                }
                break;

            case "Jeu":
                HashMap<String, Object> JeuObject = new HashMap<String, Object>();
                byte[] data3 = message.getContenu();
                ByteArrayInputStream inStream3 = new ByteArrayInputStream(data3);
                ObjectInputStream ois3 = new ObjectInputStream(inStream3);
                try {
                    JeuObject = (HashMap<String, Object>) ois3.readObject();

                    Deck deck = (Deck) JeuObject.get("Deck");
                    Carte[] main1 = (Carte[]) JeuObject.get("Main1");
                    Carte[] main2 = (Carte[]) JeuObject.get("Main2");
                    Boolean tour = (Boolean) JeuObject.get("Tour");

                    Boolean joueur = (Boolean) JeuObject.get("Joueur");

                    vue.ctrl.setDeck(deck);
                    vue.ctrl.setMainJ1(main1);
                    vue.ctrl.setMainJ2(main2);
                    vue.ctrl.setTour(tour);

                    vue.continuumGraphique = new ContinuumGraphique(vue.ctrl, vue.imagesCache);
                    vue.ctrl.ajouteInterfaceUtilisateur(vue);
                    
                    continuumGraphique = vue.continuumGraphique;
                    continuumGraphique.initParams(main1, main2, deck, tour, joueur);
                    continuumGraphique.initializeComponents();
                    continuumGraphique.miseAJour();

                    JPanel PlayMenu = new JPanel();
                    PlayMenu.setLayout(new BoxLayout(PlayMenu, BoxLayout.Y_AXIS));
                    PlayMenu.add(continuumGraphique);
                    fenetre.setContentPane(PlayMenu);
                    fenetre.revalidate();

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;

            case "newState":
                try {
                    HashMap<String, Object> newState = (HashMap<String, Object>) Message
                            .Deserialization(message.getContenu());

                    int state = (int) newState.get("State");
                    vue.ctrl.changeState(state);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            default:
                System.out.println("Message inconnu : " + message.getType());
                break;
        }
    }

    public static void sendMessage(Message message) {
        threadReseau.postMessage(message);
    }
}