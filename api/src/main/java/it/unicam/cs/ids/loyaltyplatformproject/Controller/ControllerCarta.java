package it.unicam.cs.ids.loyaltyplatformproject.Controller;

import it.unicam.cs.ids.loyaltyplatformproject.Services.DBMSController;
import it.unicam.cs.ids.loyaltyplatformproject.Model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControllerCarta {

    private List<CartaFedelta> cartaFedeltaList;

    public ControllerCarta() {
        this.cartaFedeltaList = new ArrayList<>();
    }

    public void addCarta(CartaFedelta c) throws DateMistake, SQLException {
        if(findById(c.getId())==null){
            String query= "INSERT INTO cartefedelta (id_cf, nome_cf, scadenza_cf, punticorrenti, livellocorrente, percentualelivello nome_puntovendita, cliente_id ) VALUES('" + c.getId() + "', '" + c.getNomeCarta() + "', '" +c.getScadenza() + "', '" + c.getPuntiCorrenti() + "', '" + c.getLivelliCorrenti() + "', '" + c.getPercentualeLivello() + "', '" + c.getCartaPuntoVendita().getNomePuntoVendita() + "', '" + c.getCliente() + "')";
            DBMSController.insertQuery(query);
        }
        throw new DateMistake("La carta é gia esistente");
    }

    public CartaFedelta findById(int id) {
        CartaFedelta cartaFedelta=null;
        for (CartaFedelta p: this.cartaFedeltaList){
            if(p.getId()==id)
                cartaFedelta=p;
        }
        if(cartaFedelta==null){
            throw new NullPointerException();
        }
        return cartaFedelta;
    }

    /**
     * Metodo che restituisce
     * tutte le carte di un singolo cliente;
     * @return
     * @throws SQLException
     */
    public List<CartaFedelta> visualizzaCarteFedelta(Cliente c) throws SQLException {
        String table="cartefedelta";
        ResultSet resultSet= DBMSController.selectAllFromTable(table);
        while (resultSet.next()){
            if(c.getId()==resultSet.getInt("cliente_id")){
                ControllerRegistrazione cr= new ControllerRegistrazione();
                ControllerPuntoVendita cp= new ControllerPuntoVendita();
                PuntoVendita aggiungiPuntoVendita= cp.findById(resultSet.getString("nome_puntovendita"));
                Cliente aggiungiCliente= cr.getByID(resultSet.getInt("clienti_id"));
                CartaFedelta cf= new CartaFedelta(resultSet.getString("nome_cf"),
                        resultSet.getDate("scadenza_cf"), aggiungiPuntoVendita,
                        aggiungiCliente);
                this.cartaFedeltaList.add(cf);
            }
        }
        return this.cartaFedeltaList;
    }

}
