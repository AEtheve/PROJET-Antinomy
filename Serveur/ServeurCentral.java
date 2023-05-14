package Serveur;

import java.util.HashMap;

import Modele.Jeu;

public class ServeurCentral {
    Jeu jeu;
    ThreadDialogue J1Thread, J2Thread;

    public ServeurCentral() {
        jeu = new Jeu();
        System.out.println("Serveur central lancé");
    }

    public void setJ1Thread(ThreadDialogue J1Thread) {
        this.J1Thread = J1Thread;
    }

    public void setJ2Thread(ThreadDialogue J2Thread) {
        this.J2Thread = J2Thread;
    }

    public void rejoindrePartie(ThreadDialogue thread) {
        if (J1Thread == null) {
            J1Thread = thread;

            HashMap<String, Object> JeuObject = new HashMap<String, Object>();
            JeuObject.put("Deck", jeu.getDeck());
            JeuObject.put("Main1", jeu.getMain(Jeu.JOUEUR_1));
            JeuObject.put("Main2", jeu.getMain(Jeu.JOUEUR_2));
            JeuObject.put("Tour", jeu.getTour());
            JeuObject.put("Joueur", Jeu.JOUEUR_1);

            try {
                Message message = new Message();
                message.initDepuisMessage("Jeu", Message.Serialization(JeuObject));
                J1Thread.postMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (J2Thread == null) {
            J2Thread = thread;
            HashMap<String, Object> JeuObject = new HashMap<String, Object>();
            JeuObject.put("Deck", jeu.getDeck());
            JeuObject.put("Main1", jeu.getMain(Jeu.JOUEUR_2));
            JeuObject.put("Main2", jeu.getMain(Jeu.JOUEUR_1));
            JeuObject.put("Tour", jeu.getTour());
            JeuObject.put("Joueur", Jeu.JOUEUR_2);

            try {
                Message message = new Message();
                message.initDepuisMessage("Jeu", Message.Serialization(JeuObject));
                J2Thread.postMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Partie pleine");
            return;
        }
    }

    public void clicSouris(int index, String type, ThreadDialogue thread) {
        if (J1Thread != null && J2Thread != null) {
            if (J1Thread == thread) {
                System.out.println("J1 a cliqué sur " + index + " " + type);
            }
            if (J2Thread == thread) {
                System.out.println("J2 a cliqué sur " + index + " " + type);
            }
        }
    }
}