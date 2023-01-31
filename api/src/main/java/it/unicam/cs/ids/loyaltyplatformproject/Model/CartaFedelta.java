package it.unicam.cs.ids.loyaltyplatformproject.Model;

import it.unicam.cs.ids.loyaltyplatformproject.Controller.ControllerPuntoVendita;

import java.sql.SQLException;
import java.util.Date;

public class CartaFedelta {

    private int id;

    private String nomeCarta;

    private Date scadenza;

    private final PuntoVendita cartaPuntoVendita;

    private Cliente cliente;

    private int puntiCorrenti;
    private int livelliCorrenti;
    private int percentualeLivello;
    private ControllerPuntoVendita controllerPuntoVendita;

    public CartaFedelta(String nomeCarta, Date scadenza, PuntoVendita cartaPuntoVendita, Cliente cliente) throws SQLException {
        this.cartaPuntoVendita = cartaPuntoVendita;
        this.cliente=cliente;
        this.id=randomInt();
        this.nomeCarta = nomeCarta;
        this.scadenza = scadenza;
        controllerPuntoVendita= new ControllerPuntoVendita();
        controllerPuntoVendita.visualizzaProgrammiPuntiTitolare(cartaPuntoVendita);
        controllerPuntoVendita.visualizzaProgrammiLivelliTitolare(cartaPuntoVendita);
        if(controllerPuntoVendita.getCountPunti()>0 && controllerPuntoVendita.getCountLivelli()==0){
            puntiCorrenti=0;
        } else if (controllerPuntoVendita.getCountLivelli()>0 && controllerPuntoVendita.getCountPunti()==0) {
            livelliCorrenti=0;
            percentualeLivello=0;
        }
        else if (controllerPuntoVendita.getCountPunti()>0 && controllerPuntoVendita.getCountLivelli()>0){
            puntiCorrenti=0;
            livelliCorrenti=0;
            percentualeLivello=0;
        }
    }

    public CartaFedelta(String nomeCarta, PuntoVendita cartaPuntoVendita, Cliente cliente) {
        this.id=randomInt();
        this.nomeCarta = nomeCarta;
        this.cartaPuntoVendita = cartaPuntoVendita;
        this.cliente = cliente;
        controllerPuntoVendita= new ControllerPuntoVendita();
        if(controllerPuntoVendita.getCountPunti()>0 && controllerPuntoVendita.getCountLivelli()==0){
            puntiCorrenti=0;
        } else if (controllerPuntoVendita.getCountLivelli()>0 && controllerPuntoVendita.getCountPunti()==0) {
            livelliCorrenti=0;
            percentualeLivello=0;
        }
        else if (controllerPuntoVendita.getCountPunti()>0 && controllerPuntoVendita.getCountLivelli()>0){
            puntiCorrenti=0;
            livelliCorrenti=0;
            percentualeLivello=0;
        }
    }

    public CartaFedelta(int id, String nomeCarta, Date scadenza, PuntoVendita cartaPuntoVendita, Cliente cliente, int puntiCorrenti, int livelliCorrenti, int percentualeLivello) {
        this.id = id;
        this.nomeCarta = nomeCarta;
        this.scadenza = scadenza;
        this.cartaPuntoVendita = cartaPuntoVendita;
        this.cliente = cliente;
        this.puntiCorrenti = puntiCorrenti;
        this.livelliCorrenti = livelliCorrenti;
        this.percentualeLivello = percentualeLivello;
    }

    private int randomInt(){
        double doubleRandom=Math.random()*6000;
        int intRandom= (int) doubleRandom;
        return intRandom;
    }

    public int  getId() {
        return id;
    }

    public String getNomeCarta() {
        return nomeCarta;
    }

    public void setNomeCarta(String nomeCarta) {
        this.nomeCarta = nomeCarta;
    }

    public Date getScadenza() {
        return scadenza;
    }

    public void setScadenza(Date scadenza) {
        this.scadenza = scadenza;
    }

    public PuntoVendita getCartaPuntoVendita() {
        return cartaPuntoVendita;
    }

    public int getPuntiCorrenti() {
        return puntiCorrenti;
    }

    public int getLivelliCorrenti() {
        return livelliCorrenti;
    }

    public int getPercentualeLivello() {
        return percentualeLivello;
    }

    public Cliente getCliente() {
        return cliente;
    }
}