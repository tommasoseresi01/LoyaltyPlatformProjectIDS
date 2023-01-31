package it.unicam.cs.ids.loyaltyplatformproject.Model;

import it.unicam.cs.ids.loyaltyplatformproject.Controller.ControllerProgrammaFedelta;
import it.unicam.cs.ids.loyaltyplatformproject.Controller.ControllerRegistrazione;

import java.sql.SQLException;

public class TitolarePuntoVendita extends UtenteGenerico{


    private final ControllerRegistrazione effettuaRegistrazione;

    private final ControllerProgrammaFedelta creaProgrammaFedelta;

    private boolean abilitato;
    private CartaDiCredito carta;

    public TitolarePuntoVendita(int id, String nome, String cognome, String indirizzo, String emailBusiness, String username, String password, int telefono,boolean abilitato, CartaDiCredito carta) {
        super(id, nome, cognome, indirizzo, emailBusiness, username, password, telefono);
        this.carta = carta;
        this.abilitato=abilitato;
        this.effettuaRegistrazione= new ControllerRegistrazione();
        this.creaProgrammaFedelta= new ControllerProgrammaFedelta();
    }

    public TitolarePuntoVendita(int id, String nome, String cognome, String indirizzo, String email, String username, String password, int telefono, boolean abilitato) {
        super(id, nome, cognome, indirizzo, email, username, password, telefono);
        this.abilitato=abilitato;
        this.effettuaRegistrazione= new ControllerRegistrazione();
        this.creaProgrammaFedelta= new ControllerProgrammaFedelta();
    }

    public TitolarePuntoVendita(String nome, String cognome, String indirizzo, String email, String username, String password, int telefono) {
        super(nome, cognome, indirizzo, email, username, password, telefono);
        this.abilitato=false;
        this.effettuaRegistrazione= new ControllerRegistrazione();
        this.creaProgrammaFedelta= new ControllerProgrammaFedelta();
    }
    public CartaDiCredito getCarta() {
        return carta;
    }

    public boolean isAbilitato() {
        return abilitato;
    }

    public void effettuaPagamento() throws DateMistake, SQLException {
        effettuaRegistrazione.addTitolare(this);
        abilitato=true;
    }

    public void aggiungiProgrammaFedeltaPuntoVendita(int id) throws AbilitationException, SQLException, DateMistake {
        if(abilitato){
            this.creaProgrammaFedelta.visualizzaProgrammiPunti();
            this.creaProgrammaFedelta.visualizzaProgrammiLivelli();
            this.creaProgrammaFedelta.addProgrammiTitolari(this, id);
        }else{
            throw new AbilitationException("Esercente non abilitato alla piattaforma");
        }
    }





}
