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

import java.lang.Math;
import java.util.Arrays;

public class Sauvegarde {
    JSONObject obj;
    ControleurMediateur ctrl;

    /*
    ############################# Constructeurs #############################
    */

    public Sauvegarde(String nomFichier, Jeu j, ControleurMediateur ctrl) {
        this.ctrl = ctrl;
        obj = new JSONObject();
        saveDeck(j.getDeck());
        saveTour(j.getTour());
        saveMain(true, j.getMain(true));
        saveMain(false, j.getMain(false));
        saveAutomataState();
        writeToFile();
    }

    /*
    ############################# Sauvegarde #############################
    */

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

    public void writeToFile(){
        try{
            FileWriter file = new FileWriter("./output.json");
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

    public static void restaurerSauvegarde(Jeu jeu, String nomFichier) {
        JSONObject obj = getObj("output.json");
        
        JSONObject score = (JSONObject) obj.get("score");
        int scoreJ1 = Math.toIntExact((long) score.get("j1"));
        int scoreJ2 = Math.toIntExact((long) score.get("j2"));

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

        Main m1 = new Main(main1);
        Main m2 = new Main(main2);

        String codex = (String) obj.get("codex");
        ArrayList<String> couple = getCoupleFromString(codex);
        Carte codexCarte = new Carte(Integer.parseInt(couple.get(0)), Integer.parseInt(couple.get(1)));

        JSONObject sceptres = (JSONObject) obj.get("sceptres");
        JSONObject s2 = new JSONObject(sceptres);
        Configuration.info("" + s2.get("j1"));
        Configuration.info("" + s2.get("j2"));

        int sceptreJ1 = Math.toIntExact((long) s2.get("j1"));
        int sceptreJ2 = Math.toIntExact((long) s2.get("j2"));

        boolean tour = (Boolean) obj.get("tour");
        
        jeu.restaure(m1, m2, codexCarte, sceptreJ1, sceptreJ2, tour, scoreJ1, scoreJ2);
        
        Configuration.info("Sauvegarde restaurée");

    }

}
