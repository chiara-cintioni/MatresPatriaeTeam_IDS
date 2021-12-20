package it.unicam.cicero.model;
import java.lang.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Pattern;

import lombok.Data;

@Data

/**
 * Classe che crea un cicerone. Il cicerone ha: un nome, un cognome, un email, un oggetto calendario della classe GestioneCalendario 
 * e un elenco di tour fatti dal cicerone.
 */
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
     *Metodo che aggiunge un tour del cicerone in una specifica data
     * 
     * @param tour che il cicerone vuole inserire
     * @param giorno in cui il cicerone vuole inserire il tour
     * @param mese in cui il ciceroen vuole inserire il tour
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
     *Metodo che controlla se l'anno e' bisestile oppure no
     * 
     * @param anno che si vuole controllare
     * @return true se l'anno e' bisestile, false atrimenti
     */
    private boolean annoBisestile(int anno){
        if(anno > 1584 && (anno%400==0 || (anno%4==0 && anno%100!=0))){
            return true;
        }
        return false;
    }

    /**
     *Metodo che rimuove un tour dal calendario. Viene eliminato solo il singolo tour in una specifica data, e non il tour dalla lista dei tour del cicerone
     * 
     * @param tour che si vuoel eliminare
     */
    public boolean removeDisponibilita (TourCalendario tour) {
        if (this.calendario.restituisciTour(tour.getGiorno(),tour.getMese() ) == null) throw new NullPointerException("In questa data non è presente alcun tour");
        HashSet<PrenotazioneTour> elencoPrenotazioni = tour.getElencoPrenotazioni();
        for (PrenotazioneTour p : elencoPrenotazioni) {
            Turista turista = p.getTurista();
            p.eliminaPrenotazione();
            System.out.println("Il tour non è piu' disponibile. " +
                    "La sua prenotazione e' gia' stata annullata. Ci scusiamo per l'inconvenente.");
        }
        this.calendario.eliminaTourDalCalendario(tour.getGiorno(), tour.getMese());
        return true;
    }

    /**
     *Metodo che crea un tour 
     * 
     * @param nome del tour che si vuole creare
     * @param numeroMin di partecipanti al tour che il cicerone sta creando
     * @param numeroMax di partecipanti al tour che il cicerone sta creando
     * @param prezzo del tour che il cicerone sta creando
     * @param descrizione del tour che il cicerone sta creando
     */
    public boolean creaTour(String nome, int numeroMin, int numeroMax, double prezzo, String descrizione){
        Tour tour = new Tour(nome, numeroMin, numeroMax, prezzo, descrizione);
        while (controlloStringa(nome)) {
            System.out.println ("Formato del nome non valido. Può contenere solo lettere dell'alfabeto");
            Scanner sc = new Scanner(System.in);
            setNome(sc.next());
            sc.close();
        }
        this.elencoTour.add(tour);
        return true;
    }

    /**
     * Metodo che controlla il contenuto di una stringa
     *
     * @param stringa che deve essere controllata
     * @return true se la stringa contiene uno dei caratteri speciali, false altrimenti.
     */
    private boolean controlloStringa(String stringa){
        return Pattern.matches("^[a-zA-Z0-9-!?&%$><.;,_*+§#@*|(){}]+$",stringa);
    }

    /**
     *Metodo che elimina un tour definitivamente dalla lista dei tour fatti dal singolo cicerone. Il tour è eliminato anche dal calendario.
     *
     * @param tour che si vuole eliminare definitivamente
     */
    public void eliminaTourDefinitivamente(Tour tour) {
        this.elencoTour.remove(tour);
        for (int i = 0; i < 31; i++) {
            for (int j = 0; j< 12; j++){
                TourCalendario tourCalendario = this.calendario.restituisciTour(i,j);
                if (tourCalendario.getTour().getId() == tour.getId()) {
                    HashSet<PrenotazioneTour> elencoPrenotazioni = tourCalendario.getElencoPrenotazioni();
                   for (PrenotazioneTour p : elencoPrenotazioni) {
                       Turista turista = p.getTurista();
                       p.eliminaPrenotazione();
                       System.out.println("Il tour non è piu' disponibile. " +
                               "La sua prenotazione e' gia' stata annullata. Ci scusiamo per l'inconvenente.");
                       this.calendario.eliminaTourDalCalendario(i, j);
                   }
                }
            }
        }
    }


}


