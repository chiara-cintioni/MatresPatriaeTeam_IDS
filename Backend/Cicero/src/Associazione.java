import java.util.HashSet;

public class Associazione extends Cicerone{
    private String nome;
    private String email;
    private String partitaIVA;
    private HashSet<String> listaGuideTuristiche;

   public Associazione(String nome, String email, String partitaIVA){
       this.nome = nome;
       this.email = email;
       this.partitaIVA = partitaIVA;
       this.listaGuideTuristiche = new HashSet<String>();
   }


}
