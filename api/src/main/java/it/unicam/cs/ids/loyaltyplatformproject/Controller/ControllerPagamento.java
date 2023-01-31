package it.unicam.cs.ids.loyaltyplatformproject.Controller;

import it.unicam.cs.ids.loyaltyplatformproject.Model.*;
import it.unicam.cs.ids.loyaltyplatformproject.Services.DBMSController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControllerPagamento {

    private TitolarePuntoVendita titolarePuntoVendita;

    private List<CartaDiCredito> listaCartaDiCredito;

    public ControllerPagamento() {
        this.listaCartaDiCredito= new ArrayList<>();
    }



    /**
     * se il pagamento è stato effettuato correttamente
     *
     * @return true , altrimenti false;
     */

    public boolean payment(TitolarePuntoVendita t) throws SQLException {
        if (t.getCarta().getSaldoCarta()>GestorePiattaforma.getCostoAdesionePiattaforma()){
            t.getCarta().decrementaSaldo(GestorePiattaforma.getCostoAdesionePiattaforma());
            return true;
        }
        return false;
    }

    public void addCarta(CartaDiCredito cc) throws SQLException {
        String query = "INSERT INTO cartadicredito (id_cc, scadenza_cc, cvv, pin, saldocarta) VALUES('" + cc.getNumeroCarta() + "','" + cc.getDateScadenza() + "','" + cc.getCVV() + "','" + cc.getPin() + "','" + cc.getSaldoCarta() + "')";
        DBMSController.insertQuery(query);
    }

    public List<CartaDiCredito> visualizzaCartaDiCredito() throws SQLException, DateMistake {
        String table="cartadicredito";
        ResultSet resultSet= DBMSController.selectAllFromTable(table);
        while (resultSet.next()){
            CartaDiCredito cc= new CartaDiCredito(resultSet.getInt("id_cc"),resultSet.getDate("scadenza_cc"),
                    resultSet.getString("cvv"),resultSet.getString("pin"),
                    resultSet.getDouble("saldocarta"));
            this.listaCartaDiCredito.add(cc);
        }
        return this.listaCartaDiCredito;
    }

    public CartaDiCredito getByID(int id){
        CartaDiCredito cartaCredito = null;
        for (CartaDiCredito cc : listaCartaDiCredito) {
            if (cc.getNumeroCarta() == id)
                cartaCredito = cc;
        }
        if (cartaCredito == null) {
            return null;
        }
        return cartaCredito;
    }



}