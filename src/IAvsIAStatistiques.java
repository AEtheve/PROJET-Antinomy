import java.io.FileWriter;

import Controleur.ControleurMediateurLocal;
import Global.Configuration;
import Modele.Compteur;
import Modele.Jeu;

public class IAvsIAStatistiques {
    private static ControleurMediateurLocal ctrl;

    public static void main(String[] args) {

        // statistiques(10, IAvsIAStatistiques::initIARandomVSIARandom, "IA RANDOM", "IA RANDOM", true);
        // statistiques(1000, IAvsIAStatistiques::IARandomVSIAMinMax, "IA RANDOM", "IA
        // MINMAX");
        // statistiques(100, IAvsIAStatistiques::IAMinMaxVSRandom, "IA MINMAX", "IA
        // RANDOM", false);
        // statistiques(100, IAvsIAStatistiques::IAMinMax2vsIAMinMax5, "IA MINMAX 2",
        // "IA MINMAX 5", true);
        statistiques(100, IAvsIAStatistiques::IAMinMax3vsIAMinMax3, "IA MINMAX 7", "IA MINMAX 7", true);
    }

    private static void statistiques(int nbParties, Runnable init, String J1, String J2, boolean debug) {
        init.run();
        int nbPartiesJ1Gagne = 0;
        int nbPartiesJ2Gagne = 0;

        try {
            FileWriter log = new FileWriter(J1 + "VS" + J2 + ".txt");
            while (true) {
                ctrl.tictac();
                if (Compteur.getInstance().getJ1Points() == 5 || Compteur.getInstance().getJ2Points() == 5) {
                    if (Compteur.getInstance().getJ1Points() == 5) {
                        nbPartiesJ1Gagne++;

                    } else {
                        nbPartiesJ2Gagne++;
                    }
                    if (debug && (nbPartiesJ1Gagne + nbPartiesJ2Gagne) % 1 == 0) {
                        System.out.println("Statistiques sur " + (nbPartiesJ1Gagne + nbPartiesJ2Gagne) + " parties "
                                + J1 + " VS " + J2);
                        System.out.println(J1 + " a gagné " + nbPartiesJ1Gagne + " parties ("
                                + (nbPartiesJ1Gagne * 100 / (nbPartiesJ1Gagne + nbPartiesJ2Gagne)) + "%)");
                        System.out.println(J2 + " a gagné " + nbPartiesJ2Gagne + " parties ("
                                + (nbPartiesJ2Gagne * 100 / (nbPartiesJ1Gagne + nbPartiesJ2Gagne)) + "%)");
                        
                        log.write((double) nbPartiesJ1Gagne / (nbPartiesJ1Gagne + nbPartiesJ2Gagne) + "\n");
                    }
                    if (nbPartiesJ1Gagne + nbPartiesJ2Gagne == nbParties) {
                        break;
                    }
                    init.run();
                }
            }
            System.out.println("Statistiques sur " + nbParties + " parties " + J1 + " VS " + J2);
            System.out.println(J1 + " a gagné " + nbPartiesJ1Gagne + " parties ("
                    + (float) (nbPartiesJ1Gagne * 100 / nbParties) + "%)");
            System.out.println(J2 + " a gagné " + nbPartiesJ2Gagne + " parties ("
                    + (float) (nbPartiesJ2Gagne * 100 / nbParties) + "%)");
            log.close();
        } catch (Exception e) {
        }
    }

    private static void initIARandomVSIARandom() {
        Jeu.setInitJoueurCommence(Jeu.JOUEUR_1);
        ctrl = new ControleurMediateurLocal();
        ctrl.changeJoueur(0, 1);
        ctrl.changeJoueur(1, 1);
    }

    private static void IARandomVSIAMinMax() {
        Jeu.setInitJoueurCommence(Jeu.JOUEUR_1);
        ctrl = new ControleurMediateurLocal();
        ctrl.changeJoueur(0, 1);
        Configuration.setDifficulteIA(2);
        ctrl.changeJoueur(1, 1);
    }

    private static void IAMinMaxVSRandom() {
        Jeu.setInitJoueurCommence(Jeu.JOUEUR_1);
        ctrl = new ControleurMediateurLocal();
        Configuration.setDifficulteIA(3);
        ctrl.changeJoueur(0, 1);
        Configuration.setDifficulteIA(1);
        ctrl.changeJoueur(1, 1);
    }

    private static void IAMinMax2vsIAMinMax5() {
        Jeu.setInitJoueurCommence(Jeu.JOUEUR_1);
        ctrl = new ControleurMediateurLocal();
        Configuration.setDifficulteIA(8);
        ctrl.changeJoueur(0, 1);
        Configuration.setDifficulteIA(2);
        ctrl.changeJoueur(1, 1);
    }

    public static void IAMinMax3vsIAMinMax3() {
        // Jeu.setInitJoueurCommence(Jeu.JOUEUR_2);
        ctrl = new ControleurMediateurLocal();
        Configuration.setDifficulteIA(5);
        Configuration.setTypeHeuristique(1);
        ctrl.changeJoueur(0, 1);
        Configuration.setDifficulteIA(5);
        Configuration.setTypeHeuristique(1);
        ctrl.changeJoueur(1, 1);
    }
}
