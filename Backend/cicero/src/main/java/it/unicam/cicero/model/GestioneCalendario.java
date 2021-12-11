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
     *
     * @param giorno
     * @param mese
     * @param tour
     */
    public void modificaCalendario (int giorno, int mese, Tour tour) {
        if(this.calendario[giorno][mese] != null){
            throw new IllegalArgumentException("E' già presente un tour");
        }
        TourCalendario tourDaInserire = new TourCalendario(tour, giorno, mese);
        this.calendario[giorno][mese] = tourDaInserire;
    }

    /**
     *
     * @param giorno
     * @param mese
     */
    public void eliminaTourDalCalendario(int giorno, int mese){
        if(this.calendario[giorno][mese] == null){
            throw new NullPointerException("Non è presente nessun tour in questa data");
        }
        this.calendario[giorno][mese] = null;
    }

    /**
     *
     * @param giorno
     * @param mese
     * @return
     */
    public TourCalendario restituisciTour (int giorno, int mese) {
        return this.calendario[giorno][mese];
    }


}
