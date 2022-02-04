import java.lang.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Pattern;
//import lombok.Data;



//@Data

/**
 * Classe che crea un turista. il turista ha: un nome, un cognome, un email e un codice fiscale.
 */
public class Turista implements UtenteGenerico {

    private String nome;
    private String cognome;
    private String email;
    private final String codiceFiscale;
    private ArrayList<PrenotazioneTour> elencoPrenotazioniDelTurista;
    private Scanner scanner = new Scanner(System.in);


    public Turista(String nome, String cognome, String email, String codiceFiscale) {
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

this.elencoPrenotazioniDelTurista = new ArrayList<PrenotazioneTour>();
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.codiceFiscale = codiceFiscale;

    }

    private boolean controlloStringa(String stringa){
        return Pattern.matches("[A-Za-z àèéòùì']*", stringa);
    }
    private boolean controlloStringaEmail (String stringa) {
        return Pattern.matches(".+@.+\\.[a-zA-Z0-9]+",stringa);
    }

    /**
     * Metodo che permette al turista di eseguire una prenotazione.
     *
     *
     * @return true se la prenotazione è avvenuta, false altrimenti.
     */
    public boolean eseguiPrenotazione () {
        int tentativiCoglione = 0;
        Tour tourDaPrenotare = scegliTour();
        TourCalendario tourDAppoggio = scegliData(tourDaPrenotare);
        int postiDaPrenotare;
        System.out.println("Inserire il numero di posti che si vuole prenotare: ");
      postiDaPrenotare = scanner.nextInt();
      String rispostaTurista;
      while(!GestioneCalendario.getInstance().controlloDisponibilitaPosti(tourDAppoggio, postiDaPrenotare) ) {
          System.out.println("I posti da lei inseriti non sono disponibili.");
          System.out.println("Vuole ancora prenotare cambiando il numero di posti da prenotare? Inserire SI o NO");
         rispostaTurista = scanner.next();
          while (!rispostaTurista.equalsIgnoreCase("SI")&& !rispostaTurista.equalsIgnoreCase("NO") && tentativiCoglione <=5) {
              System.out.println("Risposta non valida. Inserire SI o NO");
              rispostaTurista = scanner.nextLine();
              tentativiCoglione++;
          }
          if (rispostaTurista.equalsIgnoreCase("si")){
              System.out.println("Inserire un numero minore di: "+tourDAppoggio.getPostiDisponibili());
              postiDaPrenotare = scanner.nextInt();
          }
          else if (rispostaTurista.equalsIgnoreCase("no")) {
              System.out.println ("Operazione di prenotazione annullata");
              return false;
          }
          else {
              System.out.println("Superato il numero massimo di tentativi. L'operazione di Prenotazione e' stata annullata");
              return false;
          }
      }
      tentativiCoglione = 0;
      PrenotazioneTour prenotazione = new PrenotazioneTour(tourDAppoggio, this,postiDaPrenotare);
      tourDAppoggio.decrementaPostiDisponibili(postiDaPrenotare);
      tourDAppoggio.incrementaPostiPrenotati(postiDaPrenotare);
      prenotazione.aggiungiPrenotazione();
      elencoPrenotazioniDelTurista.add(prenotazione);
        System.out.println("Vuole proporre il tour a un amico? Inserire SI o NO");
        rispostaTurista = scanner.next();

        while (!rispostaTurista.equalsIgnoreCase("SI")&& !rispostaTurista.equalsIgnoreCase("NO") && tentativiCoglione <=5) {
            System.out.println("Risposta non valida. Inserire SI o NO");
            rispostaTurista = scanner.nextLine();
            tentativiCoglione++;
        }
        boolean creazione = true;
        if (rispostaTurista.equalsIgnoreCase("si")){
           creazione = creaInvito(tourDAppoggio);

        } else {
            System.out.println ("Operazione di prenotazione effettuata con successo");

            return true;
        }

        while(tourDAppoggio.getPostiDisponibili() > 0 && creazione == true) {

        System.out.println("Vuole creare un altro invito? Inserire SI o NO");
        rispostaTurista = scanner.next();
        if (rispostaTurista.equalsIgnoreCase("si")){
            creazione = creaInvito(tourDAppoggio);
         } else {
            break;
            }
        }
        InvioInvito();
       System.out.println ("Operazione di prenotazione effettuata con successo");

       return true;
    }

    /**
     * Metodo che permette al turista di scegliere una data da prenotare
     * @param tour che si vuole prenotare
     * @return il tour nella data scelta per la prenotazione
     */
    private TourCalendario scegliData(Tour tour) {
        tour.visualizzaDateDelTour();
        int indice;
        System.out.println ("Inserire l'indice della data che si vuole selezionare");
         indice = scanner.nextInt();
        while (indice > tour.getElencoDisponibilitaDelTour().size() || indice < 0) {
            System.out.println ("Non esiste un tour con quel indice");
            indice = scanner.nextInt();
        }
        return tour.getTourCalendario(indice);
    }


    /**
     * Metodo utilizzato per invitare un amico a partecipare al tour. Viene creato l'invito
     * @param tour al quale invitare un contato
     * @return true se l'invito e' stato inviato, false altrimenti
     */
    public boolean creaInvito(TourCalendario tour){
        int tentativiCoglione = 0;
        String rispostaTurista;
        int postiDaRiservare;
        System.out.println("Inserire il numero di posti che si vuole riservare per l'invitato: ");
        postiDaRiservare = scanner.nextInt();
        while(!GestioneCalendario.getInstance().controlloDisponibilitaPosti(tour, postiDaRiservare) ) {
            System.out.println("I posti da lei inseriti non sono disponibili.");
            System.out.println("Vuole ancora creare un invito cambiando il numero di posti da riservare? Inserire SI o NO");
            rispostaTurista = scanner.next();
            if (rispostaTurista.equalsIgnoreCase("si")){
                System.out.println("Inserire un numero minore di: "+tour.getPostiDisponibili());
                postiDaRiservare = scanner.nextInt(); }
            else {
                return false;}
        }
        Invito invito = new Invito (tour, postiDaRiservare);
        System.out.println("Invito Creato");
        invito.aggiungiInvito();
        tour.decrementaPostiDisponibili(postiDaRiservare);
        invito.avvioTimer();

        return true;
    }



private void InvioInvito () {
        System.out.println ("L'invito e' stato inoltrato al contatto da lei indicato.");
}


