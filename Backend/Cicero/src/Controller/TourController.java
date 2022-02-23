package Controller;

import java.sql.SQLException;
import java.util.Scanner;

import DBManagers.*;
import Entity.Tour;

public class TourController {
    private static TourController tourController;
    private Scanner scannerTourController = new Scanner (System.in);

    private TourController () {}

    public static TourController getInstance() {
        if (tourController == null) {
            tourController = new TourController();
        } return tourController;
    }

    public String eseguiCreaTour (String email){
        int numeroMin;
        int numeroMax;
        double prezzo;
        String descrizione;
        String nome;
        System.out.println("Inserisci il nome del tour");
        nome = scannerTourController.nextLine();
        if (nome.equalsIgnoreCase("")) {
            nome = scannerTourController.nextLine();
        }
        do {
            System.out.println("Inserire una breve descrizione del tour.");
            descrizione = scannerTourController.nextLine();
            if (descrizione.length() > 200) {
                System.out.println("La descrizione è troppo lunga.");
            }
        }while(descrizione.length()>200 || descrizione.isEmpty());
        System.out.println("Inserisci il numero minimo di partecipanti al tour. Il numero non deve essere minore di 1");
        numeroMin = scannerTourController.nextInt();
        while (numeroMin < 1) {
            System.out.println("Il numero minimo di partecipanti al tour non deve essere minore di 1.");
            System.out.println ("Inserire il numero minimo di partecipanti al tour: ");
            numeroMin = scannerTourController.nextInt();
        }
        System.out.println("Il numero massimo di partecipanti al tour. Il numero deve essere maggiore di: "+numeroMin);
        numeroMax = scannerTourController.nextInt();
        while (numeroMax <= numeroMin) {
            System.out.println("Il numero massimo di partecipanti al tour deve essere superiore al numero minimo di partecipanti");
            System.out.println ("Inserire il numero massimo di partecipanti al tour: ");
            numeroMax = scannerTourController.nextInt();
        }
        System.out.println("Inserire il prezzo del tour.");
        prezzo = scannerTourController.nextDouble();
        while ( prezzo< 1.0 ) {
            System.out.println("Il prezzo del tour deve essere superiore a 0.99");
            System.out.println ("Inserire il prezzo del tour: ");
            prezzo = scannerTourController.nextDouble();
        }
        Tour tour = new Tour(nome, numeroMin, numeroMax, prezzo, descrizione);
        System.out.println("Inserire il numero di giorni per la scadenza dell'invito al tour: "+tour.getNome());
        tour.setGiorniScadenzaInvito(scannerTourController.nextInt());
        while (tour.getGiorniScadenzaInvito() < 2){
            System.out.println("ERRORE: Il numero di giorni non puo' essere inferiore a 2. Inserire il nuovo numero di giorni: ");
            tour.setGiorniScadenzaInvito(scannerTourController.nextInt());
        }
        int giorni = tour.getGiorniScadenzaInvito();
        try{
            String risp = "si";
            boolean sostaAggiunta = false;
            int i = 0;
            DBManagerInsert.InserisciNellaTabellaTour(nome, numeroMin, numeroMax, prezzo, descrizione, giorni, email);
            do{
                if(i == 0 || sostaAggiunta == false){
                    sostaAggiunta = SostaController.getInstance().eseguiAggiungiSosta(email,nome);
                }else{
                    SostaController.getInstance().eseguiAggiungiSosta(email,nome);
                }
                System.out.println("Vuoi aggiungere un'altra sosta? Inserisci si per aggiungerla.");
                risp = scannerTourController.nextLine();
                if(risp.equalsIgnoreCase("")){
                    risp = scannerTourController.nextLine();
                }
                i++;
            }while (risp.equalsIgnoreCase("si"));
            if(!sostaAggiunta){
                DBManagerDelete.RimuoviTourDallaTabella(nome,email);
                System.out.println("Non è stato possibile creare il tour. Ci scusiamo per il disagio.");
                return null;
            }
        }catch (SQLException | ClassNotFoundException e){
            System.out.println("Errore: non è possibile inserire i dati nel database.");
            return "Tour non creato";
        }
        return "Tour creato.";
    }

    public boolean eseguiEliminaDefinitivamenteTour (String emailCicerone) {
        String nomeTour;
        int[] disponibilita;
        nomeTour = selezionaTour(emailCicerone);
        try {
            disponibilita = DBManagerStampa.prendiIndiceDisponibilita(emailCicerone,nomeTour);
            if(disponibilita.length == 1 && disponibilita[0] == -1){
                DBManagerDelete.RimuoviTourDallaTabella(nomeTour, emailCicerone);
                return true;
            }
            for (int i = 0; i < disponibilita.length; i++)
            DisponibilitaController.getInstance().eseguiRimuoviDisponibilita2(emailCicerone, disponibilita[i]);
            DBManagerDelete.RimuoviTourDallaTabella(nomeTour, emailCicerone);
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Non è possibile accedere al database.");
            return false;
        }
        return true;
    }

