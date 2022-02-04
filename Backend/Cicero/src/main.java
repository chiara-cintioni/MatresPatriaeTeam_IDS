import java.net.CacheRequest;
import java.util.TimerTask;

public class main {
    public static void main (String[] args) {
        Amministrazione amm = Amministrazione.getInstance();
        amm.stampa();
       /* amm.creaToponimo();
        amm.creaToponimo();
        amm.creaToponimo();
        amm.creaToponimo();
        ListaTag.getInstance().stampaElencoTag();
        GestoreToponimi.getInstance().stampaElenchiToponimi();*/
       Cicerone cicerone = new Cicerone("Gianni", "D'Allo", "gianni.dAllo@gmail.com");
        System.out.println(cicerone.toString());
        Cicerone cicerone2 = new Cicerone("Lucia", "Rosè", "ludrsa00@yahoo.it");
        System.out.println(cicerone2.toString());
        cicerone.creaTour("Vacanze romane", 1, 10, 15.99, "Bellissime vacanze romane a Piacenza.");
       cicerone.creaTour("Casa mia", 2, 33, 30, "Bellissime vacanze romane a Piacenza.");
        cicerone.creaTour("Torino", 6, 30, 23, "Tutti a Torinooooo.");
       // System.out.println(cicerone.visualizzaTour("Vacanze romane"));
        //Aggiungo un po' di disponibilita' per controllare che vangano inserite correttamente nel calendario di Gianni.

        cicerone.aggiungiDisponibilita(cicerone.visualizzaTour("Vacanze romane"), 30, 11);
        cicerone.aggiungiDisponibilita(cicerone.visualizzaTour("Vacanze romane"), 1, 1);
        cicerone.aggiungiDisponibilita(cicerone.visualizzaTour("Vacanze romane"), 30, 6);
        cicerone.aggiungiDisponibilita(cicerone.visualizzaTour("Vacanze romane"), 30, 9);
        cicerone.aggiungiDisponibilita(cicerone.visualizzaTour("Vacanze romane"), 2, 2);
        cicerone.aggiungiDisponibilita(cicerone.visualizzaTour("Vacanze romane"), 31, 10);
        cicerone.aggiungiDisponibilita(cicerone.visualizzaTour("Vacanze romane"), 12, 1);
        cicerone.aggiungiDisponibilita(cicerone.visualizzaTour("Casa mia"), 3, 10);
        cicerone.aggiungiDisponibilita(cicerone.visualizzaTour("Casa mia"), 18, 7);
        cicerone.aggiungiDisponibilita(cicerone.visualizzaTour("Casa mia"), 29, 4);
        cicerone.aggiungiDisponibilita(cicerone.visualizzaTour("Torino"), 15, 1);
        cicerone.aggiungiDisponibilita(cicerone.visualizzaTour("Torino"), 31, 3);
        cicerone.aggiungiDisponibilita(cicerone.visualizzaTour("Torino"), 1, 10);

       cicerone.visualizzaTour("Vacanze romane").visualizzaDateDelTour();
        cicerone.visualizzaTour("Casa mia").visualizzaDateDelTour();
        cicerone.visualizzaTour("Torino").visualizzaDateDelTour();

        Turista gino = new Turista("Gino", "Rossi", "gino.rossi@gmail.com", "GNRS");
        Turista salvo = new Turista("Salvo", "Verdi", "salvo.verdi@gmail.com", "SLVVRD");
       gino.eseguiPrenotazione();
       gino.eseguiPrenotazione();
       gino.eseguiPrenotazione();
       salvo.eseguiPrenotazione();
        cicerone.getCalendario().restituisciTour(1,1).stampaElencoPrenotazioni();
        gino.annullaPrenotazione();
        cicerone.getCalendario().restituisciTour(1,1).stampaElencoPrenotazioni();
        gino.annullaPrenotazione();
        cicerone.getCalendario().restituisciTour(1,1).stampaElencoPrenotazioni();

      /* System.out.println("Posti disponibili dopo la prenotazione del tour: "+cicerone.getCalendario().restituisciTour(1,1).getPostiDisponibili());
        System.out.println("Posti prenotati dopo la prenotazione del tour: "+cicerone.getCalendario().restituisciTour(1,1).getTotalePostiPrenotati());
       cicerone.getCalendario().restituisciTour(1,1).stampaElencoPrenotazioni();
        cicerone.getCalendario().restituisciTour(1,1).stampaElencoInviti();

        cicerone.getCalendario().restituisciTour(1,1).stampaElencoInviti();


        //Metodo per stampare l'elenco delle date di un tour
       /* for (TourCalendario t : cicerone.visualizzaTour("Vacanze romane").getElencoDisponibilitaDelTour()) {
            System.out.println("Il tour "+ t.getTour().getNome()+" è disponibile nella seguente data: "+t.getGiorno()+ "/"+t.getMese());
        }*/

      /*  Calendario calendario = cicerone.getCalendario();
        calendario.stampaCalendarioDellaGuida();

     /*
        gino.eseguiPrenotazione(calendario.restituisciTour(1,1), 2);
        for (PrenotazioneTour p : calendario.restituisciTour(1,1).getElencoPrenotazioni()){
            System.out.println("Il turista:" + p.getTurista().getNome()+ " ha prenotato:"+ p.getTourCalendario().getTour().getNome() );}
        Boolean oriva = cicerone.eliminaTourDefinitivamente(cicerone.visualizzaTour("Vacanze romane"));
        System.out.println(oriva);
        cicerone.getCalendario().stampaGriglia();

      // System.out.println(calendario.restituisciTour(1,1).getTour().getNome());
       //System.out.println(calendario.restituisciTour(5,2).getTour().getNome());
        System.out.println("\n");
        for(Tour tour: cicerone.getElencoTour()){
            System.out.println(tour.getNome());
        }
       /* for (PrenotazioneTour p : calendario.restituisciTour(1,1).getElencoPrenotazioni()){
            System.out.println("Il turista:" + p.getTurista().getNome()+ " ha prenotato:"+ p.getTourCalendario().getTour().getNome() );}*/


       //System.out.println(calendario.restituisciTour(23,7).getTour().getNome());
        // Stampa il campo da gioco.
      // cicerone.getCalendario().stampaGriglia();


        /*cicerone.creaTour("Casa mia", 2, 33, 30, "Bellissime vacanze romane a Piacenza.", cicerone.getNome());

        System.out.println(cicerone.visualizzaTour("Casa mia"));

        cicerone2.creaTour("Parigi di sera", 5, 6, 200, "Parigi di sera non e' mai stata piu' bella.", cicerone2.getNome());
        System.out.println(cicerone2.visualizzaTour("Parigi di sera"));
        cicerone.aggiungiDisponibilita(cicerone.visualizzaTour("Casa mia"), 5, 1);
        System.out.println("Il tour nel giorno 5, mese 1, di Gianni e':" +cicerone.getCalendario().restituisciTour(5, 1).getTour().getNome());
        cicerone.aggiungiDisponibilita(cicerone.visualizzaTour("Casa mia"), 6, 1);
        System.out.println("Il tour nel giorno 6, mese 1, di Gianni e':" +cicerone.getCalendario().restituisciTour(6, 1).getTour().getNome());
        cicerone2.aggiungiDisponibilita(cicerone2.visualizzaTour("Parigi di sera"), 6, 1);
        System.out.println("Il tour nel giorno 6, mese 1, di Lucia e':" +cicerone2.getCalendario().restituisciTour(6, 1).getTour().getNome());
        //Punto  esclamativo perche' voglio sapere se il tour e' presente o meno e  questo metodo ritorna true se non è presente, quindi li inverto.
        Boolean rimossoTour = !cicerone.rimuoviDisponibilita(6, 1);
        System.out.println("Il tour nel giorno 6, mese 1, di Gianni e' ancora presente: " +rimossoTour);
        //controllo che il rimuovi disponibilita' di Gianni non abbia influito anche sul tour di Lucia. Di nuovo, metto ! perche' ritorna true se il tour
        //non c'e' e false altrimenti, e io voglio il contrario.
        Boolean rimossoTour2 = !GestioneCalendario.getInstance().controlloDisponibilitaData(6, 1, cicerone2.getCalendario());
        System.out.println("Il tour nel giorno 6, mese 1, di Lucia e' ancora presente: " +rimossoTour2);
        cicerone.aggiungiDisponibilita(cicerone.visualizzaTour("Casa mia"), 30, 3);
        //Questo ciclo serve a controllare che nonostante ho più tourCalendario, il tour singolo rimane 1 solo.
        for(Tour tour: cicerone.getElencoTour()){
            System.out.println(tour.getNome());
        }

        //Faccio la stessa cosa per Lucia per assicurarmi che non siano uguali i calendari.
        Calendario cal = cicerone2.getCalendario();
        for(int i = 0; i<31; i++){
            for(int j = 0; j<12; j++){
                if(cal.restituisciTour(i,j)!=null){
                    System.out.println("Il tour di Lucia presente nel giorno: "+ i+ " e mese: "+ j+ " e': "+cal.restituisciTour(i,j).getTour().getNome());
                }
            }
        }
        cicerone.eliminaTourDefinitivamente(cicerone.visualizzaTour("Casa mia"));
        System.out.println("\n");
        Calendario calendario1 = cicerone.getCalendario();
        for(int i = 0; i<31; i++){
            for(int j = 0; j<12; j++){
                if(calendario1.restituisciTour(i,j)!=null){
                    System.out.println("Il tour di Gianni presente nel giorno: "+ i+ " e mese: "+ j+ " e': "+calendario1.restituisciTour(i,j).getTour().getNome());
                }
            }
        }
        System.out.println("\n");
        for(Tour tour: cicerone.getElencoTour()){
            System.out.println(tour.getNome());
        }*/
    }
}