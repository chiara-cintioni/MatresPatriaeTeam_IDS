package Entity;

import Controller.InvitoController;
import DBManagers.DBManagerDelete;
import DBManagers.DBManagerStampa;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.Date;
import java.util.Timer;


public class Invito {
    private String nomeInvitato;
    private String cognomeInvitato;
    private String emailInvitato;
    private int postiRiservati;
    private String emailCicerone;
    private String nomeTour;
    private String dataTour;
    private Timer timer;
    private Scanner scanner = new Scanner(System.in);
    private Date dataDiCreazione;

    public Invito(String nomeInvitato, String cognomeInvitato, String emailInvitato, int postiRiservati, String emailCicerone, String nomeTour, String dataTour) {
        this.nomeInvitato = nomeInvitato;
        this.cognomeInvitato = cognomeInvitato;
        this.emailInvitato = emailInvitato;
        this.postiRiservati = postiRiservati;
        this.emailCicerone = emailCicerone;
        this.nomeTour = nomeTour;
        this.dataTour = dataTour;
        this.timer = new Timer();
        this.dataDiCreazione = new Date();
        avvioTimer(this.nomeTour, this.emailCicerone, this.emailInvitato);
    }


    /**
     * Metodo che serve per avviare il timer di scadenza per la conferma di un invito e nel momento in
     * cui scade, l'invito viene eliminato automaticamente dall'elenco degli inviti di un tour.
     *
     */
    public void avvioTimer(String nomeTour, String emailCicerone, String emailInvitato){
        TimerTask oggettoElimina = new eliminaInvito();
        Date dataScadenza = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dataScadenza);
        try{
            int giorniScadenzaInvito = DBManagerStampa.visualizzaGiorniScadenzaInvito(nomeTour,emailCicerone);
            c.add(Calendar.DATE, giorniScadenzaInvito);
            dataScadenza = c.getTime();
            System.out.println("Data scadenza: "+ dataScadenza); //Per vedere che funziona il timer.
            this.timer.schedule(oggettoElimina, dataScadenza);
        }catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non è possibile accedere al database.");
        }
    }





    /**
     * Classe per creare una TimerTask che serve per eliminare un invito dall'elenco degli inviti di un tour
     * quando il timer scade.
     */
    private class eliminaInvito extends TimerTask{
        public void run(){
            InvitoController.getInstance().eseguiEliminaInvito(nomeTour, emailCicerone, emailInvitato);
            System.out.println("Invito Cancellato");
        }
    }

    public String getNomeInvitato() {
        return nomeInvitato;
    }

    public String getCognomeInvitato() {
        return cognomeInvitato;
    }

    public String getEmailInvitato() {
        return emailInvitato;
    }

    public int getPostiRiservati() {
        return postiRiservati;
    }

    public String getEmailCicerone() {
        return emailCicerone;
    }

    public String getNomeTour() {
        return nomeTour;
    }

    public String getDataTour() {
        return dataTour;
    }

    public Timer getTimer() {
        return timer;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public Date getDataDiCreazione() {
        return dataDiCreazione;
    }
}
