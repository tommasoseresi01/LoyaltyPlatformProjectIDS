package it.unicam.cs.ids.loyaltyplatformproject.Model;

import it.unicam.cs.ids.loyaltyplatformproject.Controller.ControllerCarta;
import it.unicam.cs.ids.loyaltyplatformproject.Controller.ControllerPuntoVendita;

import java.sql.SQLException;

public class CommessoPuntoVendita extends UtenteGenerico {

    private final ControllerPuntoVendita controllerPuntoVendita;
    private final ControllerCarta controllerCarta;
    private PuntoVendita pv;

    public CommessoPuntoVendita(String nome, String cognome, String indirizzo, String emailBusiness, String username, String password, int telefono, PuntoVendita pv) {
        super(nome, cognome, indirizzo,emailBusiness, username, password, telefono);
        this.pv=pv;
        this.controllerPuntoVendita=new ControllerPuntoVendita();
        this.controllerCarta= new ControllerCarta();
    }

    public CommessoPuntoVendita(int id, String nome, String cognome, String indirizzo, String email, String username, String password, int telefono, PuntoVendita pv) {
        super(id, nome, cognome, indirizzo, email, username, password, telefono);
        this.pv = pv;
        this.controllerPuntoVendita=new ControllerPuntoVendita();
        this.controllerCarta= new ControllerCarta();
    }

    public PuntoVendita getPv() {
        return pv;
    }

    public void incrementaCarta(int spesaEffettuata, ProgrammaFedelta pf, CartaFedelta cf, Coupon coupon) throws SQLException {
        controllerPuntoVendita.visualizzaProgrammiPuntiTitolare(pv);
        controllerPuntoVendita.visualizzaProgrammiLivelliTitolare(pv);
        if(controllerPuntoVendita.getCountPunti()>0 && controllerPuntoVendita.getCountLivelli()==0){
            if(pf instanceof ProgrammaPunti pp)
                controllerPuntoVendita.incrementaPuntiCarta(spesaEffettuata, pp, cf, coupon);
        }
        else if(controllerPuntoVendita.getCountPunti()==0 && controllerPuntoVendita.getCountLivelli()>0){
            if(pf instanceof ProgrammaLivelli pl)
                controllerPuntoVendita.incrementaLivelloCarta(spesaEffettuata, pl, cf);
        }
        else if(controllerPuntoVendita.getCountLivelli()>0 && controllerPuntoVendita.getCountPunti()>0){
            if (pf instanceof ProgrammaPunti pp){
                controllerPuntoVendita.incrementaPuntiCarta(spesaEffettuata, pp, cf, coupon);
            }
            if(pf instanceof ProgrammaLivelli pl){
                controllerPuntoVendita.incrementaLivelloCarta(spesaEffettuata, pl, cf);
            }
        }
    }

}
