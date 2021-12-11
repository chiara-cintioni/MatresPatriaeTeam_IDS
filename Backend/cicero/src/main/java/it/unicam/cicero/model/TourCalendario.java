package it.unicam.cicero.model;

import java.util.Date;
import java.util.HashSet;

/*
* Classe che implementa un oggetto di tipo TourCalendario che deve essere inserito nella matrice gestioneCalendario.
* */

/**
 *
 */

public class TourCalendario {
    private Tour tour;
    private final int giorno;
    private final int mese;
    private int postiDisponibili;
    private int totalePostiPrenotati;
    private boolean possibilitaDiPagare;
    private HashSet elencoPrenotazioni;

    public TourCalendario(Tour tour, int giorno, int mese) {
        this.tour = tour;
        this.giorno = giorno;
        this.mese = mese;
        this.postiDisponibili = tour.getNumeroMassimo();
        this.totalePostiPrenotati = 0;
        this.elencoPrenotazioni = new HashSet<>();
    }

    /**
     *
     * @return
     */
    public Tour getTour() {
        return tour;
    }

    /**
     *
     * @param tour
     */
    public void setTour(Tour tour) {
        this.tour = tour;
    }

    /**
     *
     * @return
     */
    public int getGiorno() {
        return giorno;
    }

    /**
     *
     * @return
     */
    public int getTotalePostiPrenotati(){
        return totalePostiPrenotati;
    }

    /**
     *
     * @param totalePostiPrenotati
     */
    public void setTotalePostiPrenotati(int totalePostiPrenotati) {
        this.totalePostiPrenotati = totalePostiPrenotati;
    }

    /**
     *
     * @return
     */
    public int getMese() {
        return mese;
    }

    /**
     *
     * @return
     */
    public int getPostiDisponibili() {
        return postiDisponibili;
    }

    /**
     *
     * @param postiDisponibili
     */
    public void setPostiDisponibili(int postiDisponibili) {
        this.postiDisponibili = postiDisponibili;
    }

    /**
     *
     * @return
     */
    public boolean getPossibilitaDiPagare() {
        return possibilitaDiPagare;
    }

    /**
     *
     * @param possibilitaDiPagare
     */
    public void setPossibilitaDiPagare(boolean possibilitaDiPagare) {
        this.possibilitaDiPagare = possibilitaDiPagare;
    }

    /**
     *
     */
    public void raggiuntoNumeroMinimo () {
        if (getTotalePostiPrenotati() >= getTour().getNumeroMinimo()) {
            setPossibilitaDiPagare(true);
        }
    }

    public HashSet getElencoPrenotazioni(){
        return this.elencoPrenotazioni;
    }


}
