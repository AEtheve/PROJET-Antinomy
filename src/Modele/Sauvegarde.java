package Modele;

import org.json.simple.JSONObject;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileNotFoundException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Controleur.ControleurMediateur;
import Global.Configuration;
import Structures.Sequence;

import java.lang.Math;
import java.util.Arrays;

public class Sauvegarde {
    JSONObject obj;
    ControleurMediateur ctrl;

    /*
    ############################# Constructeurs #############################
    */

    public Sauvegarde(String nomFichier, Jeu j, ControleurMediateur ctrl,Boolean swapDroit, Boolean swapGauche) {
        this.ctrl = ctrl;
        obj = new JSONObject();
        saveDeck(j.getDeck());
        saveTour(j.getTour());
        saveMain(true, j.getMain(true));
        saveMain(false, j.getMain(false));
        saveAutomataState();
        saveSwap(swapDroit, swapGauche);
        saveHistorique();
        writeToFile(nomFichier);
    }

    /*
    ############################# Sauvegarde #############################
    */

    public void saveSwap(Boolean swapDroit, Boolean swapGauche) {
        JSONObject swap = new JSONObject();
        swap.put("swapDroit", swapDroit);
        swap.put("swapGauche", swapGauche);
        obj.put("swap", swap);
    }

    public void saveHistorique() {
        // ctrl.getHistorique().getPasse() et getFutur() sont des SequenceListe
        JSONObject historique = new JSONObject();
        Sequence<Commande> passe = ctrl.getHistorique().getHistoriquePasse();
        Sequence<Commande> futur = ctrl.getHistorique().getHistoriqueFutur();
        
        // on va stocker chaque commande dans le json:
        System.out.println("Passe: " + passe.toString());
        

    }

    public void saveAutomataState() {
        obj.put("automataState", ctrl.getState());
    }

    public void saveMain(Boolean joueur, Carte[] main) {
        ArrayList<Integer> mainJoueur = new ArrayList<Integer>();
        for (int i=0; i<main.length; i++) {
            mainJoueur.add((int) main[i].getType());
        }
        if (joueur) obj.put("main1", mainJoueur);
        else obj.put("main2", mainJoueur);
    }

    public void saveContinuum(Carte[] continuum) {
        ArrayList<Integer> continuumJoueur = new ArrayList<Integer>();
        for (int i=0; i<continuum.length; i++) {
            continuumJoueur.add((int)continuum[i].getType());
            Configuration.info("" + continuum[i].getType());
        }
        obj.put("continuum", continuumJoueur);
    }

    public void saveSceptres(int j1, int j2) {
        JSONObject sceptres = new JSONObject();
        sceptres.put("j1", j1);
        sceptres.put("j2", j2);
        obj.put("sceptres", sceptres);
    }

    public void saveScore(int j1, int j2) {
        JSONObject score = new JSONObject();
        score.put("j1", j1);
        score.put("j2", j2);
        obj.put("score", score);
    }

    public void saveCodex(Carte codex){
        JSONObject score = new JSONObject();
        // obj.put("codex", getCouple(codex));
        obj.put("codex", codex.getIndex());
    }

    public void saveTour(Boolean tour){
        obj.put("tour", tour);
    }

    public void saveDeck(Deck d) {
        saveSceptres(d.getSceptre(true), d.getSceptre(false));
        Compteur c = Compteur.getInstance();
        saveScore(c.getJ1Points(), c.getJ2Points());
        saveCodex(d.getCodex());
        saveContinuum(d.getContinuum());
    }

    /*
    ############################# Ecriture #############################
    */
    

    public void writeToFile(String nomFichier){
        try{
            FileWriter file = new FileWriter(nomFichier);
            file.write(obj.toJSONString());
            file.close();
        } catch (IOException e) {}
    }

    public static void writeToFile(JSONObject obj, String nomFichier){
        try{
            FileWriter file = new FileWriter(nomFichier);
            file.write(obj.toJSONString());
            file.close();
        } catch (IOException e) {}
    }

    /*
    ############################# Restauration #############################
    */

    private static JSONObject getObj(String nomFichier) {
        JSONParser jsonP = new JSONParser();
        try{
            return (JSONObject) jsonP.parse(new FileReader(nomFichier));
        }
        catch(FileNotFoundException e){
            Configuration.info("Fichier non trouvé");
        }
        catch(ParseException e){
            Configuration.info("Erreur de parsing");
        }
        catch(IOException e){
            Configuration.info("Erreur d'IO");
        }
        Configuration.info("Erreur de lecture");
        return null;
    }

    // private static void restaureScore(JSONObject obj){
    //     JSONObject score = (JSONObject) obj.get("score");
    //     JSONObject s2 = new JSONObject(score);
    //     Compteur.getInstance().setScore(true,Math.toIntExact( (long) s2.get("j1")));
    //     Compteur.getInstance().setScore(false,Math.toIntExact((long) s2.get("j2")));
    // }

    // public static void restaureSceptres(JSONObject obj, Jeu j){
    //     JSONObject sceptres = (JSONObject) obj.get("sceptres");
    //     JSONObject s2 = new JSONObject(sceptres);
    //     Configuration.info(s2.get("j1"));
    //     Configuration.info(s2.get("j2"));
    //     j.getDeck().setSceptre(true,Math.toIntExact((long) s2.get("j1")));
    //     j.getDeck().setSceptre(false,Math.toIntExact((long) s2.get("j2")));
    // }

    private static ArrayList<String> getCoupleFromString(String s){
        s = s.replace("[","");
        s = s.replace("]","");
        s = s.replace(" ","");
        ArrayList<String> myList = new ArrayList<String>(Arrays.asList(s.split(",")));
        return myList;
    }

