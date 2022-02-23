import Actors.Amministrazione;
import Actors.UtenteGenerico;
import Boundary.MenuUtenteGenerico;

import java.io.IOException;

public class main {
    public static void main(String args[]){
        Amministrazione.getInstance();
        System.out.println("BENVENUTO IN CICERO");
        MenuUtenteGenerico.getInstance().menu();

    }
}
