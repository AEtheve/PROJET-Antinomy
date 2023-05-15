package Serveur;

import java.util.HashMap;

import Controleur.ControleurMediateur;
import Global.Configuration;
import Modele.Carte;
import Modele.Coup;
import Modele.Jeu;

public class ServeurCentral {
    ThreadDialogue J1Thread, J2Thread;

    Jeu j;
    Carte carteAJouer;
    Carte[] cartesPossibles;

    int state = ControleurMediateur.ONLINEWAITPLAYERS;
    public ServeurCentral() {
        j = new Jeu();
        System.out.println("Serveur central lancé");
    }

    boolean jeu(int index, int state, String type, Boolean joueur) {
        if (joueur != j.getTour()) {
            return false;
        }
        if (state == ControleurMediateur.STARTGAME || state == ControleurMediateur.WAITSCEPTRE) {
            Configuration.info("Pose du sceptre pour le joueur " + joueur);
            Coup coup = new Coup(Coup.SCEPTRE, index);
            j.joue(coup);
            return true;
        }
        if (type == "Main" && (state == ControleurMediateur.WAITSELECT || state == ControleurMediateur.WAITMOVE)) {
            Configuration.info("Clic une carte de sa main ");
            carteAJouer = j.getMain(j.getTour())[index];
            cartesPossibles = j.getCartesPossibles(carteAJouer);
            return true;
        }
        if (type == "Continuum" && (state == ControleurMediateur.WAITMOVE) && cartesPossibles != null) {
            Configuration.info("Clic une carte du continuum");
            Carte cartePlateau = j.getDeck().getContinuum()[index];
            if (cartePlateau == null) {
                throw new IllegalArgumentException("Carte inexistante");
            }
            for (Carte carte : cartesPossibles) {
                if (cartePlateau == carte) {
                    Configuration.info("Echange");
                    Coup c = new Coup(Coup.ECHANGE, carteAJouer.getIndex(), cartePlateau.getIndex());
                    j.joue(c);
                    carteAJouer = null;
                    cartesPossibles = null;
                    return true;
                }
            }
        }
        if (type == "Continuum" && (state == ControleurMediateur.WAITSWAP)) {
            Configuration.info("Clic pour un swap");
            Coup c;
            if (index < j.getDeck().getSceptre(j.getTour()) && index >= 0) {
                Configuration.info("Swap gauche");
                c = new Coup(Coup.SWAP_GAUCHE);
            } else if (index > j.getDeck().getSceptre(j.getTour()) && index <= 15) {
                Configuration.info("Swap droit");
                c = new Coup(Coup.SWAP_DROIT);
            } else {
                throw new IllegalArgumentException("Position du swap invalide");
            }
            j.joue(c);
            return true;
        }
        return false;
    }

    public void changeState() {
        switch (state) {
            case ControleurMediateur.ONLINEWAITPLAYERS:
                state = ControleurMediateur.WAITSCEPTRE;
                break;
            case ControleurMediateur.WAITSCEPTRE:
                if (j.getTour() == Jeu.JOUEUR_1) {
                    state = ControleurMediateur.WAITSELECT;
                }
                break;
            default:
                break;
        }

        try {
            Message message = new Message();
            HashMap<String, Object> stateObject = new HashMap<String, Object>();
            stateObject.put("State", state);
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
                Message message = new Message();
                message.initDepuisMessage("Jeu", Message.Serialization(J2JeuObject()));
                J2Thread.postMessage(message);

                changeState();
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
        JeuObject.put("Deck", j.getDeck());
        JeuObject.put("Main1", j.getMain(Jeu.JOUEUR_1));
        JeuObject.put("Main2", j.getMain(Jeu.JOUEUR_2));
        JeuObject.put("Tour", j.getTour());
        JeuObject.put("Joueur", Jeu.JOUEUR_1);
        return JeuObject;
    }

    private HashMap<String, Object> J2JeuObject() {
        HashMap<String, Object> JeuObject = new HashMap<String, Object>();
        JeuObject.put("Deck", j.getDeck());
        JeuObject.put("Main1", j.getMain(Jeu.JOUEUR_2));
        JeuObject.put("Main2", j.getMain(Jeu.JOUEUR_1));
        JeuObject.put("Tour", j.getTour());
        JeuObject.put("Joueur", Jeu.JOUEUR_2);
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

            if (jeu(index, state, type, J1Thread == thread)) {
                changeState();
                try {
                    Message message = new Message();
                    message.initDepuisMessage("Jeu", Message.Serialization(J1JeuObject()));
                    J1Thread.postMessage(message);

                    message = new Message();

                    message.initDepuisMessage("Jeu", Message.Serialization(J2JeuObject()));
                    J2Thread.postMessage(message);

                    message = new Message();
                    HashMap<String, Object> stateObject = new HashMap<String, Object>();
                    stateObject.put("State", state);
                    stateObject.put("CartesPossibles", cartesPossibles);
                    message.initDepuisMessage("newState", Message.Serialization(stateObject));

                    J1Thread.postMessage(message);
                    J2Thread.postMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}