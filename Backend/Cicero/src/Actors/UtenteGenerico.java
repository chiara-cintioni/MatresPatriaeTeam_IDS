package Actors;

import java.util.*;

public class UtenteGenerico {
    private static Scanner scanner = new Scanner (System.in);
    private static UtenteGenerico utente;

    private UtenteGenerico(){
    }

    public static UtenteGenerico getInstance(){
        if(utente == null){
            utente = new UtenteGenerico();
        }
        return utente;
    }

}
