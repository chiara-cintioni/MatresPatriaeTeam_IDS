package DBManagers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBManagerDelete {
    private final static String url = "jdbc:postgresql://localhost:5432/Cicero";
    private final static String user = "postgres";
    private final static String password = "cicero";
    private final static String driver = "org.postgresql.Driver";
    private static DBManagerDelete dbManagerDelete;

    private final static String DELETE_FROM_DISPONIBILITA = "DELETE FROM disponibilita where id = ?";
    private final static String DELETE_FROM_TOUR = "DELETE FROM tour where nome = ? AND emailCicerone = ?";
    private final static String DELETE_FROM_GUIDE = "DELETE FROM guide where id = ?";
    private final static String DELETE_FROM_TAG = "DELETE FROM tag where idTag = ?";
    private final static String DELETE_FROM_TOPONIMI = "DELETE FROM toponimi where idToponimo =  ?";
    private final static String DELETE_FROM_PRENOTAZIONI = "DELETE FROM prenotazioni where nomeTour = ? AND dataTour = ? AND emailTurista = ?";
    private final static String DELETE_FROM_SOSTE = "DELETE FROM soste where idSosta = ? ";
    private final static String DELETE_FROM_INVITI = "DELETE FROM inviti where nomeTour = ? AND emailCicerone = ? AND emailInvitato = ?";
    private final static String DELETE_FROM_TAG_PROPOSTI= "DELETE FROM tagProposti where idTagProposto = ?";

    private DBManagerDelete(){}

    public DBManagerDelete getInstance(){
        if(dbManagerDelete == null){
            dbManagerDelete = new DBManagerDelete();
        }
        return dbManagerDelete;
    }

    /**
     * Metodo per rimuovere una disponibilita dalla tabella delle disponibilita dato l'indice
     * @param index della disponibilita che si vuole rimuovere
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void RimuoviDisponibilitaDallaTabella (int index) throws ClassNotFoundException, SQLException{
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        PreparedStatement pst = c.prepareStatement(DELETE_FROM_DISPONIBILITA);
        pst.setInt(1,index);
        pst.executeUpdate();
        c.close();
    }

    public static void RimuoviPrenotazioneDallaTabella (String nomeTour, String dataTour, String emailTurista) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        PreparedStatement pst = c.prepareStatement(DELETE_FROM_PRENOTAZIONI);
        pst.setString (1, nomeTour);
        pst.setString (2, dataTour);
        pst.setString (3, emailTurista);
        pst.executeUpdate();
        c.close();
    }

    public  static void RimuoviTourDallaTabella (String nomeTour, String emailCicerone) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        PreparedStatement pst = c.prepareStatement(DELETE_FROM_TOUR);
        pst.setString (1, nomeTour);
        pst.setString (2, emailCicerone);
        pst.executeUpdate();
        c.close();
    }

    public static void RimuoviGuidaDallaTabella (int index) throws ClassNotFoundException, SQLException{
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        PreparedStatement pst = c.prepareStatement(DELETE_FROM_GUIDE);
        pst.setInt(1,index);
        pst.executeUpdate();
        c.close();
    }



    public  static void RimuoviTag (int id) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        PreparedStatement pst = c.prepareStatement(DELETE_FROM_TAG);
        pst.setInt (1, id);
        pst.executeUpdate();
        c.close();
    }

    public  static void RimuoviToponimo (int id) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        PreparedStatement pst = c.prepareStatement(DELETE_FROM_TOPONIMI);
        pst.setInt (1,id);
        pst.executeUpdate();
        c.close();
    }

    public static void RimuoviSostaDallaTabella (int id_sosta) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        PreparedStatement pst = c.prepareStatement(DELETE_FROM_SOSTE);
        pst.setInt (1, id_sosta);
        pst.executeUpdate();
        c.close();
    }

    public static void RimuoviInvitoDallaTabella (String nomeTour, String emailCicerone, String emailInvitato) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        PreparedStatement pst = c.prepareStatement(DELETE_FROM_INVITI);
        pst.setString (1, nomeTour);
        pst.setString (2, emailCicerone);
        pst.setString (3, emailInvitato);
        pst.executeUpdate();
        c.close();
    }

    public  static void RimuoviTagProposti (int id) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        PreparedStatement pst = c.prepareStatement(DELETE_FROM_TAG_PROPOSTI);
        pst.setInt (1, id);
        pst.executeUpdate();
        c.close();
    }
}
