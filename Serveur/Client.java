package Serveur;

import java.net.*;

import java.io.*;

public class Client {
    public static void main(String[] args) {
        try (
            Socket ClientSocket = new Socket("localhost", 8080);
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
                    System.out.println(in.readLine() + " (partie " + i + ")");
                    mot_de_passe_requis = Boolean.parseBoolean(in.readLine());
                    if (mot_de_passe_requis) {
                        System.out.println("Partie protégée par mot de passe");
                    } else {
                        System.out.println("Partie publique");
                    }
                }
                choisirMode(in, out, mot_de_passe_requis);
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

    public static Partie creationPartie(BufferedReader in, PrintWriter out) throws IOException {
        BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));
        String nom_partie = clavier.readLine();
        System.out.println("Mot de passe (laissez vide pour une partie publique):");
        String mot_de_passe = clavier.readLine();
        out.println("creationPartie");
        out.println(nom_partie);
        out.println(mot_de_passe);
        Partie partie = new Partie(nom_partie, mot_de_passe);
        return partie;
    }

    public static void sendPartie(Partie partie, PrintWriter out) {
        out.println("newPartie");
        out.println(partie.getHote());
        out.println(partie.getMotDePasse());
    }

    public static void choisirMode(BufferedReader in, PrintWriter out, boolean mot_de_passe_requis) throws IOException {
        BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Choisissez un mode:");
        System.out.println("1. Créer une partie");
        System.out.println("2. Rejoindre une partie");
        int choix = Integer.parseInt(clavier.readLine());
        switch (choix) {
            case Variables.MODE_CREATION:
                System.out.println("Nom de la partie:");
                Partie partie = creationPartie(in,out);
                sendPartie(partie, out);
                break;
            case Variables.MODE_REJOINDRE:
                System.out.println("Choisissez une partie:");
                sendChoixPartie(in, out, mot_de_passe_requis);
                int connexion_partie = Integer.parseInt(in.readLine());
                switch (connexion_partie) {
                    case Variables.CONNEXION_ECHOUEE:
                        System.out.println("Connexion échouée");
                        break;
                    case Variables.CONNEXION_REUSSIE:
                        System.out.println("Connexion réussie");
                        break;
                    case Variables.PARTIE_PLEINE:
                        System.out.println("Partie pleine");
                        break;
                    default:
                        System.out.println("Connexion échouée");
                        break;
                }
                break;
            default:
                System.out.println("Choix invalide");
                choisirMode(in, out, mot_de_passe_requis);
                break;
        }
    }
}
