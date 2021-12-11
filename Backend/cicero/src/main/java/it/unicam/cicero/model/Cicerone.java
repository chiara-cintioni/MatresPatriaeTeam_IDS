package it.unicam.cicero.model;
import java.lang.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;

import lombok.Data;

@Data
public class Cicerone {

    private String nome;
    private String cognome;
    private String email;
    private GestioneCalendario calendario;
    private HashSet elencoTour;


    public Cicerone(String nome, String cognome, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.calendario = new GestioneCalendario();
        this.elencoTour = new HashSet<>();
    }

    /**
     *
     * @param tour
     * @param giorno
     * @param mese
     */
    public void addDisponibilita (Tour tour, int giorno, int mese) {
        if (giorno > 31 || giorno < 1) throw new IllegalArgumentException("Il giorno non esiste");
        if (mese > 12 || mese < 1) throw new IllegalArgumentException("Il mese non esiste");
        /*
        * Controllo per il mese di Febbraio riguardo l'anno bisestile */
        Calendar date = new GregorianCalendar();
        int year = date.get(Calendar.YEAR);
        if (mese == 2) {
            if(!annoBisestile(year)){
                if(giorno>28){
                    throw new IllegalArgumentException("Non esiste questo giorno");
                }
            } else{
                if (giorno > 29) {
                    throw new IllegalArgumentException("Non esiste questo giorno");
                }
            }
        }
        int [] arrayMesi = {4, 6, 9, 11}; //mesi che hanno 30 giorni
        for (int i = 0; i < arrayMesi.length; i++) {
            if (mese == arrayMesi[i]) {
                if (giorno >= 31) {
                    throw new IllegalArgumentException("Non esiste questo giorno");
                }

            }
        }

    this.calendario.modificaCalendario(giorno, mese, tour);
    }

    /**
     *
     * @param anno
     * @return
     */
    boolean annoBisestile(int anno){
        if(anno > 1584 && (anno%400==0 || (anno%4==0 && anno%100!=0))){
            return true;
        }
        return false;
    }

    /**
     *
     * @param tour
     */
    public void removeDisponibilita (TourCalendario tour) {
        this.calendario.eliminaTourDalCalendario(tour.getGiorno(), tour.getMese());
    }

    /**
     *
     * @param nome
     * @param numeroMin
     * @param numeroMax
     * @param prezzo
     * @param descrizione
     */
    public void creaTour(String nome, int numeroMin, int numeroMax, double prezzo, String descrizione){
        Tour tour = new Tour(nome, numeroMin, numeroMax, prezzo, descrizione);
        this.elencoTour.add(tour);
    }

    /**
     *
     * @param tour
     */
    public void eliminaTourDefinitivamente(Tour tour) {
        this.elencoTour.remove(tour);
        for (int i = 0; i < 31; i++) {
            for (int j = 0; j< 12; j++){
                TourCalendario tourCalendario = this.calendario.restituisciTour(i,j);
                if (tourCalendario.getTour().getId() == tour.getId()) {
                    this.calendario.eliminaTourDalCalendario(i,j);
                }
            }
        }

    }

}


