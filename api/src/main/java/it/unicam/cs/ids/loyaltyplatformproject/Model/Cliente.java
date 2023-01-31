package it.unicam.cs.ids.loyaltyplatformproject.Model;

import it.unicam.cs.ids.loyaltyplatformproject.Controller.ControllerCarta;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends UtenteGenerico {

    private List<CartaFedelta> carteFedelta;
    private final ControllerCarta controllerCarta;


    public Cliente(String nome, String cognome, String indirizzo,String email, String username, String password, int telefono) {
        super(nome, cognome, indirizzo, email, username, password, telefono);
        this.carteFedelta = new ArrayList<>();
        this.controllerCarta= new ControllerCarta();
    }

    public Cliente(int id, String nome, String cognome, String indirizzo, String email, String username, String password, int telefono) {
        super(id, nome, cognome, indirizzo, email, username, password, telefono);
        this.carteFedelta = new ArrayList<>();
        this.controllerCarta= new ControllerCarta();
    }

    public List<CartaFedelta> getCarteFedelta() {
        return carteFedelta;
    }

    public void creaCarta(CartaFedelta cf) throws DateMistake, SQLException {
        for (CartaFedelta c: this.carteFedelta){
            if(c.getCartaPuntoVendita()==cf.getCartaPuntoVendita()){
                throw new DateMistake("Non puoi creare piu di 2 carte di uno stesso Punto Vendita");
            }
        }
        this.controllerCarta.addCarta(cf);
        this.carteFedelta.add(cf);
    }


}