    public boolean eseguiEliminaDefinitivamenteTourDatoIlNome (String emailCicerone, String nomeTour) {
        int[] disponibilita;
        try {
            disponibilita = DBManagerStampa.prendiIndiceDisponibilita(emailCicerone,nomeTour);
            if(disponibilita.length == 1 && disponibilita[0] == -1){
                DBManagerDelete.RimuoviTourDallaTabella(nomeTour, emailCicerone);
                return true;
            }
            for (int i = 0; i < disponibilita.length; i++)
                DisponibilitaController.getInstance().eseguiRimuoviDisponibilita2(emailCicerone, disponibilita[i]);
            DBManagerDelete.RimuoviTourDallaTabella(nomeTour, emailCicerone);
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Non è possibile accedere al database.");
            return false;
        }
        return true;
    }

    public boolean eseguiAggiungiSosta(String emailCicerone){
        String nomeTour = selezionaTour(emailCicerone);
        return SostaController.getInstance().eseguiAggiungiSosta(emailCicerone,nomeTour);
    }

    public boolean eseguiEliminaSosta(String emailCicerone){
        String nomeTour = selezionaTour(emailCicerone);
        return SostaController.getInstance().eseguiEliminaSosta(emailCicerone,nomeTour);
    }

    public boolean eseguiModificaNomeTour(String email){
        String vecchioNomeTour = selezionaTour(email);
        System.out.println("Tour preso: "+vecchioNomeTour);
        String nuovoNome;
        boolean valoriCorretti = false;
        while(!valoriCorretti) {
            try {
                System.out.println("Inserisci il nuovo nome da dare al tour.");
                nuovoNome = scannerTourController.nextLine();
                if(nuovoNome.equalsIgnoreCase("")){
                    nuovoNome = scannerTourController.nextLine();
                }
                DBManagerUpdate.cambiaNomeDelTour(nuovoNome, vecchioNomeTour, email);
                valoriCorretti = true;
                break;
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Hai gia' un tour con questo nome.");
            }
        }
        return true;
    }

    public boolean eseguiModificaDescrizioneTour(String email){
        String nomeTour = selezionaTour(email);
        String nuovaDescrizione;
        boolean valoriCorretti = false;
        String vecchiaDescrizione;
        while(!valoriCorretti) {
            try {
                vecchiaDescrizione = DBManagerStampa.visualizzaDescrizioneTour(nomeTour,email);
                System.out.println("Tour preso: "+nomeTour+", descrizione: "+vecchiaDescrizione);
                System.out.println("Inserisci la nuova descrizione da dare al tour.");
                nuovaDescrizione= scannerTourController.nextLine();
                if(nuovaDescrizione.equalsIgnoreCase("")){
                    nuovaDescrizione = scannerTourController.nextLine();
                }
                DBManagerUpdate.cambiaDescrizioneTour(email, nomeTour, nuovaDescrizione, vecchiaDescrizione);
                valoriCorretti = true;
                break;
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Descrizione non accettata.");
            }
        }
        return true;
    }

    public boolean eseguiModificaPrezzoTour(String email) {
        String nomeTour = selezionaTour(email);
        double prezzoVecchio;
        double nuovoPrezzo;
        int[] disponibilita;
        boolean valoriCorretti = false;
        String avviso;
        while(!valoriCorretti) {
            try {
                prezzoVecchio = DBManagerStampa.visualizzaPrezzoTour(nomeTour,email);
                System.out.println("Tour preso: "+nomeTour+"; prezzo attuale del tour: "+prezzoVecchio);
                System.out.println("Inserisci il nuovo prezzo da dare al tour.");
                nuovoPrezzo = scannerTourController.nextDouble();
                disponibilita = DBManagerStampa.prendiIndiceDisponibilita(email,nomeTour);
                while ( nuovoPrezzo< 1.0 ) {
                    System.out.println("Il prezzo del tour deve essere superiore a 0.99");
                    System.out.println ("Inserire il prezzo del tour: ");
                    nuovoPrezzo = scannerTourController.nextDouble();
                }
                if(prezzoVecchio<nuovoPrezzo){
                    avviso = "Il tour: "+nomeTour+", ha subito un aumento di prezzo.";
                }else{
                    avviso = "Il tour: "+nomeTour+", ha subito una diminuzione di prezzo.";
                }
                DBManagerUpdate.cambiaPrezzoDelTour(prezzoVecchio,nuovoPrezzo, email, nomeTour);
                if(disponibilita[0] == -1){
                }else{
                    for (int i = 0; i < disponibilita.length; i++) {
                        DisponibilitaController.getInstance().eseguiInviaAvvisoAiTuristiCheHannoPrenotato(email, disponibilita[i], avviso);
                    }
                    valoriCorretti = true;
                }
                break;
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println("Prezzo non valido.");
            }
        }
        return true;

    }

