import Controleur.ControleurMediateur;
import Controleur.ControleurMediateurLocal;
import Global.Configuration;

import Vue.InterfaceGraphique;
// import Vue.InterfaceTextuelle;

public class Antinomy{
    final static String typeInterface = Configuration.typeInterface;

    public static void main(String[] args) {
        Configuration.info("Bienvenue dans Antinomy !");
        ControleurMediateur c = new ControleurMediateurLocal();

        switch (typeInterface) {
            case "Graphique":
                Configuration.info("Interface graphique");
                InterfaceGraphique.demarrer(c);
                break;
            // case "Textuelle":
            //     Configuration.info("Interface textuelle");
            //     InterfaceTextuelle.demarrer(c);
            //     break;
            default:
                Configuration.erreur("Type d'interface inconnu");
                break;
        }

    }

}