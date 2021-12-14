package it.unicam.cicero.model;

import java.util.Date;
import java.util.HashSet;

/*
* Classe che implementa un oggetto di tipo TourCalendario che deve essere inserito nella matrice gestioneCalendario. L'oggetto di questa classe e' un tour che
* specifica la data (giorno e mese), il nuemro di psoti disponibili, il totale di posti prenotati, se e' possibile pagare il tour oppure no
* e l'elenco delle prenotazioni fatte in quel tour (di quel specifico tour).
* Questa classe serve per gestire ogni tour in modo autonomo (soprattutto per quanto riguarda le prenotazioni).
* */


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
     *Metodo che ritorna un tour della classe Tour
     *
     * @return un tour della classe Tour
     */
    public Tour getTour() {
        return tour;
    }

    /**
     *Metodo che modifica un tour della classe Tour
     *
     * @param tour che si vuole modificare
     */
    public void setTour(Tour tour) {
        this.tour = tour;
    }

    /**
     *Metodo che ritorna il giorno del calendario dove viene registrato il tour
     *
     * @return il giorno del clendario dove viene registrato il tour
     */
    public int getGiorno() {
        return giorno;
    }

    /**
     *Metodo che ritorna il numero totale dei posti prenotati di uno specifico tour
     *
     * @return il numero totale di posti prenotati del tour
     */
    public int getTotalePostiPrenotati(){
        return totalePostiPrenotati;
    }

    /**
     *Metodo che modifica il numero totale dei posti prenotati
     *
     * @param totalePostiPrenotati del tour
     */
    public void setTotalePostiPrenotati(int totalePostiPrenotati) {
        this.totalePostiPrenotati = totalePostiPrenotati;
    }

    /**
     *Metodo che ritorna il mese del calendario in cui e' registrato il tour
     *
     * @return il mese del calendario in cui e' registrati il tour
     */
    public int getMese() {
        return mese;
    }

    /**
     *Metodo che ritorna il numero totale di posti ancora disponibili in quel tour
     *
     * @return il numero totale di posti ancora disponibili in quel tour
     */
    public int getPostiDisponibili() {
        return postiDisponibili;
    }

    /**
     *Metodo che modifica il numero di posti disponibili del tour
     *
     * @param postiDisponibili che sono diminuiti dopo una prneotazione, o aumentati dopo un annullamento di prenotazione
     */
    public void setPostiDisponibili(int postiDisponibili) {

        this.postiDisponibili = postiDisponibili;
    }

    /**
     *Metodo che ritorna true se il tour ha raggiunto il numero minimo di posti da prenotare (e quindi può essere pagato), false altrimenti.
     *
     * @return true se il tour puo' essere pagato perche' ha raggiunto il numero minimo di prenotazioni, false altrimenti
     */
    public boolean getPossibilitaDiPagare() {
        return possibilitaDiPagare;
    }

    /**
     *Metodo che modifica il valore booleano di PossibilitadiPagare
     *
     * @param possibilitaDiPagare nuovo valore boolean che si vuoel modificare
     */
    public void setPossibilitaDiPagare(boolean possibilitaDiPagare) {
        this.possibilitaDiPagare = possibilitaDiPagare;
    }

    /**
     * Metodo che controlla se e' stato raggiunto il numero minimo di prenotazioni, per far si che un turista possa pagare il tour. Se il numero
     * minimo di prenotazioni e' stato raggiunto, allora viene richiamato il metodo setPossibilitaDiPagare con valore imput true.
     */
    public void raggiuntoNumeroMinimo () {
        if (getTotalePostiPrenotati() >= getTour().getNumeroMinimo()) {
            setPossibilitaDiPagare(true);
        }
    }

    /**
     * Metodo che ritorna elenco di prenotazioni di un singolo tour
     *
     * @return elenco di prneotazioni di un singolo tour
     */
    public HashSet getElencoPrenotazioni(){
        return this.elencoPrenotazioni;
    }


}