    /**
     * Metodo che modifica il numero di posti di un tour.
     * @param email del cicerone
     * @return true se il numero di posti è stato cambiato, false altrimenti.
     */
    public boolean eseguiModificaPostiTour(String email) {
        String nomeTour = selezionaTour(email);
        int nuovoNumeroPosti;
        int vecchioNumeroPostiTour = 0;
        int postiMinimi = 0;
        try {
            vecchioNumeroPostiTour = DBManagerStampa.visualizzaNumeroMassimoPosti(nomeTour,email);
            postiMinimi = DBManagerStampa.visualizzaNumeroMinimoPosti(nomeTour, email);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Non è stato possibile accedere al database.");
            return false;
        }
        System.out.println("Tour preso: "+nomeTour+"; attuale numero massimo di posti del tour: "+vecchioNumeroPostiTour);
        System.out.println("Inserisci il nuovo numero di posti massimo per il tour.");
        nuovoNumeroPosti = scannerTourController.nextInt();
        int postiPrenotati = DisponibilitaController.getInstance().ritornaPostiMassimiPrenotatiNelleDisponibilitaDiUnTour(nomeTour,email);
        if(postiPrenotati == -1){
            return false;
        }
        while(nuovoNumeroPosti< postiPrenotati || nuovoNumeroPosti<= postiMinimi){
            if(postiPrenotati == 0 || postiPrenotati<=postiMinimi){
                System.out.println("Numero non valido. Inserire un valore superiore o uguale a: "+postiMinimi);
            }else{
                System.out.println("Numero non valido. Inserire un valore superiore o uguale a: "+postiPrenotati);
            }
            nuovoNumeroPosti = scannerTourController.nextInt();
        }
        try {
            DBManagerUpdate.cambiaNumeroMassimoPostiTour(email, nomeTour, nuovoNumeroPosti, vecchioNumeroPostiTour);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Non è stato possibile accedere al database.");
            return false;
        }
        return true;
    }

    /**
     * Metodo che stampa l'elenco dei tour dei Ciceroni
     * @param email
     */
    public void stampaElencoTourCiceroni (String email) {
        try{
            DBManagerStampa.visualizzaITuoiTour(email);
        }catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non è possibile accedere al database.");
        }
    }

    public boolean stampaElencoTourCiceroneConDettagli (String email) {
        try{
            DBManagerStampa.visualizzaITuoiTourConDettagli(email);
            if(!DBManagerConfronti.verificaEsistenzaTourPerCicerone(email)){
                return false;
            }
            System.out.println("Se vuoi visualizzare le soste di un tour inserisci il suo id, altrimenti inserisci 0 per tornare al menu.");
            int risposta = scannerTourController.nextInt();
            while(!DBManagerConfronti.confrontaIdTour(risposta) && risposta != 0){
                System.out.println("Risposta non valida. Inserisci o l'id di un tour per visualizzarne le soste o 0 per tornare al menu.");
                risposta = scannerTourController.nextInt();
            }
            if(risposta == 0){
                return false;
            }else{
                String nomeTour = DBManagerStampa.prendiNomeTourDaTabellaTour(risposta);
                DBManagerStampa.visualizzaSoste(nomeTour, email);
            }
        }catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non è possibile accedere al database.");
        }
        return true;
    }

    //Metodo per visualizzare i tour del sistema e vederne le soste.
    public boolean stampaElencoTourConDettagli () {
        try{
            DBManagerStampa.visualizzaTourConDettagli();
            System.out.println("Se vuoi visualizzare le soste di un tour inserisci il suo id, altrimenti inserisci 0 per tornare al menu.");
            int risposta = scannerTourController.nextInt();
            while(!DBManagerConfronti.confrontaIdTour(risposta) && risposta != 0){
                System.out.println("Risposta non valida. Inserisci o l'id di un tour per visualizzarne le soste o 0 per tornare al menu.");
                risposta = scannerTourController.nextInt();
            }
            if(risposta == 0){
                return false;
            }else{
                String nomeTour = DBManagerStampa.prendiNomeTourDaTabellaTour(risposta);
                String email = DBManagerStampa.prendiEmailCiceroneDaTour(risposta);
                DBManagerStampa.visualizzaSoste(nomeTour, email);
            }
        }catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non è possibile accedere al database.");
        }
        return true;
    }

    /**
     * Metodo che stampa tutti i tour presenti nel programma.
     */
    public boolean eseguiVisualizzaTour () {
        try{
            if(!DBManagerStampa.visualizzaTourConDettagli()){
                return false;
            }
            return true;
        }catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non è possibile accedere al database.");
            return false;
        }
    }

    public String selezionaTour(String email){
        System.out.println("Seleziona il tour: ");
        stampaElencoTourCiceroni(email);
        int id_tour;
        String nomeTour = "";
        id_tour = scannerTourController.nextInt();
        try{
            while(!DBManagerConfronti.confrontaIdTour(id_tour)){
                System.out.println("Non esiste un tour con questo id. Inserisci un nuovo id.");
                id_tour = scannerTourController.nextInt();
            }
            nomeTour = DBManagerStampa.prendiNomeTourDaTabellaTour(id_tour); }
        catch(SQLException | ClassNotFoundException e){
            System.out.println("Non è possibile accedere al database.");
        }
        return nomeTour;
    }

}
