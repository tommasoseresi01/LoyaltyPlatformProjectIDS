package it.unicam.cs.ids.loyaltyplatformproject.Model;

public class PuntoVendita {

    private String nomePuntoVendita;
    private String indirizzo;
    private TitolarePuntoVendita titolare;

    public PuntoVendita(String nomePuntoVendita, String indirizzoPuntoVendita, TitolarePuntoVendita titolare) {
        this.nomePuntoVendita = nomePuntoVendita;
        this.indirizzo = indirizzoPuntoVendita;
        this.titolare = titolare;
    }

    public String getNomePuntoVendita() {
        return nomePuntoVendita;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public TitolarePuntoVendita getTitolare() {
        return titolare;
    }
}
