package Serveur;

import java.net.*;
import java.io.*;


class ClientHandler implements Runnable {
    final int PARTIE_PLEINE = 2;
    final int CONNEXION_REUSSIE = 1;
    final int CONNEXION_ECHOUEE = 0;
    

    private Socket clientSocket;
    private Partie[] parties;
    
    public ClientHandler(Socket socket, Partie[] parties) {
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

    public void sendParties(PrintWriter out, Partie[] parties) {
        out.println("parties");
        out.println(parties.length);
        for (int i = 0; i < parties.length; i++) {
            if (!parties[i].estVisible()) {
                continue;
            }
            out.println(parties[i].hote);
            if (parties[i].mot_de_passe != null) {
                out.println("true");
            } else {
                out.println("false");
            }
        }
    }

    public void MessageHandler(String message, BufferedReader in, PrintWriter out) throws IOException {
        switch (message) {
            case "choixPartie":
                System.out.println("Choix de partie");
                String choix = in.readLine();
                System.out.println("Partie: " + choix);
                Partie partie = parties[Integer.parseInt(choix)];
                int connexion_reussie = CONNEXION_ECHOUEE;
                if (partie.mot_de_passe != null) {
                    System.out.println("Mot de passe requis");
                    String mot_de_passe = in.readLine();
                    if (!partie.estVisible()){
                        System.out.println("Partie pleine");
                        out.println(PARTIE_PLEINE);
                        return;
                    }
                    else if (mot_de_passe.equals(partie.mot_de_passe)) {
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
            default:
                System.out.println("Message inconnu");
                break;
        }
    }
}

class Serveur {
    static Partie[] parties;
    public static void main(String[] args) {
        Partie partieTEST1 = new Partie("Theodora", "pass");
        Partie partieTEST2 = new Partie("Alexis", "pass2");

        parties = new Partie[2];
        parties[0] = partieTEST1;
        parties[1] = partieTEST2;
        
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