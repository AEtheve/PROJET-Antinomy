package Serveur;

import java.net.*;

import java.io.*;

public class Client {
    public static void main(String[] args) {
        try (
            Socket ClientSocket = new Socket("localhost", 3000);
            PrintWriter out = new PrintWriter(ClientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));
        ) {
            System.out.println("Connecté au serveur");
            String message = in.readLine();
            MessageHandler(message, in, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void MessageHandler(String message, BufferedReader in, PrintWriter out) throws IOException {
        switch (message) {
            case "parties":
                System.out.println("Parties disponibles:");
                int nbParties = Integer.parseInt(in.readLine());
                boolean mot_de_passe_requis = false;
                for (int i = 0; i < nbParties; i++) {
                    System.out.println(in.readLine() + " (" + i + ")");
                    mot_de_passe_requis = Boolean.parseBoolean(in.readLine());
                    if (mot_de_passe_requis) {
                        System.out.println("Partie protégée par mot de passe");
                    } else {
                        System.out.println("Partie publique");
                    }
                }
                System.out.println("Choisissez une partie:");
                sendChoixPartie(in, out, mot_de_passe_requis);
                int connexion_partie = Integer.parseInt(in.readLine());
                if (connexion_partie == TypeConnexionPartie.PARTIE_PLEINE) {
                    System.out.println("Partie pleine");
                }
                else if (connexion_partie == TypeConnexionPartie.CONNEXION_REUSSIE) {
                    System.out.println("Connexion réussie");
                } else {
                    System.out.println("Connexion échouée");
                }
                break;
            default:
                System.out.println("Message inconnu");
                break;
        }
    }

    public static void sendChoixPartie(BufferedReader in, PrintWriter out, boolean mot_de_passe_requis) throws IOException {
        BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));
        String choix = clavier.readLine();
        if (mot_de_passe_requis) {
            System.out.println("Mot de passe:");
            String mot_de_passe = clavier.readLine();
            out.println("choixPartie");
            out.println(choix);
            out.println(mot_de_passe);
        } else {
            out.println(choix);
        }
    }
    
}
