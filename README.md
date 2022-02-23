# MatresPatriaeTeam_IDS

ISTRUZIONI PER CREARE IL DATABASE (POSTGRES)

CREARE IN POSTGRES UN DATABASE DI NOME Cicero.
DETTAGLI DATABASE (SONO SCRITTI ANCHE NELLE CLASSI DEI DBManager come variabili di istanza della classe):
Host: localhost
Port: 5432
User: postgres
password: cicero 
driver: org.postgresql.Driver;
url: jdbc:postgresql://localhost:5432/Cicero

NB: SE NON SI UTILIZZA POSTGRES, CAMBIARE I VALORI RIPORTATI SOPRA SUL CODICE (CAMBIARE I VALORI PER OGNI VARIABILE DELLE CLASSI DBManager ).
 LE VARIABILI DI ISTANZA SONO SCRITTE NEL SEGUENTE MODO: 
 
 private static DBManagerConfronti dbManagerConfronti;
    private final static String url = "jdbc:postgresql://localhost:5432/Cicero";
    private final static String user = "postgres";
    private final static String password = "cicero";
    private final static String driver = "org.postgresql.Driver";

COPIARE NEL DATABASE IL CONTENUTO DEL FILE Inizializzazione Database.