    /**
     * Metodo che permette al turista di annullare una prenotazione

     * @return true se la prenotazione viene annullata, false altrimenti
     */
    public boolean annullaPrenotazione () {
        visualizzaElencoPrenotazioni();
        int indice;
        System.out.println ("Inserire l'indice della prenotazione che si vuole annullare");
        indice = scanner.nextInt();
        while (indice > elencoPrenotazioniDelTurista.size() || indice < 0) {
            System.out.println ("Non esiste una prenotazione con quel indice. Inserire un nuovo indice");
            indice = scanner.nextInt();
        }
       PrenotazioneTour prenotazione = ritornaPrenotazione(indice);
        int tentativiCoglione = 0;
        String rispostaTurista;
if (prenotazione.isAnnullaGratis() == false) {
    System.out.println ("Il tempo per annullare una prenotazione gratis e' scaduto. Se decide di confermare l'annullamento deve pagare una mora.");
    System.out.println ("Vuole confermare l'annullamento della prenotazione? Inserire SI o NO");
    rispostaTurista = scanner.nextLine();
    while (!rispostaTurista.equalsIgnoreCase("SI")&& !rispostaTurista.equalsIgnoreCase("NO") && tentativiCoglione <=5){
        System.out.println("Risposta non valida. Inserire SI o NO");
        rispostaTurista = scanner.nextLine();
        tentativiCoglione++;
    }
    if (!rispostaTurista.equalsIgnoreCase("SI")) {
        // mi ritorna true se la prenotazione e' stata pagato, false altrimenti.
         if (prenotazione.isStatoPrenotazione() == true) {
                 prenotazione.richiestaRimborso();
             }
    else {

        return false; }
    }
}
prenotazione.getTourCalendario().incrementaPostiDisponibili(prenotazione.getNumeroPostiDaPrenotare());
prenotazione.getTourCalendario().decrementaPostiPrenotati(prenotazione.getNumeroPostiDaPrenotare());
prenotazione.eliminaPrenotazione();
elencoPrenotazioniDelTurista.remove(prenotazione);

               return true;
    }

    private PrenotazioneTour ritornaPrenotazione(int indice) {
        return elencoPrenotazioniDelTurista.get(indice);
    }

    public void visualizzaElencoPrenotazioni() {
        int cont =0;
        for (PrenotazioneTour p : elencoPrenotazioniDelTurista) {
                System.out.println("Nome del Tour prenotato: " + p.getTourCalendario().getTour().getNome() + ", data del tour: [" + p.getTourCalendario().getGiorno() + ", " + p.getTourCalendario().getMese() + "] ,indice della prenotazione: " + cont);
            cont++;
        }
    }


    /**
     * Metodo utilizzato per accettare un invito. 
     * @param invito da accettare
     */
    // TODO: 27/01/2022  
    public void accettaInvito(Invito invito){
    }

    /**
     * Metodo per il pagamento del tour
     * @param prenotazione che si vuole pagare
     * @return true se il pagamento è avvenuto, false altrimenti.
     */
    // TODO: 27/01/2022  
    public boolean effettuaPagamento(PrenotazioneTour prenotazione){
        return true;
    }


    /**
     * Metodo che serve per visualizzare un tour
     */
    @Override
    public void visualizzaTuttiTour(){
         ListaTour.getInstance().visualizzaTourDellaLista();
    }



    @Override
    public String rispondiAllInvito(Invito invito) {
        return "Invito accettato/rifiutato";
    }


    /**
     * Metodo per scegliere un tour
     * @return il tour che si vuole visualizzare
     */
    @Override
    public Tour scegliTour() {
       visualizzaTuttiTour();
       System.out.println("Inserire il codice id del tour che si vuole visualizzare: ");
      int id =  scanner.nextInt();
      Tour t = ListaTour.getInstance().getTour(id);
        while (t == null) {
                     System.out.println("Non esiste un tour con codice id = "+id);
            id =  scanner.nextInt();
            t=ListaTour.getInstance().getTour(id);
        }
      System.out.println(t.toString());

return t;
    }


    /**
     * Metodo per inserire i dati della carta di credito che si vuole utilizzare per pagare
     * @return true se i dati inseriti sono tutti corretti, false altrimenti
     */
    //TODO: 27/01/2022
    public boolean inserisciCartaDiCredito(){
        return true;
    }

    /**
     * Metodo per annullare un pagamento
     * @param prenotazione di cui si vuole annullare il pagamento
     * @return true se l'annullamento è avvenuto, false altrimenti
     */
    // TODO: 27/01/2022
    public boolean annullaPagamento(PrenotazioneTour prenotazione){
        return true;
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

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public ArrayList<PrenotazioneTour> getElencoPRenotazioniDelTurista() {
        return elencoPrenotazioniDelTurista;
    }

    public void setElencoPRenotazioniDelTurista(ArrayList<PrenotazioneTour> elencoPRenotazioniDelTurista) {
        this.elencoPrenotazioniDelTurista = elencoPRenotazioniDelTurista;
    }
}
