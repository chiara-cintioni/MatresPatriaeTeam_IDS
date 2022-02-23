package DBManagers;

import java.sql.*;

public class DBManagerStampa {

    private final static String url = "jdbc:postgresql://localhost:5432/Cicero";
    private final static String user = "postgres";
    private final static String password = "cicero";
    private final static String driver = "org.postgresql.Driver";
    private static DBManagerStampa dbManagerStampa;

    private DBManagerStampa(){}



    public DBManagerStampa getInstance(){
        if(dbManagerStampa == null){
            dbManagerStampa = new DBManagerStampa();
        }
        return dbManagerStampa;
    }

    /**
     * Metodo per visualizzare le guide (nome, cognome, id) di una associazione
     * @param emailAssociazione dell'associazione di cui si vuole vedere l'elenco delle guide
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void visualizzaIdGuidaDaElenco (String emailAssociazione) throws ClassNotFoundException, SQLException{
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        int index;
        String nome;
        String cognome;
        String stringa = "SELECT nomeGuida, cognomeGuida, id from guide where emailAssociazione ='"+emailAssociazione+"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        while(resultSet.next()){
            nome = resultSet.getString("nomeGuida");
            cognome = resultSet.getString("cognomeGuida");
            index = resultSet.getInt("id");
            System.out.println("Guida: "+nome+" "+cognome+ ", con indice: ["+index+"]");
        }
        c.close();
    }

    /**
     * Metodo per visualizzare i tag dell'Amministrazione
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void visualizzaTag () throws ClassNotFoundException, SQLException{
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String nomeTag;
        String stringa = "SELECT nome FROM tag";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        while(resultSet.next()){
            nomeTag = resultSet.getString("nome");
            System.out.println("Tag: "+nomeTag);
        }
        c.close();
    }

    public static void visualizzaTagConId () throws ClassNotFoundException, SQLException{
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String nomeTag;
        int id;
        String stringa = "SELECT nome, idTag FROM tag";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        while(resultSet.next()){
            nomeTag = resultSet.getString("nome");
            id = resultSet.getInt("idTag");
            System.out.println("Tag: "+nomeTag+", id: "+id);
        }
        c.close();
    }

    /**
     * Metodo per visualizzare tutti i toponimi
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void visualizzaTuttiIToponimi () throws ClassNotFoundException, SQLException{
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String nomeToponimo;
        String tipo;
        String stringa = "SELECT nome, tipoToponimo FROM toponimi ORDER BY tipoToponimo";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        while(resultSet.next()){
            nomeToponimo = resultSet.getString("nome");
            tipo = resultSet.getString("tipoToponimo");
            System.out.println("Toponimo: "+nomeToponimo+", tipo: "+tipo);
        }
        c.close();
    }

    /**
     * Metodo per visualizzare i toponimi di un solo tipo
     * @param tipo
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void visualizzaToponimiPerTipo (String tipo) throws ClassNotFoundException, SQLException{
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String nomeToponimo;
        String stringa = "SELECT nome FROM toponimi where tipoToponimo ='"+tipo+"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        while(resultSet.next()){
            nomeToponimo = resultSet.getString("nome");
            System.out.println("Toponimo: "+nomeToponimo+", tipo: "+tipo);
        }
        c.close();
    }
    public static void visualizzaIdToponimoPerTipo (String tipo) throws ClassNotFoundException, SQLException{
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String nomeToponimo;
        int id;
        String stringa = "SELECT nome, idToponimo FROM toponimi where tipoToponimo ='"+tipo+"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        while(resultSet.next()){
            nomeToponimo = resultSet.getString("nome");
            id = resultSet.getInt("idToponimo");
            System.out.println("Toponimo: "+nomeToponimo+", id: "+id);
        }
        c.close();
    }

    public static void visualizzaToponimoConId () throws ClassNotFoundException, SQLException{
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String nomeToponimo;
        String tipo;
        int id;
        String stringa = "SELECT nome, tipoToponimo, idToponimo FROM toponimi ORDER BY tipoToponimo";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        while(resultSet.next()){
            nomeToponimo = resultSet.getString("nome");
            tipo = resultSet.getString("tipoToponimo");
            id = resultSet.getInt("idToponimo");
            System.out.println("Toponimo: "+nomeToponimo+", tipo: "+tipo+", id: "+id);
        }
        c.close();
    }
    /**
     *  * Metodo per visualizzare le guide senza però la guida che non vogliamo considerare,
     * @param emailAssociazione
     * @param data
     * @return stampa = false se non ci sono guide stampate, true se c'e' almeno una guida stampata
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static boolean visualizzaGuidaDaElencoSenzaVecchiaGuida (String emailAssociazione,  String data) throws ClassNotFoundException, SQLException{
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        boolean stampa = false;
        int index;
        String nome;
        String stringa = "select username, id\n" +
                "from guide\n" +
                "where emailAssociazione = '"+emailAssociazione+"' AND username <> ALL ((select nomeGuida from disponibilita where dataTour = '"+data+"'))";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        while(resultSet.next()){
            stampa = true;
            nome = resultSet.getString("username");
            index = resultSet.getInt("id");
            System.out.println("Guida: "+nome+", con indice: ["+index+"]");

        }
        c.close();
        return stampa;
    }

    public static void visualizzaITuoiAvvisi (String email) throws ClassNotFoundException, SQLException{
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String avviso;
        String stringa = "SELECT avviso FROM avvisi where email ='"+email+"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        while(resultSet.next()){
            avviso = resultSet.getString("avviso");
            System.out.println("AVVISO: "+avviso);
        }
        c.close();
    }

    /**
     * Metodo per visualizzare l'id di una guida dato il nome della guida
     * @param emailAssociazione
     * @param nomeGuida
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static int prendiIdGuidaDatoIlNomeDellaGuida(String emailAssociazione, String nomeGuida) throws ClassNotFoundException, SQLException{
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        int index;

        String stringa = "SELECT id from guide where emailAssociazione ='"+emailAssociazione+"' AND username = '"+nomeGuida+"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        index = resultSet.getInt("id");
        c.close();
        return index;
    }

    /**
     * Metodo che ritorna id di una prenotazione dati il nome del tour prenotato, la data e l'email del turista che ha prenotato
     * @param dataTour
     * @param nomeTour
     * @param emailTurista
     * @return indice della prenotazione
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static int prendiIdDellaPrenotazione(String dataTour, String nomeTour, String emailTurista) throws ClassNotFoundException, SQLException{
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        int index;

        String stringa = "SELECT id from prenotazioni where emailTurista ='"+emailTurista+"' AND nomeTour = '"+nomeTour+"' AND dataTour = '"+dataTour+"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        index = resultSet.getInt("id");
        c.close();
        return index;
    }

    /**
     * Metodo per visualizzare le informazioni delle guide di un associaizone
     * @param emailAssociazione dell'associazione
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void visualizzaInformazioniGuidaAssociazione (String emailAssociazione) throws ClassNotFoundException, SQLException{
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String informazioni;
        String nome;
        String cognome;
        String stringa = "SELECT nomeGuida, cognomeGuida, informazioni from guide where emailAssociazione ='"+emailAssociazione+"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        while(resultSet.next()){
            nome = resultSet.getString("nomeGuida");
            cognome = resultSet.getString("cognomeGuida");
            informazioni = resultSet.getString("informazioni");
            System.out.println("Guida: "+nome+", "+cognome+ ", informazioni: "+informazioni);
        }
        c.close();
    }



    /**
     * Metodo che ritorna l'username di una guida dato l'indice della guida
     * @param index della guida
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static String selezionaGuidaDaIndice (int index) throws ClassNotFoundException, SQLException{
        Class.forName(driver);
        String username;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT username FROM guide where id = "+index;
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        username = resultSet.getString("username");
        c.close();
        return username;
    }

    /**
     * visualizza tutte le disponibilita di un tour dato il nome del tour
     * @param nomeTour del tour di cui si vogliono visualizzare le disponibilita
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void visualizzaDisponibilitaDiUnTour (String nomeTour) throws ClassNotFoundException, SQLException{
        Class.forName(driver);
        String nome;
        String data;
        String guida;
        int posti;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT nomeTour, dataTour, nomeGuida, postiDisponibili FROM disponibilita where nomeTour = '"+nomeTour+"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        while(resultSet.next()){
            nome = resultSet.getString("nomeTour");
            data = resultSet.getString("dataTour");
            guida = resultSet.getString("nomeGuida");
            posti = resultSet.getInt ("postiDisponibili");
            System.out.println ("Tour: "+nome+", in data ["+data+"], con la guida: "+guida+", posti ancora disponibili: "+posti);
        }
        c.close();
    }

    /**
     * Metodo per selezionare emial di un cicerone dato l'indice della disponibilita di un tour
     * @param index del tour
     * @return email del cicerone che posssiede il tour con indice specificato
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static String selezionaEmailCiceroneDaDisponibilita (int index) throws ClassNotFoundException, SQLException{
        Class.forName(driver);
        String emailCicerone;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT emailCicerone FROM disponibilita where id = "+index;
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        emailCicerone = resultSet.getString("emailCicerone");
        c.close();
        return emailCicerone;
    }




    /**
     * Metodo per visualizzare tutti i tour di un cicerone
     * @param email del cicerone corrispondente
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void visualizzaITuoiTour(String email)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String nomeTour;
        String stringa = "SELECT nome, id FROM tour WHERE emailCicerone = '"+email+"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        int indice;
        while(resultSet.next()){
            nomeTour = resultSet.getString("nome");
            indice = resultSet.getInt("id");
            System.out.println("Tour: "+nomeTour+ ", con indice: "+indice);
        }
        c.close();
    }

    /**
     * Metodo che permette al turista di visualizzare solo quei tour che hanno anche delle disponibilita
     * @return true se stampa i tour che hanno disponibilita, false non ci sono tour da stampare perchè non esistono tour con disponibilita'
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static boolean visualizzaSoloTourDisponibili()
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String nomeTour;
        boolean var = false;
        String stringa = "SELECT DISTINCT disponibilita.nomeTour, tour.id FROM disponibilita INNER JOIN tour on nomeTour = nome AND disponibilita.emailCicerone = tour.emailCicerone ";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        int indice;
        while(resultSet.next()){
            var = true;
            nomeTour = resultSet.getString("nomeTour");
            indice = resultSet.getInt("id");
            System.out.println("Tour: "+nomeTour+ ", con indice: "+indice);

        }
        c.close();
        return var;
    }

    /**
     * Metodo che permette di visualizzare le disponibilita del cicerone (tutti i tour)
     * @param email del cicerone/associazione
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void visualizzaLeTueDisponibilita (String email)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String nomeTour;
        String stringa = "SELECT nomeTour, dataTour, id FROM disponibilita WHERE emailCicerone = '"+email+"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        String data;
        int indice;
        while(resultSet.next()){
            nomeTour = resultSet.getString("nomeTour");
            data = resultSet.getString("dataTour");
            indice = resultSet.getInt("id");
            System.out.println("Tour: "+nomeTour+ ", data: "+data+",  con indice: "+indice);
        }
        c.close();
    }

    public static void visualizzaLeDisponibilitaDiUnTourSpecifico (String email, String nomeTour)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT nomeTour, dataTour, id FROM disponibilita WHERE emailCicerone = '"+email+"' AND nomeTour = '"+nomeTour+"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        String data;
        int indice;
        String nome;
        while(resultSet.next()){
            nome = resultSet.getString("nomeTour");
            data = resultSet.getString("dataTour");
            indice = resultSet.getInt("id");
            System.out.println("Tour: "+nome+", data: "+data+", indice: "+indice);
        }
        c.close();
    }

    /**
     * Metodo per visualizzare tutte le disponibilita di un cicerone in ordine
     * @param email del cicerone di cui si vuole sapere l'elenco delle disponibilita
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void visualizzaLeTueDisponibilitaOrdinate (String email)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String nomeTour;
        String nomeGuida;
        String stringa = "SELECT nomeTour, dataTour, nomeGuida FROM disponibilita WHERE emailCicerone = '"+email+"' ORDER BY dataTour ASC";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        String data;

        while(resultSet.next()){
            nomeTour = resultSet.getString("nomeTour");
            data = resultSet.getString("dataTour");
            nomeGuida = resultSet.getString("nomeGuida");
            System.out.println("Tour: "+nomeTour+ ", in data: "+data+", con guida: "+nomeGuida);
        }
        c.close();
    }

    /**
     * Metodo per visualizzare le date di un tour dove sono ancora disponibili dei posti da prenotare
     * @param nome
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void visualizzaDisponibilitaDelTour (String nome)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String nomeTour;
        String nomeGuida;
        String stringa = "SELECT nomeTour, dataTour, nomeGuida FROM disponibilita WHERE nomeTour = '"+nome+"'  AND postiDisponibili > 0 ORDER BY dataTour ASC";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        String data;

        while(resultSet.next()){
            nomeTour = resultSet.getString("nomeTour");
            data = resultSet.getString("dataTour");
            nomeGuida = resultSet.getString("nomeGuida");
            System.out.println("Tour: "+nomeTour+ ", in data: "+data+", con guida: "+nomeGuida);
        }
        c.close();
    }

    public static void visualizzaLeTueDisponibilitaOrdinateSenzaGuida (String email)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String nomeTour;
        String nomeGuida;
        String stringa = "SELECT nomeTour, dataTour FROM disponibilita WHERE emailCicerone = '"+email+"' ORDER BY dataTour ASC";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        String data;

        while(resultSet.next()){
            nomeTour = resultSet.getString("nomeTour");
            data = resultSet.getString("dataTour");
            System.out.println("Tour: "+nomeTour+ ", in data: "+data+".");
        }
        c.close();
    }

    /**
     * Metodo per visulizzare un tour in una specifica data inserendo l'id del tour
     * @param index del tour che si vuole selezionare
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void visualizzaTourDaIndice(int index) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String nomeTour;
        String data;
        String stringa = "SELECT nomeTour, dataTour FROM disponibilita WHERE id = " +index;
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        while(resultSet.next()){
            nomeTour = resultSet.getString("nomeTour");
            data = resultSet.getString("dataTour");
            System.out.println("Tour: "+nomeTour+ ", in data [ "+data+" ]");
        }
        c.close();
    }



    /**
     * Metodo che preso il nome del tour e email del cicerone, ritorna il numero più alto prenotati al tour
     * @param nomeTour del tour di cui si vuole sapere il numero massimo di prenotati
     * @param emailCicerone del cicerone a cui appartiene il tour
     * @return il valore di prenotati più alto
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static int prendiValoreMassimoPostiPrenotati (String nomeTour, String emailCicerone)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        int postiPrenotati;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT MAX (totalePostiPrenotati) as totalePostiPrenotati FROM disponibilita WHERE nomeTour = '"+nomeTour+"'  AND emailCicerone = '" + emailCicerone + "'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        postiPrenotati = resultSet.getInt("totalePostiPrenotati");
        c.close();
        return postiPrenotati;
    }

    /**
     * Metodo per stampare tutti i nomi dei tour del sistema con rispettivo indice
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void visualizzaTourEIndice()
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String nomeTour;
        String stringa = "SELECT nome, id FROM tour";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        int id_tour;
        while(resultSet.next()){
            nomeTour = resultSet.getString("nome");
            id_tour = resultSet.getInt("id");
            System.out.println("Tour: "+nomeTour+ ", con indice: "+id_tour);
        }
        c.close();
    }

    /**
     * Metodo per visualizzare tutti i tour del sistema con tutte le informazioni
     * @return true se stampa i tour con dettagli, false se non stampa nulla perche' non ci sono tour disponibili da stampare
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static boolean visualizzaTourConDettagli()
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String nomeTour;
        int indice;
        boolean var = false;
        String stringa = "select nome, descrizione, prezzo, id, username as guida FROM tour INNER JOIN utenti ON emailCicerone = email";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        String descrizione;
        double prezzo;
        String username;
        while(resultSet.next()){
            var = true;
            nomeTour = resultSet.getString("nome");
            descrizione = resultSet.getString("descrizione");
            prezzo = resultSet.getDouble("prezzo");
            username = resultSet.getString("guida");
            indice = resultSet.getInt("id");
            System.out.println("Tour: "+nomeTour+ ", id: "+indice+", prezzo: "+prezzo+ ", guida: "+username+", descrizione: ");
            System.out.println(descrizione);
        }
        c.close();
        return var;
    }

    /**
     * Metodo per visualizzare tutti i tour di un cicerone con le rispettive informazioni
     * @param email del cicerone di cui si vuole sapere i tour
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void visualizzaITuoiTourConDettagli(String email)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String nomeTour;
        String stringa = "SELECT nome, descrizione, prezzo, numeroMinimo, numeroMassimo, giorniScadenzaInvito, id FROM tour WHERE emailCicerone = '"+email+"' ORDER BY id";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        String descrizione;
        double prezzo;
        int id_tour;
        int nMin, nMax, gSI;
        while(resultSet.next()){
            nomeTour = resultSet.getString("nome");
            descrizione = resultSet.getString("descrizione");
            prezzo = resultSet.getDouble("prezzo");
            nMin = resultSet.getInt("numeroMinimo");
            nMax = resultSet.getInt("numeroMassimo");
            gSI = resultSet.getInt("giorniScadenzaInvito");
            id_tour = resultSet.getInt("id");
            System.out.println("Tour: "+nomeTour+ ", prezzo: "+prezzo+ ", numero minimo: "+nMin+", numero massimo: "+nMax+", giorni scadenza invito: "+gSI+", id = "+id_tour);
            System.out.println("Descrizione: "+descrizione);
        }
        c.close();
    }

    /**Metodo per visualizzare tutte le prenotazioni di un turista
     * @param emailTurista di cui si vuole vedere le prenotazioni
     * @return true se stampa le prenotazioni (esistono prenotazioni), false altrimenti
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static boolean visualizzaPrenotazioniDelTurista(String emailTurista)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String nomeTour;
        String data;
        int  id_tour;
        boolean var = false;
        String stringa = "SELECT nomeTour, dataTour, id FROM prenotazioni where emailTurista ='"+emailTurista+"' ORDER BY dataTour";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);

        while(resultSet.next()){
            var = true;
            data = resultSet.getString("dataTour");
            nomeTour = resultSet.getString("nomeTour");
            id_tour = resultSet.getInt("id");
            System.out.println("Tour: "+nomeTour+ ", data: [ "+data+" ] con indice: "+id_tour);
        }
        c.close();
        return var;
    }
    public static String prendiEmailTuristaDaPrenotazione(int id) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String emailTurista;

        String stringa = "SELECT emailTurista FROM prenotazioni where id =" +id;
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        emailTurista = resultSet.getString ("emailTurista");
        c.close();
        return emailTurista;
    }

    public static String prendiEmailCiceroneDaPrenotazione(int id) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String email;

        String stringa = "SELECT emailCicerone FROM prenotazioni where id =" +id;
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        email = resultSet.getString ("emailCicerone");
        c.close();
        return email;
    }

    public static String prendiNomeTourDaPrenotazione(int id) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String nome;

        String stringa = "SELECT nomeTour FROM prenotazioni where id =" +id;
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        nome = resultSet.getString ("nomeTour");
        c.close();
        return nome;
    }

    public static String prendiDataTourDaPrenotazione(int id) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String data;

        String stringa = "SELECT dataTour FROM prenotazioni where id =" +id;
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        data = resultSet.getString ("dataTour");
        c.close();
        return data;
    }


    /**
     * Metodo che ritorna la password di uno specifico utente
     * @param email dell'utente di cui si vuole sapere la password
     * @return la password dell'utente
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static String prendiPassword (String email)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String password;
        String stringa = "SELECT password FROM utenti WHERE email = '"+email+"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        password = resultSet.getString("password");
        c.close();
        return password;
    }

    /**
     * Metodo per visualizzare il ruolo di un utente
     * @param email dell'utente di cui si vuole sapere il ruolo
     * @return il ruolo dell'utente
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static String prendiRuolo (String email)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String ruolo;
        String stringa = "SELECT ruolo FROM utenti WHERE email = '"+email+"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        ruolo = resultSet.getString("ruolo");
        c.close();
        return ruolo;
    }

    /**
     * Metodo per ritornare l'username di un utente data l'email
     * @param username dell'utente di cui si vuole sapere email
     * @return email dell'utente
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static String prendiEmail (String username)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String email_utente;
        String stringa = "SELECT email FROM utenti WHERE username = '"+username+"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        email_utente = resultSet.getString("email");
        c.close();
        return email_utente;
    }

    /**
     * Metodo che ritorna email di un cicerone dato l'indice del suo tour
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static String prendiEmailCiceroneDaTour (int id)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String email_cicerone;
        String stringa = "SELECT emailCicerone FROM tour WHERE id = " +id;
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        email_cicerone = resultSet.getString("emailCicerone");
        c.close();
        return email_cicerone;
    }

    /**
     * Metodo per ritornare l'username di un utente data l'email
     * @param email dell'utente di cui si vuole sapere username
     * @return username dell'utente
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static String prendiUsername (String email)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection c = DriverManager.getConnection(url, user, password);
        String username_utente;
        String stringa = "SELECT username FROM utenti WHERE email = '"+email+"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        username_utente = resultSet.getString("username");
        c.close();
        return username_utente;
    }

    /**
     * Metodo per prendere il nome di un tour dalla tabella disponibilita
     * @param email del cicerone che possiede il tour
     * @param id del tour di cui si vuole sapere il nome
     * @return il nome del tour
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static String prendiNomeTourDaTabellaDisponibilita(String email, int id)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        String nomeTour;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT nomeTour FROM disponibilita WHERE emailCicerone = '" +email + "' AND id = " + id;
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        nomeTour = resultSet.getString("nomeTour");
        c.close();
        return nomeTour;
    }

    /**
     * Metodo per prendere il nome di un tour dalla tabella tour

     * @param id del tour di cui si vuole sapere il nome
     * @return il nome del tour
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static String prendiNomeTourDaTabellaTour( int id)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        String nomeTour;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT nome FROM tour WHERE id = " + id;
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        nomeTour = resultSet.getString("nome");
        c.close();
        return nomeTour;
    }

    /**
     * Metodo per ritornare le date di un tour
     * @param email del cicerone che possiede il tour
     * @param id del tour di cui si vuole sapere le date
     * @return le date del tour
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static String prendiDataTourDaTabellaDisponibilita(String email, int id)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        String dataTour;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT dataTour FROM disponibilita WHERE emailCicerone = '" + email + "' AND id = " + id;
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        dataTour = resultSet.getString("dataTour");
        c.close();
        return dataTour;
    }

    /**
     * Metodo per contare quanti tour ha un cicerone
     * @param email del cicerone a cui dobbiamo contare i tour
     * @return il numero di tour del cicerone
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static int prendiNumeroTourCicerone(String email)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        int numero_tour;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT COUNT(nome) AS numero_tour FROM tour WHERE emailCicerone = '" + email +"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        numero_tour = resultSet.getInt("numero_tour");
        c.close();
        return numero_tour;
    }

    /**
     * Metodo per prendere il numero di posti massimo di partecipanti di un tour
     * @param email del cicerone che possiede il tour
     * @param nome del tour di cui si vuole sapere il numero di posti massimo
     * @return il numero di posti massimo di partecipanti al tour
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static int prendiNumeroMassimoTour(String email, String nome)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        int posti;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT numeroMassimo FROM tour WHERE emailCicerone = '" + email + "' AND nome = '" +nome+"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        posti = resultSet.getInt("numeroMassimo");
        c.close();
        return posti;
    }


    /**
     * Metodo per prendere l'insieme delle mail dei turisti che hanno prenotato uno specifico tour in una specifica data.
     * @param email del cicerone che fa il tour
     * @param nomeTour prenotato dal turista
     * @param dataTour prenotata dal turista
     * @return insieme delle email dei turisti che hanno prenotato un tour in una specifica data
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static String[] prendiEmailTurista(String email, String nomeTour, String dataTour)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        int numero_email;
        Connection c = DriverManager.getConnection(url, user, password);
        Statement stm = c.createStatement();
        String stringaCount = "SELECT COUNT(emailTurista) AS numero_email FROM prenotazioni WHERE emailCicerone ='"+email+"' AND nomeTour ='"+nomeTour+"' AND dataTour = '"+dataTour+"'";
        ResultSet resultSet = stm.executeQuery(stringaCount);
        resultSet.next();
        numero_email = resultSet.getInt("numero_email");
        String[] emailTurista = new String[numero_email];
        String stringa = "SELECT emailTurista FROM prenotazioni WHERE emailCicerone ='"+email+"' AND nomeTour ='"+nomeTour+"' AND dataTour = '"+dataTour+"'";
        Statement stm2 = c.createStatement();
        ResultSet resultSet2 = stm.executeQuery(stringa);
        int contatore = 0;
        while(resultSet2.next()){
            emailTurista[contatore] = resultSet2.getString("emailTurista");
            contatore++;
        }
        c.close();
        return emailTurista;
    }

    /**
     * Metodo per ritornare un elenco dei nomi delle guide di un'associazione
     * @param emailAssociazione dell'associazione di cui vogliamo sapere l'elenco dei nomi delle guide
     * @param nomeGuida nome della guida
     * @return elenco contenente i nomi delle guide dell'associazione
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static String[] prendiNomeGuida(String emailAssociazione, String nomeGuida)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        int numero_guida;
        Connection c = DriverManager.getConnection(url, user, password);
        Statement stm = c.createStatement();
        String stringaCount = "SELECT COUNT(nomeGuida) AS numero_guida FROM guide WHERE emailAssociazione ='"+emailAssociazione+"' AND nomeGuida <>'"+nomeGuida+"'";
        ResultSet resultSet = stm.executeQuery(stringaCount);
        resultSet.next();
        numero_guida = resultSet.getInt("numero_guida");
        String[] elencoGuide = new String[numero_guida];
        String stringa = "SELECT nomeGuida FROM guide WHERE emailAssociazione ='"+emailAssociazione+"' AND nomeGuida <>'"+nomeGuida+"'";
        Statement stm2 = c.createStatement();
        ResultSet resultSet2 = stm.executeQuery(stringa);
        int contatore = 0;
        while(resultSet2.next()){
            elencoGuide[contatore] = resultSet2.getString("nomeGuida");
            contatore++;
        }
        c.close();
        return elencoGuide;
    }

    public static int prendiIndiceSingolaDisponibilita(String emailCicerone, String nomeTour, String dataTour)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        int id;
        Connection c = DriverManager.getConnection(url, user, password);
        Statement stm = c.createStatement();
        String stringaCount = "SELECT id FROM disponibilita WHERE emailCicerone ='"+emailCicerone+"' AND nomeTour ='"+nomeTour+"' AND dataTour = '"+dataTour+"'";
        ResultSet resultSet = stm.executeQuery(stringaCount);
        resultSet.next();
        id = resultSet.getInt("id");
        c.close();
        return id;
    }

    public static int [] prendiIndiceDisponibilita(String email, String nomeTour)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        int numero_disponibilita;
        Connection c = DriverManager.getConnection(url, user, password);
        Statement stm = c.createStatement();
        String stringaCount = "SELECT COUNT(nomeTour) AS numero_disponibilita FROM disponibilita WHERE emailCicerone ='"+email+"' AND nomeTour ='"+nomeTour+"'";
        ResultSet resultSet = stm.executeQuery(stringaCount);
        resultSet.next();
        numero_disponibilita = resultSet.getInt("numero_disponibilita");
        int [] indiceDisponibilita;
        if(numero_disponibilita == 0){
            numero_disponibilita = 1;
            indiceDisponibilita = new int[numero_disponibilita];
            indiceDisponibilita[0] = -1;
            return indiceDisponibilita;
        }
        indiceDisponibilita = new int[numero_disponibilita];
        String stringa = "SELECT id FROM disponibilita WHERE emailCicerone ='"+email+"' AND nomeTour ='"+nomeTour+"'";
        Statement stm2 = c.createStatement();
        ResultSet resultSet2 = stm.executeQuery(stringa);
        int contatore = 0;
        while(resultSet2.next()){
            indiceDisponibilita[contatore] = resultSet2.getInt("id");
            contatore++;
        }
        c.close();
        return indiceDisponibilita;
    }

    /**
     * Metodo che ritorna gli indici delle disponibilita di una guida
     * @param email dell'associaizone
     * @param nomeGuida della guida da cui prendere le disponibilita
     * @return indici delle disponibilita id una guida
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static int [] prendiIndiceDisponibilitaPerGuidaAssociazione(String email, String nomeGuida)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        int numero_disponibilita;
        Connection c = DriverManager.getConnection(url, user, password);
        Statement stm = c.createStatement();
        String stringaCount = "SELECT COUNT(nomeTour) AS numero_disponibilita FROM disponibilita WHERE emailCicerone ='"+email+"' AND nomeGuida ='"+nomeGuida+"'";
        ResultSet resultSet = stm.executeQuery(stringaCount);
        resultSet.next();
        numero_disponibilita = resultSet.getInt("numero_disponibilita");
        int [] indiceDisponibilita;
        if(numero_disponibilita == 0){
            numero_disponibilita = 1;
            indiceDisponibilita = new int[numero_disponibilita];
            indiceDisponibilita[0] = -1;
            return indiceDisponibilita;
        }
        indiceDisponibilita = new int[numero_disponibilita];
        String stringa = "SELECT id FROM disponibilita WHERE emailCicerone ='"+email+"' AND nomeGuida ='"+nomeGuida+"'";
        Statement stm2 = c.createStatement();
        ResultSet resultSet2 = stm.executeQuery(stringa);
        int contatore = 0;
        while(resultSet2.next()){
            indiceDisponibilita[contatore] = resultSet2.getInt("id");
            contatore++;
        }
        c.close();
        return indiceDisponibilita;
    }

    /**
     * Metodo per sapere se la prenotazione di un turista e' stata pagata oppure no
     * @param nomeTour della prenotazione
     * @param dataTour della prenotazione
     * @param emailCicerone del tour prenotato
     * @param emailTurista del turista che ha prenotato
     * @return true se il turista ha pagato, false altrimenti
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static boolean prendiStatoDellaPrenotazione (String nomeTour, String dataTour, String emailCicerone, String emailTurista)  throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        boolean statoPrenotazione;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT statoPrenotazione FROM prenotazioni WHERE nomeTour = '"+nomeTour+"' AND dataTour = '"+dataTour+"' AND emailCicerone = '" + emailCicerone + "' AND emailTurista = '" +emailTurista+"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        statoPrenotazione = resultSet.getBoolean("statoPrenotazione");
        c.close();
        return statoPrenotazione;
    }

    /**
     * Metodo per visualizzare il prezzo di un tour
     * @param nomeTour di cui si vuole vedere il prezzo
     * @param emailCicerone del cicerone del tour di cui si vuole vedere il prezzo
     * @return il prezzo del tour
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static double visualizzaPrezzoTour (String nomeTour, String emailCicerone) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        double prezzoDelTour;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT prezzo FROM tour WHERE nome = '"+nomeTour+"'  AND emailCicerone = '" + emailCicerone + "'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        prezzoDelTour = resultSet.getDouble("prezzo");
        c.close();
        return prezzoDelTour;
    }

    /**
     * Metodo per visualizzare la descrizione di un tour
     * @param nomeTour del tour di cui si vuole visualizzare la descrizione
     * @param emailCicerone del cicerone che possiede il tour
     * @return descrizione del tour
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static String visualizzaDescrizioneTour (String nomeTour, String emailCicerone) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        String descrizioneTour;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT descrizione FROM tour WHERE nome = '"+nomeTour+"'  AND emailCicerone = '" + emailCicerone + "'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        descrizioneTour = resultSet.getString("descrizione");
        c.close();
        return descrizioneTour;
    }

    /**
     * Metodo per visualizzare il numero massimo dei posti prenotabili di un tour
     * @param nomeTour del tour
     * @param emailCicerone del cicerone a cui appartiene il tour
     * @return il numero massimo di posti prenotabili del tour
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static int visualizzaNumeroMassimoPosti(String nomeTour, String emailCicerone) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        int numeroMax;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT numeroMassimo FROM tour WHERE nome = '"+nomeTour+"'  AND emailCicerone = '" + emailCicerone + "'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        numeroMax = resultSet.getInt("numeroMassimo");
        c.close();
        return numeroMax;
    }

    public static int visualizzaNumeroMinimoPosti(String nomeTour, String emailCicerone) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        int numeroMin;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT numeroMinimo FROM tour WHERE nome = '"+nomeTour+"'  AND emailCicerone = '" + emailCicerone + "'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        numeroMin = resultSet.getInt("numeroMinimo");
        c.close();
        return numeroMin;
    }

    /**
     * Metodo per visualizzare la data di un tour
     * @param nomeTour del tour di cui si vuole sapere una specifica data
     * @param emailCicerone del cicerone che possiede il tour
     * @param id indice della dipsonibilita che si vuole modificare
     * @return la data del tour
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static String visualizzaDataDiUnTour (String nomeTour, String emailCicerone, int id) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        String data;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT dataTour FROM disponibilita WHERE nomeTour = '"+nomeTour+"'  AND emailCicerone = '" + emailCicerone + "' AND id = "+id;
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        data = resultSet.getString("dataTour");
        c.close();
        return data;
    }

    public static int visualizzaPostiPrenotati(String nomeTour, String dataTour, String emailCicerone, String emailTurista) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        int postiPrenotati;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT numeroPostiPrenotati FROM prenotazioni WHERE nomeTour = '"+nomeTour+"'  AND dataTour = '"+dataTour+"' AND  emailCicerone = '" + emailCicerone + "' AND emailTurista = '"+emailTurista+"'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        postiPrenotati = resultSet.getInt("numeroPostiPrenotati");
        c.close();
        return postiPrenotati;
    }

    /**
     * Metodo per sapere se annullaGratis e' true o false
     * @param id della prenotazione
     * @return true se si puo' annullare gratis, false se bisogna pagare una mora per annullare
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static boolean visualizzaAnnullagratis ( int id) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        boolean  gratis;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT annullaGratis FROM prenotazioni WHERE id = "+id;
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        gratis = resultSet.getBoolean("annullaGratis");
        if (gratis == true) {
            return true;
        }
        c.close();
        return false;
    }

    /**
     * Metodo per sapere se la prenotazione e' stata pagata oppure no
     * @param id della prenotazione
     * @return true se la prenotazione e' stata pagata, false altrimenti
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static boolean visualizzaStatoPrenotazione ( int id) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        boolean stato;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT statoPrenotazione FROM prenotazioni WHERE id = "+id;
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        stato = resultSet.getBoolean("statoPrenotazione");
        if (stato == true) {
            return true;
        }
        c.close();
        return false;
    }


    /**
     * Metodo per vedere nome e descrizione di una sosta
     * @param nomeTour
     * @param emailCicerone
     * @return nome e descrizione di una sosta
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void visualizzaSoste (String nomeTour, String emailCicerone) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        String nomeSosta;
        String descrizioneSosta;
        int id;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT nomeSosta, descrizioneSosta, idSosta FROM soste WHERE nomeTour = '"+nomeTour+"'  AND emailCicerone = '" + emailCicerone + "' ORDER BY idSosta";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        while (resultSet.next()) {
            nomeSosta = resultSet.getString("nomeSosta");
            descrizioneSosta = resultSet.getString("descrizioneSosta");
            id = resultSet.getInt("idSosta");
            System.out.println ("Sosta: "+nomeSosta+", descrizione: "+descrizioneSosta+", id = "+id);

        } c.close();
    }


    /**
     * Metodo che ritorna i giorni per la scadenza invito di un tour
     * @param nomeTour
     * @param emailCicerone
     * @return giorni della scadenza invito di un tour
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static int visualizzaGiorniScadenzaInvito(String nomeTour, String emailCicerone) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        int giorni;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT giorniScadenzaInvito FROM tour WHERE nome = '"+nomeTour+"'  AND emailCicerone = '" + emailCicerone + "'";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        giorni = resultSet.getInt("giorniScadenzaInvito");
        c.close();
        return giorni;
    }

    public static String prendiNomeTagProposto(int id) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        String nomeTag;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT nome FROM tagProposti WHERE idTagProposto = "+id;
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        nomeTag = resultSet.getString("nome");
        c.close();
        return nomeTag;
    }

    public static int IdTagPropostoMinimo() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        int idTag;
        Connection c = DriverManager.getConnection(url, user, password);
        String stringa = "SELECT MIN(idTagProposto) AS id FROM tagProposti ";
        Statement stm = c.createStatement();
        ResultSet resultSet = stm.executeQuery(stringa);
        resultSet.next();
        idTag = resultSet.getInt("id");
        c.close();
        return idTag;
    }

}
