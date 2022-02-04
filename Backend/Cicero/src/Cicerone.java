import java.lang.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

/**
 * Classe che crea un cicerone. Il cicerone ha: un nome, un cognome, un email,
 * un oggetto calendario della classe GestioneCalendario e un elenco di tour fatti dal cicerone.
 */
public class Cicerone implements Guida {

    private String nome;
    private String cognome;
    private String email;
    private Calendario calendario;
    private HashSet<Tour> elencoTour;
    private Scanner scanner = new Scanner(System.in);




    public Cicerone(String nome, String cognome, String email) {
        while (!controlloStringa(nome) || !controlloStringa(cognome) ) {
            if (!controlloStringa(nome)  &&  !controlloStringa(cognome)){
                System.out.println("Formato del nome e cognome non valido. Inserire un nuovo nome e cognome:");
                System.out.println("Inserisci nome");
                nome = scanner.nextLine();
                System.out.println("Inserisci cognome");
                cognome=scanner.nextLine();
            }

            else if (!controlloStringa(nome)) {
                System.out.println("Formato del nome non valido. Inserire un nuovo nome:");
                nome = (scanner.nextLine());
            }
            else  {
                System.out.println("Formato del cognome non valido. Inserire un nuovo cognome:");
                cognome = (scanner.nextLine());}

            if (!controlloStringa(nome) ||  !controlloStringa(cognome));

        }
        while (!controlloStringaEmail(email)) {
            System.out.println("Email non valida:");
            System.out.println("Inserisci email");
            email = scanner.nextLine();
        }

        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.calendario = new Calendario();
        this.elencoTour = new HashSet<Tour>();
    }

public Cicerone () {}


    /**
     *Metodo che aggiunge un tour del cicerone in una specifica data
     *
     * @param tour che il cicerone vuole inserire
     * @param giorno in cui il cicerone vuole inserire il tour
     * @param mese in cui il ciceroen vuole inserire il tour
     */
    @Override
    public boolean aggiungiDisponibilita (Tour tour, int giorno, int mese) {
        while (!GestioneCalendario.getInstance().modificaCalendario(giorno, mese, tour, calendario)) {
            System.out.println("Inserire il nuovo giorno per il tour: "+tour.getNome());
            giorno = scanner.nextInt();
            System.out.println("Inserire il nuovo mese per il tour: "+tour.getNome());
            mese = scanner.nextInt();
        }

        return true;
    }

    /**
     * Metodo utilizzato dal Cicerone/associazione per modificare la data di un tour, andando
     * modificare la disponibilita' del tour
     * @param tour di cui vuole modificare la data
     * @param nuovoGiorno del tour
     * @param nuovoMese del tour
     * @return true se la modifca e' avvenuta, false altrimenti
     */
    @Override
    public boolean modificaData(TourCalendario tour, int nuovoGiorno, int nuovoMese){
        GestioneCalendario.getInstance().modificaCalendario(nuovoGiorno, nuovoMese, tour.getTour(), this.calendario);
        rimuoviDisponibilita(tour.getGiorno(), tour.getMese());
        return true;
    }



    /**
     *Metodo che rimuove un tour dal calendario. Viene eliminato solo il singolo tour in una specifica data, e non il tour dalla lista dei tour del cicerone
     *
     * @param giorno del calendario dove e' presente il tour che si vuole rimuovere
     * @param mese del calendario dove e' presente il tour che si vuole rimuovere
     * @return true se il tour e' stato eliminato nella data specificata, false altrimenti
     */

