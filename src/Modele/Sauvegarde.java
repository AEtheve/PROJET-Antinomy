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
            mainJoueur.add((int) main[i].getByteType());
        }
        if (joueur) obj.put("main1", mainJoueur);
        else obj.put("main2", mainJoueur);
    }

    public void saveContinuum(Carte[] continuum) {
        ArrayList<Integer> continuumJoueur = new ArrayList<Integer>();
        for (int i=0; i<continuum.length; i++) {
            continuumJoueur.add((int)continuum[i].getByteType());
            System.out.println(continuum[i].getByteType());
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

    // {
    //     "score": { "j1": 0, "j2": 0 },
    //     "continuum": [-51, 41, -119, -95, 109, 1, 89, -99, 113],
    //     "main2": [-75, 21, -7],
    //     "automataState": 3,
    //     "main1": [-27, -47, 61],
    //     "codex": 4,
    //     "sceptres": { "j1": 8, "j2": 8 },
    //     "tour": true
    //   }
      
    public static void restaurerSauvegarde(Jeu jeu, String nomFichier) {
        JSONObject obj = getObj("output.json");
        
        JSONObject score = (JSONObject) obj.get("score");
        int scoreJ1 = Math.toIntExact((long) score.get("j1"));
        int scoreJ2 = Math.toIntExact((long) score.get("j2"));
        
        System.out.println("Score: " + scoreJ1 + " - " + scoreJ2);

        JSONObject sceptres = (JSONObject) obj.get("sceptres");
        int sceptreJ1 = Math.toIntExact((long) sceptres.get("j1"));
        int sceptreJ2 = Math.toIntExact((long) sceptres.get("j2"));

        System.out.println("Sceptres: " + sceptreJ1 + " - " + sceptreJ2);

        Boolean tour = (Boolean) obj.get("tour");
        System.out.println("Tour: " + tour);
        
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

        System.out.println("Main 1: " + Arrays.toString(m1));
        System.out.println("Main 2: " + Arrays.toString(m2));

        for (int i=0; i<continuumLong.size(); i++) {
            int type = continuumLong.get(i).intValue();
            int color = ((type & 0b110000) >> 4) + 1;
            int symbol = ((type & 0b11000000) >> 6) + 1;
            int value = ((type & 0b1100) >> 2) + 1;
            continuumCarte[i] = new Carte(symbol, color, value, i, true);
            cartes[k++] = continuumCarte[i];
        }

        System.out.println("Continuum: " + Arrays.toString(continuumCarte));

        int codexType = Math.toIntExact((long) obj.get("codex"));
        int codexColor = ((codexType & 0b110000) >> 4) + 1;
        int codexSymbol = ((codexType & 0b11000000) >> 6) + 1;
        int codexValue = ((codexType & 0b1100) >> 2) + 1;
        Carte codexCarte = new Carte(codexSymbol, codexColor, codexValue, 0, true);

        jeu.restaure(continuumCarte, new Main(m1), new Main(m2), codexCarte, sceptreJ1, sceptreJ2, tour, scoreJ1, scoreJ2);

        System.out.println("Sauvegarde restaurée");

    }

}
