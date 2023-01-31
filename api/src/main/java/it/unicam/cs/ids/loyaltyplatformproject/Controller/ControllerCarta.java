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
            String query= "INSERT INTO cartefedelta (id_cf, nome_cf, scadenza_cf, punticorrenti, livellocorrente, percentualelivello, puntovenditanome_pv, clientiid_c ) VALUES('" + c.getId() + "', '" + c.getNomeCarta() + "', '" +c.getScadenza() + "', '" + c.getPuntiCorrenti() + "', '" + c.getLivelliCorrenti() + "', '" + c.getPercentualeLivello() + "', '" + c.getCartaPuntoVendita().getNomePuntoVendita() + "', '" + c.getCliente().getId() + "')";
            DBMSController.insertQuery(query);
        }
        else throw new DateMistake("La carta é gia esistente");
    }

    public CartaFedelta findById(int id) {
        CartaFedelta cartaFedelta=null;
        for (CartaFedelta p: this.cartaFedeltaList){
            if(p.getId()==id)
                cartaFedelta=p;
        }
        if(cartaFedelta==null){
            return null;
        }
        return cartaFedelta;
    }

    /**
     * Metodo che restituisce
     * tutte le carte di un singolo cliente;
     * @return
     * @throws SQLException
     */
    public List<CartaFedelta> visualizzaCarteFedelta(Cliente c) throws SQLException, DateMistake {
        String table="cartefedelta";
        ResultSet resultSet= DBMSController.selectAllFromTable(table);
        while (resultSet.next()){
            if(c.getId()== resultSet.getInt("clientiid_c")){
                ControllerRegistrazione cr= new ControllerRegistrazione();
                cr.visualizzaClienti();
                ControllerPuntoVendita cp= new ControllerPuntoVendita();
                cp.visualizzaPuntoVendita();
                PuntoVendita aggiungiPuntoVendita= cp.findById(resultSet.getString("puntovenditanome_pv"));
                Cliente aggiungiCliente= cr.getByID(resultSet.getInt("clientiid_c"));
                CartaFedelta cf= new CartaFedelta(resultSet.getInt("id_cf"), resultSet.getString("nome_cf"),
                        resultSet.getDate("scadenza_cf"), aggiungiPuntoVendita,
                        aggiungiCliente, resultSet.getInt("punticorrenti"),
                        resultSet.getInt("livellocorrente"), resultSet.getInt("percentualelivello"));
                this.cartaFedeltaList.add(cf);
            }
        }
        return this.cartaFedeltaList;
    }
    @Override
    public String toString() {
        String string ="";
        for (CartaFedelta cf : cartaFedeltaList ){
            string+= "id: ["+ cf.getId()+"] \n" +
                    "scadenza: ["+ cf.getScadenza()+"] \n" +
                    "cliente: ["+ cf.getCliente().getUsername()+"] \n" +
                    "puntovendita: ["+cf.getCartaPuntoVendita()+"]\n" +
                    "punticorrenti: ["+cf.getPuntiCorrenti()+"]\n" +
                    "livellocorrente: ["+cf.getLivelliCorrenti()+"]\n" +
                    "-------------------------------------------------\n";
        }
        return string;
    }

}