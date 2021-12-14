package it.unicam.cicero.model;
import java.lang.*;


import java.util.*;
/*
Il calendario e' una matrice di tour dove il cicerone inserisce i tour che è disponibile a fare in quel giorno.
Ogni tour nella matrice e' si uguale per tipo (se il Cicerone crea il tour1 ogni volta che inserisce il tour1 nel calendario
il tour rimane uguale), ma diverso in quanto svolto in giorni diversi (diverso numero di persone che hanno prenotato).
*/
public class GestioneCalendario {

    private TourCalendario[][] calendario;


    public GestioneCalendario() {
        this.calendario = new TourCalendario[31][12];

    }


    /**
     *Metodo che modifica il calendario del cicerone
     *
     * @param giorno del tour che si vuole modificare
     * @param mese del tour che si vuole modificare
     * @param tour che si vuole inserire in quel giorno e quel mese
     */
    public void modificaCalendario (int giorno, int mese, Tour tour) {
        if(this.calendario[giorno][mese] != null){
            throw new IllegalArgumentException("E' già presente un tour");
        }
        TourCalendario tourDaInserire = new TourCalendario(tour, giorno, mese);
        this.calendario[giorno][mese] = tourDaInserire;
    }

    /**
     *Metodo che eimina un tour dal calendario del cicerone
     *
     * @param giorno del tour che si vuole eliminare
     * @param mese del tour che si vuoel eliminare
     */
    public void eliminaTourDalCalendario(int giorno, int mese){
        if(this.calendario[giorno][mese] == null){
            throw new NullPointerException("Non è presente nessun tour in questa data");
        }
        this.calendario[giorno][mese] = null;
    }

    /**
     *Metodo che ritorna un tour inserito nel calendario del cicerone
     *
     * @param giorno del calendario del tour che si vuole ritornare
     * @param mese del calendario del tour che si vuole ritornare
     *
     * @return il tour inserito in quella specifica data
     */
    public TourCalendario restituisciTour (int giorno, int mese) {
        return this.calendario[giorno][mese];
    }


}
