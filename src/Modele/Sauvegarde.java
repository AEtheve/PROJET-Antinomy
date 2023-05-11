package Modele;

import org.json.simple.JSONObject;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileNotFoundException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Controleur.ControleurJoueur;

import java.lang.Math;
import java.util.Arrays;

public class Sauvegarde {
    JSONObject obj;

    public Sauvegarde(String nomFichier, Jeu j) {
        obj = new JSONObject();
        saveDeck(j.getDeck(), obj);
        saveTour(j.getTour());
        saveMain(true, j.getMain(true));
        saveMain(false, j.getMain(false));
        saveAutomataState();
        writeToFile();
    }

    public void saveAutomataState() {
        obj.put("automataState", ControleurJoueur.getState());
    }

    public void saveMain(Boolean joueur, Carte[] main) {
        ArrayList<Integer> mainJoueur = new ArrayList<Integer>();
        for (int i=0; i<main.length; i++) {
            mainJoueur.add(main[i].getType());
        }
        if (joueur) obj.put("main1", mainJoueur);
        else obj.put("main2", mainJoueur);
    }

    public void saveContinuum(Carte[] continuum) {
        ArrayList<Integer> continuumJoueur = new ArrayList<Integer>();
        for (int i=0; i<continuum.length; i++) {
            continuumJoueur.add(continuum[i].getType());
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

    public void saveDeck(Deck d, JSONObject obj) {
        JSONObject sceptres = new JSONObject();
        saveSceptres(d.getSceptre(true), d.getSceptre(false));
        Compteur c = Compteur.getInstance();
        saveScore(c.getJ1Points(), c.getJ2Points());
        saveCodex(d.getCodex());
        saveContinuum(d.getContinuum());
    }

    public void writeToFile(){
        try{
            FileWriter file = new FileWriter("./output.json");
            file.write(obj.toJSONString());
            file.close();
        } catch (IOException e) {}
    }

    private static JSONObject getObj(String nomFichier) {
        JSONParser jsonP = new JSONParser();
        try{
            return (JSONObject) jsonP.parse(new FileReader(nomFichier));
        }
        catch(FileNotFoundException e){
            System.out.println("Fichier non trouvé");
        }
        catch(ParseException e){
            System.out.println("Erreur de parsing");
        }
        catch(IOException e){
            System.out.println("Erreur d'IO");
        }
        System.out.println("Erreur de lecture");
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
    //     System.out.println(s2.get("j1"));
    //     System.out.println(s2.get("j2"));
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

        Carte[] cartes = new Carte[16];

        int k = 0;
        for (int i=0; i<3; i++) {
            ArrayList<String> couple = getCoupleFromString(mainJoueur1.get(i));
            main1[i] = new Carte(Integer.parseInt(couple.get(0)), Integer.parseInt(couple.get(1)));
            cartes[k] = main1[i];
            k++;
        }
        for (int i=0; i<3; i++) {
            ArrayList<String> couple = getCoupleFromString(mainJoueur2.get(i));
            main2[i] = new Carte(Integer.parseInt(couple.get(0)), Integer.parseInt(couple.get(1)));
            cartes[k] = main2[i];
            k++;
        }

        Main m1 = new Main(main1);
        Main m2 = new Main(main2);

        String codex = (String) obj.get("codex");
        ArrayList<String> couple = getCoupleFromString(codex);
        Carte codexCarte = new Carte(Integer.parseInt(couple.get(0)), Integer.parseInt(couple.get(1)));

        JSONObject sceptres = (JSONObject) obj.get("sceptres");
        JSONObject s2 = new JSONObject(sceptres);
        System.out.println(s2.get("j1"));
        System.out.println(s2.get("j2"));

        int sceptreJ1 = Math.toIntExact((long) s2.get("j1"));
        int sceptreJ2 = Math.toIntExact((long) s2.get("j2"));

        boolean tour = (Boolean) obj.get("tour");

        int j1Points = Math.toIntExact((long) score.get("j1"));
        int j2Points = Math.toIntExact((long) score.get("j2"));
        
        jeu.restaure(cartes, m1, m2, codexCarte, sceptreJ1, sceptreJ2, tour, j1Points, j2Points);
        
        System.out.println("Sauvegarde restaurée");

    }

}