    public static void restaureMain(JSONObject obj, Jeu j){
        ArrayList<String> mainJoueur1 = (ArrayList<String>) obj.get("main1");
        ArrayList<String> mainJoueur2 = (ArrayList<String>) obj.get("main2");
        Carte[] main1 = new Carte[3];
        Carte[] main2 = new Carte[3];
        for (int i=0; i<3; i++) {
            ArrayList<String> couple = getCoupleFromString(mainJoueur1.get(i));
            main1[i] = new Carte(Integer.parseInt(couple.get(0)), Integer.parseInt(couple.get(1)));
        }
        for (int i=0; i<3; i++) {
            ArrayList<String> couple = getCoupleFromString(mainJoueur2.get(i));
            main2[i] = new Carte(Integer.parseInt(couple.get(0)), Integer.parseInt(couple.get(1)));
        }
        j.setMain(main1, true);
        j.setMain(main2, false);
    }

    public static void restaureTour(JSONObject obj, Jeu j){
        j.setTour((Boolean) obj.get("tour"));
    }

    public static void restaureContinuum(JSONObject obj, Jeu j){
        ArrayList<String> continuum = (ArrayList<String>) obj.get("continuum");
        Carte[] continuumJoueur = new Carte[9];
        for (int i=0; i<9; i++) {
            ArrayList<String> couple = getCoupleFromString(continuum.get(i));
            continuumJoueur[i] = new Carte(Integer.parseInt(couple.get(0)), Integer.parseInt(couple.get(1)));
        }
        j.getDeck().setContinuum(continuumJoueur);
    }

    public static void restaureCodex(JSONObject obj, Jeu j){
        String codex = (String) obj.get("codex");
        ArrayList<String> couple = getCoupleFromString(codex);
        Carte c = new Carte(Integer.parseInt(couple.get(0)), Integer.parseInt(couple.get(1)));
        j.getDeck().setCodex(c);
    }

    public static JSONObject restaurerSauvegarde(Jeu jeu, String nomFichier) {
         JSONObject obj = getObj(nomFichier);
        
        JSONObject score = (JSONObject) obj.get("score");
        int scoreJ1 = Math.toIntExact((long) score.get("j1"));
        int scoreJ2 = Math.toIntExact((long) score.get("j2"));
        
        //System.out.println("Score: " + scoreJ1 + " - " + scoreJ2);

        JSONObject sceptres = (JSONObject) obj.get("sceptres");
        int sceptreJ1 = Math.toIntExact((long) sceptres.get("j1"));
        int sceptreJ2 = Math.toIntExact((long) sceptres.get("j2"));

        //System.out.println("Sceptres: " + sceptreJ1 + " - " + sceptreJ2);

        Boolean tour = (Boolean) obj.get("tour");
        //System.out.println("Tour: " + tour);
        
        ArrayList<Long> continuumLong = (ArrayList<Long>) obj.get("continuum");

        Carte[] continuumCarte = new Carte[continuumLong.size()];
        Carte [] cartes = new Carte[16];

        Carte m1[] = new Carte[3];
        Carte m2[] = new Carte[3];
        
        ArrayList<Long> main1Long = (ArrayList<Long>) obj.get("main1");
        ArrayList<Long> main2Long = (ArrayList<Long>) obj.get("main2");

        int k = 0;
        for (int i=0; i<main1Long.size(); i++) {
            int type = main1Long.get(i).intValue();
            int color = ((type & 0b110000) >> 4) + 1;
            int symbol = ((type & 0b11000000) >> 6) + 1;
            int value = ((type & 0b1100) >> 2) + 1;
            m1[i] = new Carte(symbol, color, value, i, true);
            cartes[k++] = m1[i];
        }

        for (int i=0; i<main2Long.size(); i++) {
            int type = main2Long.get(i).intValue();
            int color = ((type & 0b110000) >> 4) + 1;
            int symbol = ((type & 0b11000000) >> 6) + 1;
            int value = ((type & 0b1100) >> 2) + 1;
            m2[i] = new Carte(symbol, color, value, i, true);
            cartes[k++] = m2[i];
        }

        //System.out.println("Main 1: " + Arrays.toString(m1));
        //System.out.println("Main 2: " + Arrays.toString(m2));

        for (int i=0; i<continuumLong.size(); i++) {
            int type = continuumLong.get(i).intValue();
            int color = ((type & 0b110000) >> 4) + 1;
            int symbol = ((type & 0b11000000) >> 6) + 1;
            int value = ((type & 0b1100) >> 2) + 1;
            continuumCarte[i] = new Carte(symbol, color, value, i, true);
            cartes[k++] = continuumCarte[i];
        }

        //System.out.println("Continuum: " + Arrays.toString(continuumCarte));

        int codexType = Math.toIntExact((long) obj.get("codex"));
        int codexColor = ((codexType & 0b110000) >> 4) + 1;
        int codexSymbol = ((codexType & 0b11000000) >> 6) + 1;
        int codexValue = ((codexType & 0b1100) >> 2) + 1;
        Carte codexCarte = new Carte(codexSymbol, codexColor, codexValue, 0, true);

        jeu.restaure(continuumCarte,new Main(m1), new Main(m2), codexCarte, sceptreJ1, sceptreJ2, tour, scoreJ1, scoreJ2);

        JSONObject obj2 = new JSONObject();
        JSONObject swap = (JSONObject) obj.get("swap");
        obj2.put("int",Math.toIntExact((long)obj.get("automataState")));
        obj2.put("swapdroit", swap.get("swapDroit"));
        obj2.put("swapgauche", swap.get("swapGauche"));
        return obj2;

    }

}