    //TODO: aggiungere la questione del turista che ha già pagato il tour da eliminare
    @Override
    public boolean rimuoviDisponibilita (int giorno, int mese) {
        if (this.calendario.restituisciTour(giorno, mese) == null) throw new NullPointerException("In questa data non è presente alcun tour");
        HashSet<PrenotazioneTour> elencoPrenotazioni = this.calendario.restituisciTour(giorno, mese).getElencoPrenotazioni();
        for (PrenotazioneTour p : elencoPrenotazioni) {
            Turista turista = p.getTurista();
            System.out.println("Il tour non è piu' disponibile. " +
                    "La sua prenotazione e' gia' stata annullata. Ci scusiamo per l'inconvenente.");
            p.eliminaPrenotazione();
        }
        GestioneCalendario.getInstance().eliminaTourDalCalendario(giorno, mese, this.calendario);
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
    @Override
    public boolean creaTour(String nome, int numeroMin, int numeroMax, double prezzo, String descrizione){
        while (numeroMin < 1) {
        System.out.println("Il numero minimo di partecipanti al tour non deve essere minore di 1");
        System.out.println ("Inserire il numero minimo di partecipanti al tour: ");
        numeroMin = scanner.nextInt(); }
        while (numeroMax <= numeroMin) {
            System.out.println("Il numero massimo di partecipanti al tour deve essere superiore al numero minimo di partecipanti");
            System.out.println ("Inserire il numero massimo di partecipanti al tour: ");
            numeroMax = scanner.nextInt(); }
        while ( prezzo< 1.0 ) {
            System.out.println("Il prezzo del tour deve essere superiore a 0.99");
            System.out.println ("Inserire il prezzo del tour: ");
            prezzo = scanner.nextDouble(); }
        Tour tour = new Tour(nome, numeroMin, numeroMax, prezzo, descrizione, this.nome);
        System.out.println("Inserire il numero di giorni per la scadenza del tour: "+tour.getNome());
        //Scanner sc = new Scanner(System.in);
        tour.setGiorniScadenzaInvito(scanner.nextInt());
        while (tour.getGiorniScadenzaInvito() < 2){
            System.out.println("Inserire il nuovo numero di giorni per la scadenza del tour: "+tour.getNome());
            tour.setGiorniScadenzaInvito(scanner.nextInt());
          }
        this.elencoTour.add(tour);
        ListaTour.getInstance().aggiungiTour(tour);
        return true;
    }


    /**
     * Metodo che controlla il contenuto di una stringa (utilizzato per il controllo dei nomi e dei cognomi)
     *
     * @param stringa che deve essere controllata
     * @return true se la stringa contiene uno dei caratteri speciali, false altrimenti.
     */

    private boolean controlloStringa(String stringa){
        return Pattern.matches("[A-Za-z àèéòùì']*", stringa);
    }
    private boolean controlloStringaEmail (String stringa) {
        return Pattern.matches(".+@.+\\.[a-zA-Z0-9]+",stringa);
    }

    /**
     *Metodo che elimina un tour definitivamente dalla lista dei tour fatti dal singolo cicerone. Il tour è eliminato anche dal calendario.
     *
     * @param tour che si vuole eliminare definitivamente
     */

    //TODO: aggiungere la questione del turista che ha già pagato il tour da eliminare
    @Override
    public boolean eliminaTourDefinitivamente(Tour tour) {

        for (int i = 1; i < 32; i++) {
            for (int j = 1; j< 13; j++){
                TourCalendario tourCalendario = this.calendario.restituisciTour(i,j);
                if (tourCalendario == null);
                else if  (tourCalendario.getTour().getId() == tour.getId()) {
                    HashSet<PrenotazioneTour> elencoPrenotazioni = tourCalendario.getElencoPrenotazioni();
                    for (PrenotazioneTour p : elencoPrenotazioni) {
                        Turista turista = p.getTurista();
                        p.eliminaPrenotazione();
                        System.out.println("Il tour non è piu' disponibile. " +
                                "La sua prenotazione e' gia' stata annullata. Ci scusiamo per l'inconveniente.");
                    }
                    GestioneCalendario.getInstance().eliminaTourDalCalendario(i, j, calendario);
                }
            }
        }
        this.elencoTour.remove(tour);
        return true;
    }

    /**
     * Metodo per modificare un tour. La guida puo' modificare il prezzo del tour, la data del tour e il numero minimo di partecipanti la tour.
     * @param tour che si vuole modificare
     * @return true se la modifica vien fatta, false altrimenti
     */
    @Override
    public boolean modificaTour(Tour tour) {
        return true;
    }

    @Override
    public String toString() {
        return "Cicerone [nome=" + nome + ", cognome=" + cognome + ", email=" + email + "]";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Calendario getCalendario() {
        return calendario;
    }

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }

    public HashSet<Tour> getElencoTour() {
        return elencoTour;
    }

    public Tour visualizzaTour(String nome){
        for(Tour t: elencoTour){
            if(t.getNome() == nome){
                return t;
            }
        }
        return null;
    }

    public void setElencoTour(HashSet elencoTour) {
        this.elencoTour = elencoTour;
    }
}

