package Modele;

import org.json.simple.JSONObject;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileNotFoundException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.lang.Math;

public class Sauvegarde {
    JSONObject obj;

    public Sauvegarde(String nomFichier, Jeu j) {
        obj = new JSONObject();
        saveDeck(j.getDeck(), obj);
        saveTour(j.getTour());
        saveMain(true, j.getMain(true));
        saveMain(false, j.getMain(false));
        writeToFile();
    }

    public void saveMain(Boolean joueur, Carte[] main) {
        ArrayList<String> mainJoueur = new ArrayList<String>();
        for (int i=0; i<main.length; i++) {
            mainJoueur.add(java.util.Arrays.toString(getCouple(main[i])));
        }
        if (joueur) obj.put("main1", mainJoueur);
        else obj.put("main2", mainJoueur);
    }

    public void saveContinuum(Carte[] continuum) {
        ArrayList<String> continuumJoueur = new ArrayList<String>();
        for (int i=0; i<continuum.length; i++) {
            continuumJoueur.add(java.util.Arrays.toString(getCouple(continuum[i])));
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
        obj.put("codex", java.util.Arrays.toString(getCouple(codex)));
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

    public int[] getCouple(Carte c){
        int[] couple = new int[2];
        couple[0] = c.getIndex();
        couple[1] = c.getType();
        return couple;
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

    private static void restaureScore(JSONObject obj){
        JSONObject score = (JSONObject) obj.get("score");
        JSONObject s2 = new JSONObject(score);
        Compteur.getInstance().setScore(true,Math.toIntExact( (long) s2.get("j1")));
        Compteur.getInstance().setScore(false,Math.toIntExact((long) s2.get("j2")));
    }

    public static void restaureSceptres(JSONObject obj, Jeu j){
        JSONObject sceptres = (JSONObject) obj.get("sceptres");
        JSONObject s2 = new JSONObject(sceptres);
        System.out.println(s2.get("j1"));
        System.out.println(s2.get("j2"));
        j.getDeck().setSceptre(true,Math.toIntExact((long) s2.get("j1")));
        j.getDeck().setSceptre(false,Math.toIntExact((long) s2.get("j2")));
    }

    public static  Jeu restaurerSauvegarde(String nomFichier) {
        JSONObject obj = getObj("output.json");
        Jeu j = new Jeu();
        restaureScore(obj);
        restaureSceptres(obj,j);
        //restaureMain(obj,j);
        return j;

    }

}
