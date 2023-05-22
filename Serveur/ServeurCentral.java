import java.util.HashMap;

import Controleur.ControleurMediateurLocal;
import Modele.Jeu;

public class ServeurCentral {
    ThreadDialogue J1Thread, J2Thread;
    private static ControleurMediateurLocal ctrl;

    public ServeurCentral() {
        ctrl = new ControleurMediateurLocal();
        System.out.println("Serveur central lancé");
    }

    public void sendState() {
        try {
            Message message = new Message();
            message.initDepuisMessage("Jeu", Message.Serialization(J1JeuObject()));
            J1Thread.postMessage(message);

            message = new Message();
            message.initDepuisMessage("Jeu", Message.Serialization(J2JeuObject()));
            J2Thread.postMessage(message);

            message = new Message();
            HashMap<String, Object> stateObject = new HashMap<String, Object>();
            stateObject.put("State", ctrl.getState());
            message.initDepuisMessage("newState", Message.Serialization(stateObject));

            J1Thread.postMessage(message);
            J2Thread.postMessage(message);

            
        } catch (Exception e) {
        }

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

            try {
                Message message = new Message();
                message.initDepuisMessage("Jeu", Message.Serialization(J1JeuObject()));
                J1Thread.postMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (J2Thread == null) {
            J2Thread = thread;

            try {
                sendState();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Partie pleine");
            return;
        }
    }

    private HashMap<String, Object> J1JeuObject() {
        HashMap<String, Object> JeuObject = new HashMap<String, Object>();
        JeuObject.put("Deck", ctrl.getInterfaceDeck());
        JeuObject.put("Main1", ctrl.getInterfaceMain(Jeu.JOUEUR_1));
        JeuObject.put("Main2", ctrl.getInterfaceMain(Jeu.JOUEUR_2));
        JeuObject.put("Tour", ctrl.getInterfaceTour());
        JeuObject.put("Joueur", Jeu.JOUEUR_1);
        if (ctrl.getInterfaceTour() == Jeu.JOUEUR_1) {
            JeuObject.put("CartesPossibles", ctrl.getCartesPossibles());
        } else {
            JeuObject.put("CartesPossibles", null);
        }
        return JeuObject;
    }

    private HashMap<String, Object> J2JeuObject() {
        HashMap<String, Object> JeuObject = new HashMap<String, Object>();
        JeuObject.put("Deck", ctrl.getInterfaceDeck());
        JeuObject.put("Main1", ctrl.getInterfaceMain(Jeu.JOUEUR_2));
        JeuObject.put("Main2", ctrl.getInterfaceMain(Jeu.JOUEUR_1));
        JeuObject.put("Tour", ctrl.getInterfaceTour());
        JeuObject.put("Joueur", Jeu.JOUEUR_2);
        if (ctrl.getInterfaceTour() == Jeu.JOUEUR_2) {
            JeuObject.put("CartesPossibles", ctrl.getCartesPossibles());
        } else {
            JeuObject.put("CartesPossibles", null);
        }
        return JeuObject;
    }

    public void clicSouris(int index, String type, ThreadDialogue thread) {
        if (J1Thread != null && J2Thread != null) {
            if (J1Thread == thread) {
                System.out.println("J1 a cliqué sur " + index + " " + type);
            }
            if (J2Thread == thread) {
                System.out.println("J2 a cliqué sur " + index + " " + type);
            }
            ctrl.clicSouris(index, type);
            sendState();
        }
    }
}