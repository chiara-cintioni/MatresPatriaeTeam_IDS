package DBManagers;


import java.sql.*;

public class DBManagerConfronti {
    private static DBManagerConfronti dbManagerConfronti;
    private final static String url = "jdbc:postgresql://localhost:5432/Cicero";
    private final static String user = "postgres";
    private final static String password = "cicero";
    private final static String driver = "org.postgresql.Driver";

    private DBManagerConfronti(){}




    public DBManagerConfronti getInstance(){
        if(dbManagerConfronti == null){
            dbManagerConfronti = new DBManagerConfronti();
        }
        return dbManagerConfronti;
    }

    public static boolean confrontaUsername (String username)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        int valore;
        String stringa = "SELECT COUNT(*) AS valore FROM utenti WHERE username = '"+username+"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        valore = resultSet.getInt("valore");
        c.close();
        if(valore != 0){
            return false;
        }
        return true;
    }

    /**
     * Metodo per vedere se dato un id esiste un tour legato a quell'id
     * @param id_Tour
     * @return true se l'id corrisponde a un tour, false altrimenti
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static boolean confrontaIdTour (int id_Tour)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        int valore;
        String stringa = "SELECT COUNT(*) AS valore FROM tour WHERE  id = "+id_Tour;
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        valore = resultSet.getInt("valore");
        c.close();
        if(valore == 0){
            return false;
        }else {
            return true;
        }
    }

    /**
     * Metodo per sapere se esiste gia' un tag con il nome dato in input
     * @param nomeTag che vogliamo controllare
     * @return true se esiste gia' un tag con il nome dato, false se non esiste tag con il nome dato
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static boolean confrontoEsistenzaTag(String nomeTag)  throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        int valore;
        String stringa = "SELECT COUNT(*) AS valore FROM tag WHERE nome = '"+nomeTag+"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        valore = resultSet.getInt("valore");
        c.close();
        if(valore == 0){
            return false;
        }else {
            return true;
        }
    }

    /**
     * Metodo per sapere se un toponimo con nome e tipo dati esiste gia'
     * @param nomeToponimo
     * @param tipoToponimo
     * @return true se il toponimo di quel tipo esiste gia, false altrimenti
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static boolean confrontoEsistenzaToponimi(String nomeToponimo, String tipoToponimo)  throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        int valore;
        String stringa = "SELECT COUNT(*) AS valore FROM toponimi WHERE nome = ?  AND tipoToponimo = ?";
        PreparedStatement stm = c.prepareStatement(stringa);
        stm.setString(1,nomeToponimo);
        stm.setString (2, tipoToponimo);
        ResultSet res = stm.executeQuery();
        res.next();
        valore = res.getInt("valore");
        c.close();
        if(valore == 0){
            return false;
        }else {
            return true;
        }
    }

    public static boolean confrontoEsistenzaAvviso(String avviso, String email)  throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        int valore;
        String stringa = "SELECT COUNT(*) AS valore FROM avvisi WHERE avviso = '"+avviso+"' AND email ='"+email+"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        valore = resultSet.getInt("valore");
        c.close();
        if(valore == 0){
            return false;
        }else {
            return true;
        }
    }

    /**
     * Metodo per vedere se esiste una disponibilita con l'id passato in input
     * @param id_Disponibilita del tour di cui si vuole sapere il numero di disponibilita
     * @return true esiste almenouna disponibilita, false altrimenti
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static boolean confrontaDisponibilita(int id_Disponibilita)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        int valore;
        String stringa = "SELECT COUNT(*) AS valore FROM disponibilita where id = "+id_Disponibilita;
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        valore = resultSet.getInt("valore");
        c.close();
        if(valore == 0){
            return false;
        }else {
            return true;
        }
    }

    /**
     * Metodo per sapere se ha un indice corrisponde una guida
     * @param index da controllare
     * @return true se all'indice corrisponde una guida, false se l'indice non esiste nell'elenco delle guide
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static boolean confrontaIdGuida(int index) throws  ClassNotFoundException, SQLException{
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        int valore;
        String stringa = "SELECT COUNT(*) AS valore FROM guide where id = "+index;
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        valore = resultSet.getInt("valore");
        c.close();
        if(valore == 0){
            return false;
        }else {
            return true;
        }
    }

    /**
     * Metodo per sapere se una guida ha delle disponibilita oppure no
     * @param nomeGuida della guida di cui si vuole sapere se ha assegnate delle disponibilita
     * @return true se la guida ha almeno una disponibilita , false altrimenti (guida che non fa nessun tour )
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static boolean confrontaDisponibilitaGuida(String nomeGuida) throws  ClassNotFoundException, SQLException{
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        int valore;
        String stringa = "SELECT COUNT(*) AS valore FROM disponibilita where nomeGuida = '"+nomeGuida+"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        valore = resultSet.getInt("valore");
        c.close();
        if(valore == 0){
            return false;
        }else {
            return true;
        }
    }



    /**
     * Metodo per sapere se un associazione ha delle guide nella sua lista delle guide
     * @param emailAssociazione dell'Associazione
     * @param nomeGuida della guida che non vogliamo considerare
     * @return true se l'Associazione ha altre guide oltre a quella che ha passato, false se l'Associazione ha solo una guida (che è quella passata)
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static boolean esistenzaDelleGuide ( String emailAssociazione, String nomeGuida) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        int valore;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT COUNT(*) as valore FROM guide where emailAssociazione = '"+emailAssociazione+"' AND username <> '"+nomeGuida+"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        valore = resultSet.getInt("valore");
        c.close();
        if(valore != 0){
            return true;
        }
        return false;
    }

    /**
     * Metodo per sapere se un cicerone ha una data gia' occupata da un altro tour
     * @param data che si vuole controllare se e' libera oppure no
     * @param emailCicerone del cicerone di cui si vuole sapere se in quella data ha gia' un tour
     * @return true se la data e' gia' occupata da un altro tour, false se la data e' libera e il cicerone può inserirla come nuova disponibilita'
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static boolean esistenzaDiUnTourInQuellaData (String data, String emailCicerone) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        int valore;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT COUNT(*) as valore FROM disponibilita where emailCicerone = '"+emailCicerone+"' AND dataTour = '"+data+"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        valore = resultSet.getInt("valore");
        c.close();
        if(valore != 0){
            return true;
        }
        return false;
    }

    /**
     * Metodo per sapere se un tour ha già una disponibilita nella data passata in input
     * @param data della disponibilita da controllare
     * @param emailCicerone del cicerone
     * @param nomeTour del tour di cui vogliamo sapere l'esistenza della disponibilita
     * @return true se il tour presenta la disponibilita in quella data, false altrimenti
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static boolean esistenzaDelTourNellaDataSpecificata (String data, String emailCicerone, String nomeTour )throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        int valore;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT COUNT(*) as valore FROM disponibilita where emailCicerone = '"+emailCicerone+"' AND dataTour = '"+data+"' AND nomeTour ='"+nomeTour+"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        valore = resultSet.getInt("valore");
        c.close();
        if(valore != 0){
            return true;
        }
        return false;
    }

    /**
     * Metodo per sapere se la guida del'associazione ha gia un altro tour nella data specifica
     * @param username della guida
     * @param data del tour
     * @param emailAssociazione dell'associaizone a cui appartiene la guida
     * @return true se la guida ha un tour in quella data, false altrimenti
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static boolean esistenzaDiUnTourInQuellaDataPerGuidaAssociazione (String username, String data,  String emailAssociazione) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        int valore;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT COUNT(*) as valore FROM disponibilita where emailCicerone = '"+emailAssociazione+"' AND nomeGuida = '"+username+"' AND dataTour = '"+data+"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        valore = resultSet.getInt("valore");
        c.close();
        if(valore != 0){
            return true;
        }
        return false;
    }

    /*Metodo per vedere se esistono tag
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static boolean esistenzaTagNellaTabella () throws ClassNotFoundException, SQLException {
        int valore;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT COUNT(*) as valore FROM tag";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        valore = resultSet.getInt("valore");
        c.close();
        if(valore != 0){
            return true;
        }
        return false;
    }

    /**
     * Metodo per sapere se esistono dei toponimi
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static boolean esistenzaToponimiNellaTabella (String tipo) throws ClassNotFoundException, SQLException {
        int valore;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT COUNT(*) as valore FROM toponimi where tipoToponimo = '"+tipo+"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        valore = resultSet.getInt("valore");
        c.close();
        if(valore != 0){
            return true;
        }
        return false;
    }

    /**
     * Metodo per sapere se ha un id corrisponde una prenotazione
     * @param index della prenotazione
     * @return true se la prenotazione con quell'id esiste, false altrimenti
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static boolean esistenzaPrenotazioneDatoId (int index) throws ClassNotFoundException, SQLException {
        int valore;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT COUNT(*) as valore FROM prenotazioni where id ="+index;
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        valore = resultSet.getInt("valore");
        c.close();
        if(valore != 0){
            return true;
        }
        return false;
    }

    public static boolean confrontaEmail (String email)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        int valore;
        String stringa = "SELECT COUNT(*) AS valore FROM utenti WHERE email = '"+email+"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        valore = resultSet.getInt("valore");
        c.close();
        if(valore != 0){
            return false;
        }
        return true;
    }

    /**
     * Metodo per sapere se esiste un turista registrato con email datat in input
     * @param email
     * @return true se esiste un turista con email data in input, false altrimenti
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static boolean confrontaEmailDaTabellaTuristi (String email)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        int valore;
        String stringa = "SELECT COUNT(*) AS valore FROM turisti WHERE emailTurista = '"+email+"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        valore = resultSet.getInt("valore");
        c.close();
        if(valore != 0) {
            return true;
        }
        return false;
    }

    /**
     * Metodo per sapere se i posti da prenotare sono disponibili in una data disponibilita
     * @param id
     * @param postiDaPrenotare
     * @return true se i posti si possono prenotare, false altrimenti
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static boolean verificaPerIPostiDaPrenotare ( int id, int postiDaPrenotare) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        int postiDisp;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT postiDisponibili FROM disponibilita WHERE id = "+id;
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        postiDisp = resultSet.getInt("postiDisponibili");
        if (postiDisp > postiDaPrenotare) {
            return true;
        }
        c.close();
        return false;
    }

    /**
     * Metodo per sapere se un tour presenta gia' una sosta con il nome dato, nel tour dato
     * @param nomeSosta
     * @param emailCicerone
     * @param nomeTour
     * @return true se la sosta con quel nome esiste gia' nel tour, false altrimenti
     */
    public static boolean verificaEsistenzaSosta(String nomeSosta, String emailCicerone, String nomeTour) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        int valore;
        String stringa = "SELECT COUNT(*) AS valore FROM soste WHERE nomeSosta = '"+nomeSosta+"' AND emailCicerone = '"+emailCicerone+"' AND nomeTour ='"+nomeTour+"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        valore = resultSet.getInt("valore");
        c.close();
        if(valore != 0) {
            return true;
        }
        return false;
    }

    /**
     * Metodo per sapere se dato un id, quell'id corrisponde a una sosta
     * @param id_sosta
     * @return true se la sosta con quell'id esiste, false altrimenti
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static boolean verificaEsistenzaSostaDatoIdSosta(int id_sosta) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        int valore;
        String stringa = "SELECT COUNT(*) AS valore FROM soste WHERE idSosta ="+id_sosta;
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        valore = resultSet.getInt("valore");
        c.close();
        if(valore != 0) {
            return true;
        }
        return false;
    }

    /**
     * Metodo per sapere se un cicerone ha almeno un tour a suo nome
     * @param emailCicerone
     * @return true se esiste almeno un tour a nome di quel cicerone, false latrimenti
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static boolean verificaEsistenzaTourPerCicerone( String emailCicerone) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        int valore;
        String stringa = "SELECT COUNT(*) AS valore FROM tour WHERE emailCicerone = '"+emailCicerone+"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        valore = resultSet.getInt("valore");
        c.close();
        if(valore != 0) {
            return true;
        }
        return false;
    }

    /**
     * Metodo per sapere se il tour ha almeno una sosta oltre quella che diamo in input
     * @param emailCicerone
     * @param nomeTour
     * @return true se il tour ha almeno due soste nel tour, false altrimenti
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static boolean esistenzaSosteNelTour ( String nomeTour, String emailCicerone) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        int valore;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT COUNT(*) as valore FROM soste where nomeTour = '"+nomeTour+"' AND emailCicerone = '"+emailCicerone+"' ";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        valore = resultSet.getInt("valore");
        c.close();
        if(valore > 1){
            return true;
        }
        return false;
    }

    public static boolean esistenzaTagProposti() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        int valore;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT COUNT(*) as valore FROM tagProposti ";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        valore = resultSet.getInt("valore");
        c.close();
        if(valore > 0){
            return true;
        }
        return false;
    }
}
