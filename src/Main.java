package src;

import java.util.ArrayList;
import java.util.Arrays;

import src.recomanador.domini.*;
import src.recomanador.persistencia.*;

public class Main {
    public static void main(String[] args) {
        ControladorDomini cd = new ControladorDomini();
        
        ArrayList<ArrayList<String>> s = new ArrayList<ArrayList<String>>();
        ArrayList<String> c = new ArrayList<>(Arrays.asList("adult","belongs_to_collection","budget","genres","homepage","id","imdb_id","original_language","original_title","overview","popularity","poster_path","production_companies","production_countries","release_date","revenue","runtime","spoken_languages","status","tagline","title","video","vote_average","vote_count","keywords"));
        s.add(c);
        c = new ArrayList<>(Arrays.asList("False","","98000000","Action;Adventure","","1408","tt0112760","en","Cutthroat Island","Morgan Adams and her slave, William Shaw, are on a quest to recover the three portions of a treasure map. Unfortunately, the final portion is held by her murderous uncle, Dawg. Her crew is skeptical of her leadership abilities, so she must complete her quest before they mutiny against her. This is made yet more difficult by the efforts of the British crown to end her pirate raids","7.284477","/odM9973kIv9hcjfHPp6g6BlyTIJ.jpg","Le Studio Canal+;Laurence Mark Productions;Metro-Goldwyn-Mayer (MGM);Carolco Pictures","","1995-12-22","10017322","119","en;la","Released","The Course Has Been Set. There Is No Turning Back. Prepare Your Weapons. Summon Your Courage. Discover the Adventure of a Lifetime!","Cutthroat Island","False","5.7","137","zxotic island;treasure;map;ship;scalp;pirate"));
        s.add(c);
        c = new ArrayList<>(Arrays.asList("False","","4000000","Crime;Comedy","","5","tt0113101","en","Four Rooms","Its Ted the Bellhops first night on the job...and the hotels very unusual guests are about to place him in some outrageous predicaments. It seems that this evenings room service is serving up one unbelievable happening after another.","9.026586","/eQs5hh9rxrk1m4xHsIz1w11Ngqb.jpg","Miramax Films;A Band Apart","","1995-12-09","4300000","98","en","Released","Twelve outrageous guests. Four scandalous requests. And one lone bellhop, in his first day on the job, whos in for the wildest New years Eve of his life.","Four Rooms","False","6.5","539","hotel;new years eve;witch;bet;hotel room;sperm;los angeles;hoodlum;woman director;episode film"));
        s.add(c);
        cd.provaItems(s);
    }
}