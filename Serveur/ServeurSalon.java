package Serveur;

import java.net.*;
import java.io.*;


class ClientHandler implements Runnable {
    final int PARTIE_PLEINE = 2;
    final int CONNEXION_REUSSIE = 1;
    final int CONNEXION_ECHOUEE = 0;
    

    private Socket clientSocket;
    private Parties parties;
    
    public ClientHandler(Socket socket, Parties parties) {
        this.clientSocket = socket;
        this.parties = parties;
    }

    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        ) {
            System.out.println("Client connecté");
            sendParties(out, parties);
            String message = in.readLine();
            MessageHandler(message, in, out);
            clientSocket.close();
            System.out.println("Client déconnecté");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendParties(PrintWriter out, Parties parties) {
        out.println("parties");
        int nbParties = parties.getNbParties();
        out.println(nbParties);
        for (int i = 0; i < nbParties; i++) {
            if (!parties.getPartie(i).estVisible()) {
                continue;
            }
            out.println(parties.getPartie(i).getHote());
            if (parties.getPartie(i).getMotDePasse()!= null) {
                out.println("true");
            } else {
                out.println("false");
            }
        }
    }

    public void MessageHandler(String message, BufferedReader in, PrintWriter out) throws IOException {
        switch (message) {
            case "choixPartie":
            {
                System.out.println("Choix de partie");
                String choix = in.readLine();
                System.out.println("Partie: " + choix);
                Partie partie = parties.getPartie(Integer.parseInt(choix));
                int connexion_reussie = CONNEXION_ECHOUEE;
                if (partie.getMotDePasse()!= null) {
                    System.out.println("Mot de passe requis");
                    String mot_de_passe = in.readLine();
                    if (!partie.estVisible()){
                        System.out.println("Partie pleine");
                        out.println(PARTIE_PLEINE);
                        return;
                    }
                    else if (mot_de_passe.equals(partie.getMotDePasse())) {
                        System.out.println("Mot de passe correct");
                        connexion_reussie = CONNEXION_REUSSIE;
                        partie.setPasVisible();
                        out.println(connexion_reussie);
                    } else {
                        System.out.println("Mot de passe incorrect");
                        out.println(connexion_reussie);
                    }
                } else {
                    System.out.println("Pas de mot de passe");
                }
                break;
            }
            case "creationPartie":
            {
                System.out.println("Création de partie");
                String hote = in.readLine();
                String mot_de_passe = in.readLine();
                Partie partie = new Partie(hote, mot_de_passe);
                parties.ajouterPartie(partie);
                break;
            }
            default:
                System.out.println("Message inconnu");
                break;
        }
    }
}

class ServeurSalon {
    static Partie[] parties;
    public static void main(String[] args) {
        Partie partieTEST1 = new Partie("Theodora", "pass");
        Partie partieTEST2 = new Partie("Alexis", "pass2");

        Parties parties = new Parties();
        // parties[nbParties++] = partieTEST1
        // parties[nbParties++] = partieTEST2;
        parties.ajouterPartie(partieTEST1);
        parties.ajouterPartie(partieTEST2);
        
        int port = 8080;
        try (ServerSocket ServeurSocket = new ServerSocket(port)) {
            System.out.println("Serveur lancé sur le port " + port);
            while (true) {
                Socket clientSocket = ServeurSocket.accept();
                Thread t = new Thread(new ClientHandler(clientSocket, parties));
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}