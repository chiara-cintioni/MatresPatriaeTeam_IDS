package DBManagers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBManagerInsert {
    private static DBManagerInsert dbManagerInsert;
    private final static String url = "jdbc:postgresql://localhost:5432/Cicero";
    private final static String user = "postgres";
    private final static String password = "cicero";
    private final static String driver = "org.postgresql.Driver";

    private final static String INSERT_INTO_UTENTI = "INSERT INTO utenti"+"(email,username,password,ruolo) values (?,?,?,?)";
    private final static String INSERT_INTO_CICERONI = "INSERT INTO ciceroni"+"(nome,cognome,emailCicerone,ruoloCicerone) values (?,?,?,?)";
    private final static String INSERT_INTO_ASSOCIAZIONI = "INSERT INTO associazioni"+"(nomeAssociazione, partitaIva, emailAssociazione, ruoloAssociazione) values (?,?,?,?)";
    private final static String INSERT_INTO_TURISTI = "INSERT INTO turisti"+"(nome, cognome, emailTurista, ruoloTurista) values (?,?,?,?)";
    private final static String INSERT_INTO_TOUR = "INSERT INTO tour"+"(nome, numeroMinimo, numeroMassimo, prezzo, descrizione, giorniScadenzaInvito, emailCicerone) values (?,?,?,?,?,?,?)";
    private final static String INSERT_INTO_DISPONIBILITA ="INSERT INTO disponibilita"+"(nomeTour, nomeGuida, nomeCicerone, emailCicerone, dataTour, postiDisponibili) values (?,?,?,?,?,?)";
    private final static String INSERT_INTO_AVVISI = "INSERT INTO avvisi"+" (avviso, email) values (?,?)";
    private final static String INSERT_INTO_PRENOTAZIONI = "INSERT INTO prenotazioni"+" (nomeTour, dataTour, emailTurista, emailCicerone, numeroPostiPrenotati) values(?,?,?,?,?)";
    private final static String INSERT_INTO_GUIDE ="INSERT INTO guide"+"(nomeGuida, cognomeGuida,username, informazioni, emailAssociazione) values (?,?,?,?,?)";
    private final static String INSERT_INTO_TAG = "INSERT INTO tag (nome) values (?)";
    private final static String INSERT_INTO_TOPONIMI = "INSERT INTO toponimi (nome, tipoToponimo) values (?,?)";
    private final static String INSERT_INTO_TAGPROPOSTI = "INSERT INTO tagProposti (nome) values (?)";
    private final static String INSERT_INTO_INVITI = "INSERT INTO inviti (nomeInvitato, cognomeInvitato, emailInvitato, emailCicerone, dataTour, nomeTour, postiRiservati) values (?,?,?,?,?,?,?)";
    private final static String INSERT_INTO_SOSTE = "INSERT INTO soste (nomeSosta, descrizioneSosta, emailCicerone, nomeTour) values (?,?,?,?)";

    private DBManagerInsert() {}

    public DBManagerInsert getIstance () {
        if (dbManagerInsert == null) {
            dbManagerInsert = new DBManagerInsert();
        }
        return dbManagerInsert;
    }
    public static void InserisciDatiRegistrazione (String email, String username, String password_utente, String ruolo)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        PreparedStatement pst = c.prepareStatement(INSERT_INTO_UTENTI);
        pst.setString(1, email);
        pst.setString(2, username);
        pst.setString(3, password_utente);
        pst.setString(4, ruolo);
        pst.executeUpdate();
        c.close();
    }

    public static void InserisciNellaTabellaCicerone (String email, String nome, String cognome, String ruolo)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        PreparedStatement pst = c.prepareStatement(INSERT_INTO_CICERONI);
        pst.setString(1, nome);
        pst.setString(2, cognome);
        pst.setString(3, email);
        pst.setString(4, ruolo);
        pst.executeUpdate();
        c.close();
    }

    public static void InserisciNellaTabellaAssociazioni (String email, String nome, String partitaIVA, String ruolo)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        PreparedStatement pst = c.prepareStatement(INSERT_INTO_ASSOCIAZIONI);
        pst.setString(1, nome);
        pst.setString(2, partitaIVA);
        pst.setString(3, email);
        pst.setString(4, ruolo);
        pst.executeUpdate();
        c.close();
    }

    public static void InserisciNellaTabellaTuristi (String nome, String cognome, String email, String ruolo)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        PreparedStatement pst = c.prepareStatement(INSERT_INTO_TURISTI);
        pst.setString(1, nome);
        pst.setString(2, cognome);
        pst.setString(3, email);
        pst.setString(4, ruolo);
        pst.executeUpdate();
        c.close();}

    public static void InserisciNellaTabellaTour (String nome, int numeroMinimo, int numeroMassimo, double prezzo, String descrizione, int giorniScadenzaInvito, String email)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        PreparedStatement pst = c.prepareStatement(INSERT_INTO_TOUR);
        pst.setString(1, nome);
        pst.setInt(2, numeroMinimo);
        pst.setInt(3, numeroMassimo);
        pst.setDouble(4, prezzo);
        pst.setString(5, descrizione);
        pst.setInt(6, giorniScadenzaInvito);
        pst.setString(7, email);
        pst.executeUpdate();
        c.close();
    }

    public static void InserisciNellaTabellaDisponibilita(String nomeTour,String nomeGuida, String nomeCicerone, String email,  String dataTour, int postiDisponibili )
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        PreparedStatement pst = c.prepareStatement(INSERT_INTO_DISPONIBILITA);
        pst.setString(1, nomeTour);
        pst.setString(2,nomeGuida);
        pst.setString(3, nomeCicerone);
        pst.setString(4, email);
        pst.setString(5, dataTour);
        pst.setInt(6, postiDisponibili);
        pst.executeUpdate();
        c.close();
    }
    public static void InserisciNellaTabellaPrenotazioni(String nomeTour,String dataTour, String emailTurista,  String emailCicerone, int postiPrenotati )
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        PreparedStatement pst = c.prepareStatement(INSERT_INTO_PRENOTAZIONI);
        pst.setString(1, nomeTour);
        pst.setString(2, dataTour);
        pst.setString(3, emailTurista);
        pst.setString(4,emailCicerone);
        pst.setInt(5, postiPrenotati);
        pst.executeUpdate();
        c.close();
    }


    public static void InserisciNellaTabellaAvvisi (String avviso, String email)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        PreparedStatement pst = c.prepareStatement(INSERT_INTO_AVVISI);
        pst.setString(1, avviso);
        pst.setString(2, email);
        pst.executeUpdate();
        c.close();
    }


    public static void InserisciNellaTabellaGuide (String nomeGuida, String cognomeGuida, String userName ,String informazioni, String emailAssociazione) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection (url, user, password);
        PreparedStatement pst = c.prepareStatement(INSERT_INTO_GUIDE);
        pst.setString(1,nomeGuida);
        pst.setString(2,cognomeGuida);
        pst.setString(3,userName);
        pst.setString(4,informazioni);
        pst.setString(5,emailAssociazione);
        pst.executeUpdate();
        c.close();
    }

    public static void InserisciNellaTabellaTag (String nomeTag)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        PreparedStatement pst = c.prepareStatement(INSERT_INTO_TAG);
        pst.setString(1, nomeTag);
        pst.executeUpdate();
        c.close();
    }

    public static void InserisciNellaTabellaToponimi (String nomeToponimo, String tipoToponimo)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        PreparedStatement pst = c.prepareStatement(INSERT_INTO_TOPONIMI);
        pst.setString(1, nomeToponimo);
        pst.setString(2, tipoToponimo);
        pst.executeUpdate();
        c.close();
    }

    public static void InserisciNellaTabellaTagProposti (String nomeTag)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        PreparedStatement pst = c.prepareStatement(INSERT_INTO_TAGPROPOSTI);
        pst.setString(1, nomeTag);
        pst.executeUpdate();
        c.close();
    }

    public static void InserisciNellaTabellaInviti (String nomeInvitato, String cognomeInvitato, String emailInvitato ,String emailCicerone, String dataTour, String nomeTour, int postiRiservati) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection (url, user, password);
        PreparedStatement pst = c.prepareStatement(INSERT_INTO_INVITI);
        pst.setString(1,nomeInvitato);
        pst.setString(2,cognomeInvitato);
        pst.setString(3,emailInvitato);
        pst.setString(4,emailCicerone);
        pst.setString(5,dataTour);
        pst.setString(6,nomeTour);
        pst.setInt(7,postiRiservati);
        pst.executeUpdate();
        c.close();
    }

    public static void InserisciNellaTabellaSoste (String nomeSosta, String descrizioneSosta, String emailCicerone ,String nomeTour) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection (url, user, password);
        PreparedStatement pst = c.prepareStatement(INSERT_INTO_SOSTE);
        pst.setString(1,nomeSosta);
        pst.setString(2,descrizioneSosta);
        pst.setString(3,emailCicerone);
        pst.setString(4,nomeTour);
        pst.executeUpdate();
        c.close();
    }



}
