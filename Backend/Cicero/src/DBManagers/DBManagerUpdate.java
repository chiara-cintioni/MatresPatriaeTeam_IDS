package DBManagers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManagerUpdate {
    private static DBManagerUpdate dbManagerUpdate;
    private final static String url = "jdbc:postgresql://localhost:5432/Cicero";
    private final static String user = "postgres";
    private final static String password = "cicero";
    private final static String driver = "org.postgresql.Driver";

    private DBManagerUpdate () {}

    public DBManagerUpdate getIstance () {
        if (dbManagerUpdate == null) {
            dbManagerUpdate = new DBManagerUpdate();
        }
        return dbManagerUpdate;
    }

    /**
     * Metodo per cambiare il nome del tour
     * @param nuovoNome da dare al tour
     * @param vecchioNome da modificare del tour
     * @param emailCicerone del cicerone che possiede il tour
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void cambiaNomeDelTour(String nuovoNome, String vecchioNome, String emailCicerone) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "UPDATE tour SET nome = '"+nuovoNome+"' where nome = '"+vecchioNome+"' AND emailCicerone = '"+emailCicerone+"'";
        Statement stm = c.createStatement();
        stm.executeUpdate(stringa);
        c.close();
    }

    /**
     * Metodo per cambiare il prezzo di un tour
     * @param prezzoVecchio del tour
     * @param prezzoNuovo del tour
     * @param emailCicerone del cicerone che vuole cambiare il prezzo al tour
     * @param nomeTour del tour al quale cambiare il prezzo
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void cambiaPrezzoDelTour (double prezzoVecchio, double prezzoNuovo, String emailCicerone, String nomeTour) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "UPDATE tour SET prezzo = "+prezzoNuovo+" where prezzo = "+prezzoVecchio+" AND emailCicerone = '"+emailCicerone+"' AND nome = '"+nomeTour+"'" ;
        Statement stm = c.createStatement();
        stm.executeUpdate(stringa);
        c.close();
    }

    /**
     * Metodo per cambiare la descrizione di un tour
     * @param emailCicerone del cicerone che possiede il tour
     * @param nomeTour del tour a cui si vuole modficare la descrizione
     * @param descrizioneNuova del tour che si vuole modificare
     * @param descrizioneVecchia del tour che si vuole modificare
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void cambiaDescrizioneTour (String emailCicerone, String nomeTour, String descrizioneNuova, String descrizioneVecchia) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "UPDATE tour SET descrizione = '"+descrizioneNuova+"' where descrizione = '"+descrizioneVecchia+"' AND emailCicerone = '"+emailCicerone+"' AND nome = '"+nomeTour+"'" ;
        Statement stm = c.createStatement();
        stm.executeUpdate(stringa);
        c.close();
    }

    /**
     * Metodo per modificare il numero massimo di posti prenotabili di un tour
     * @param emailCicerone del cicerone che possiede il tour
     * @param nomeTour del tour che si vuoel modificare
     * @param nuovoValore da inserire al numero massimo di posti prenotabili del tour
     * @param vecchioValore del numero massimo di posti prenotabili del tour
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void cambiaNumeroMassimoPostiTour (String emailCicerone, String nomeTour, int nuovoValore, int vecchioValore) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "UPDATE tour SET numeroMassimo = "+nuovoValore+" where numeroMassimo = "+vecchioValore+" AND emailCicerone = '"+emailCicerone+"' AND nome = '"+nomeTour+"'" ;
        Statement stm = c.createStatement();
        stm.executeUpdate(stringa);
        c.close();
    }

    /**
     * Metodo per cambiare la data di un tour nella tambella disponibilita
     * @param emailCicerone del cicerone che possiede il tour
     * @param nomeTour del tour che si vuole modificare
     * @param nuovaData da dare al tour
     * @param vecchiaData del tour
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void cambiaDataDelTour (String emailCicerone, String nomeTour, String nuovaData, String vecchiaData) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "UPDATE disponibilita SET dataTour = '"+nuovaData+"' where dataTour = '"+vecchiaData+"' AND emailCicerone = '"+emailCicerone+"' AND nomeTour = '"+nomeTour+"'" ;
        Statement stm = c.createStatement();
        stm.executeUpdate(stringa);
        c.close();
    }

    /**
     * Metdoo per cambiare la guida ha una disponibilita
     * @param data
     * @param emailAssociazione
     * @param vecchiaGuida
     * @param nuovaGuida
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void cambiaNomeGuidaAllaDisponibilita(String data, String emailAssociazione, String vecchiaGuida, String nuovaGuida) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "UPDATE disponibilita SET nomeGuida = '"+nuovaGuida+"' where nomeGuida = '"+vecchiaGuida+"' AND emailCicerone = '"+emailAssociazione+"' AND dataTour = '"+data+"'" ;
        Statement stm = c.createStatement();
        stm.executeUpdate(stringa);
        c.close();
    }

    /**
     * Metodo per aggiornare i posti disponibili di una disponibilita avvenuta una prenotazione
     * @param id disponibilita
     * @param postiPrenotati
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void aggiornaPostiDisponibili (int id, int postiPrenotati) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "UPDATE disponibilita SET postiDisponibili = postiDisponibili -"+postiPrenotati+" where id= "+id;
        Statement stm = c.createStatement();
        stm.executeUpdate(stringa);
        c.close();
    }

    /**
     * Caso in cui viene eliminata una prenotazione e devono essere aggiornati i posti disponibili
     * @param id disponibilita
     * @param postiPrenotati
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void aggiungiPostiAPostiDisponibili (int id, int postiPrenotati) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "UPDATE disponibilita SET postiDisponibili = postiDisponibili +"+postiPrenotati+" where id= "+id;
        Statement stm = c.createStatement();
        stm.executeUpdate(stringa);
        c.close();
    }


    /**
     * Metodo per aggiornare il totale dei posti prenotati di una disponibilita avvenuta una prenotazione
     * @param id disponibilita
     * @param postiPrenotati
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void aggiornaTotalePostiPrenotati (int id, int postiPrenotati) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "UPDATE disponibilita SET totalePostiPrenotati = totalePostiPrenotati +"+postiPrenotati+" where id= "+id ;
        Statement stm = c.createStatement();
        stm.executeUpdate(stringa);
        c.close();
    }

    /**
     * Caso in cui viene eliminata una prenotazione e quidni vengono aggiornati i posti tatale prenotati di un tour
     * @param id disponibilita
     * @param postiPrenotati
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void eliminaPostiDaTotalePostiPrenotati (int id, int postiPrenotati) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "UPDATE disponibilita SET totalePostiPrenotati = totalePostiPrenotati -"+postiPrenotati+" where id= "+id ;
        Statement stm = c.createStatement();
        stm.executeUpdate(stringa);
        c.close();
    }
}
