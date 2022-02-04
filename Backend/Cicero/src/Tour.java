//import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;


//@Data
/**
 * Classe che crea un oggetto di tipo Tour. Oggetto di tipo Tour viene preso da TourCalendario che lo inserisce nel calendario del Cicerone.
 * Oggetto di tipo tour ha: un contatoreTour che mi indica il numero di tour che vengono creati, un nome, un numeroMinimo di persone che possono prenotare il tour,
 * il numeroMassimo di persone che possono prenotare il tour, il prezzo del tour, una descrizione, e un id.
 */
/**
 * @author Chiara
 *
 */
public class Tour {
    
    private static int contatoreTour = 0;
    private String nome;
    private int numeroMinimo;
    private int numeroMassimo;
    private double prezzo;
    private String descrizione;
    private final int id;
    private String nomeGuida;
    private int giorniScadenzaInvito;
    private ArrayList<TourCalendario> elencoDisponibilitaDelTour;



    public Tour(String nome, int numeroMinimo, int numeroMassimo, double prezzo, String descrizione, String nomeGuida) {
        this.nome = nome;
        this.numeroMinimo = numeroMinimo;
        this.numeroMassimo = numeroMassimo;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        contatoreTour++;
        this.id = contatoreTour;
        this.nomeGuida = nomeGuida;
        this.giorniScadenzaInvito = 0;
        this.elencoDisponibilitaDelTour = new ArrayList<TourCalendario>();
    }

    //Metodo per stampare la disponibilità di un tour
    public void visualizzaDateDelTour () {
        int cont = 1;
        for (TourCalendario t : insertionSort()) {
                        System.out.println("Il tour '"+ t.getTour().getNome()+"' è disponibile nella seguente data: "+t.getGiorno()+ "/"+t.getMese()+" con codice: "+cont);
                        cont++;
        }
    }

    /**
     * Metodo per ordinare le date in cui un tour e' disponibile
     * @return la lista ordinata delle date del tour
     */
    private LinkedList<TourCalendario> insertionSort() {
        LinkedList<TourCalendario> elencoOrdinato = new LinkedList<TourCalendario>();
        TourCalendario appoggioMin = null;

        int contatore = 0;
        while (contatore < elencoDisponibilitaDelTour.size()) {
            for (TourCalendario t : getElencoDisponibilitaDelTour()) {
                if (elencoOrdinato.contains(t));
               else if (appoggioMin == null) {
                    appoggioMin = t;
                } else if (t.getMese() < appoggioMin.getMese()) {
                   appoggioMin = t;
                } else if (t.getMese() == appoggioMin.getMese()) {
                    if (t.getGiorno() < appoggioMin.getGiorno()) {
                        appoggioMin = t;
                    }
                }
            }
            elencoOrdinato.addLast(appoggioMin);
            appoggioMin = null;
            contatore++;
        }

return elencoOrdinato;
    }


    public void setNomeGuida(String nomeGuida) {
        this.nomeGuida = nomeGuida;
    }

    public ArrayList<TourCalendario> getElencoDisponibilitaDelTour() {
        return elencoDisponibilitaDelTour;
    }

    public void setElencoDisponibilitaDelTour(ArrayList<TourCalendario> elencoDisponibilitaDelTour) {
        this.elencoDisponibilitaDelTour = elencoDisponibilitaDelTour;
    }



    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public int getNumeroMinimo() {
        return numeroMinimo;
    }


    public void setNumeroMinimo(int numeroMinimo) {
        this.numeroMinimo = numeroMinimo;
    }


    public int getNumeroMassimo() {
        return numeroMassimo;
    }


    public void setNumeroMassimo(int numeroMassimo) {
        this.numeroMassimo = numeroMassimo;
    }


    public double getPrezzo() {
        return prezzo;
    }


    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }


    public String getDescrizione() {
        return descrizione;
    }


    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getNomeGuida() {
        return nomeGuida;
    }

    public int getGiorniScadenzaInvito() {
        return giorniScadenzaInvito;
    }

    public void setGiorniScadenzaInvito(int giorniScadenzaInvito) {
        if(giorniScadenzaInvito >= 2){
            this.giorniScadenzaInvito = giorniScadenzaInvito;
        }

    }

    public int getId() {
        return id;
    }

    public TourCalendario getTourCalendario (int index) {

       return elencoDisponibilitaDelTour.get(index);
    }


    @Override
    public String toString() {
        return "Tour{" +
                "nome='" + nome + '\'' +
                ", numeroMinimo=" + numeroMinimo +
                ", numeroMassimo=" + numeroMassimo +
                ", prezzo=" + prezzo +
                ", descrizione='" + descrizione + '\'' +
                ", id=" + id +
                ", nomeGuida='" + nomeGuida + '\'' +
                ", giorniScadenzaInvito=" + giorniScadenzaInvito +
                '}';
    }
}
