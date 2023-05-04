import Controleur.ControleurJoueur;
import Global.Configuration;

import Modele.Jeu;
import Vue.InterfaceTextuelle;

public class Antinomy{
    final static String typeInterface = Configuration.typeInterface;

    public static void main(String[] args) {
        Configuration.info("Bienvenue dans Antinomy !");
        Jeu j = new Jeu();
        ControleurJoueur c = new ControleurJoueur(j);

        switch (typeInterface) {
            case "Graphique":
                Configuration.info("Interface graphique");
                // TODO
                break;
            case "Textuelle":
                Configuration.info("Interface textuelle");
                InterfaceTextuelle.demarrer(j, c);
                break;
            default:
                Configuration.erreur("Type d'interface inconnu");
                break;
        }

    }

}