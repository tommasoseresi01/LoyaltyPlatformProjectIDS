package it.unicam.cs.ids.loyaltyplatformproject.Controller;

import it.unicam.cs.ids.loyaltyplatformproject.Model.*;
import it.unicam.cs.ids.loyaltyplatformproject.Services.DBMSController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControllerCoupon {

    private List<Coupon> listaCoupon;

    public ControllerCoupon() {
        this.listaCoupon = new ArrayList<>();
    }

    public List<Coupon> getListaCoupon() {
        return listaCoupon;
    }

    public void addCoupon(Coupon c) throws SQLException {
        String query = "INSERT INTO coupon (programpuntititolareid_ppt, id_coupon, nome_coupon, costopunti) VALUES('" + c.getPp().getId() + "','" + c.getIdCoupon() + "','" + c.getNomeCoupon() + "', '" + c.getCostoPunti() + "')";
        DBMSController.insertQuery(query);
    }

    public List<Coupon> visualizzaCoupon(PuntoVendita pv) throws SQLException, DateMistake {
        String table="coupon";
        ResultSet resultSet= DBMSController.selectAllFromTable(table);
        while (resultSet.next()){
            ControllerRegistrazione cr=new ControllerRegistrazione();
            ControllerPuntoVendita cp= new ControllerPuntoVendita();
            cr.visualizzaClienti();
            Cliente cliente=cr.getByID(resultSet.getInt("clientiid_c"));
            cp.visualizzaProgrammiPuntiTitolare(pv);
            cp.visualizzaProgrammiLivelliTitolare(pv);
            ProgrammaFedelta pf=cp.getById(resultSet.getInt("programpuntititolareid_ppt"));
            if(pf instanceof ProgrammaPunti pp) {
                Coupon cc = new Coupon(resultSet.getInt("id_coupon"), resultSet.getString("nome_coupon"),
                        resultSet.getInt("costopunti"), pp, cliente);
                this.listaCoupon.add(cc);
            }
        }
        return this.listaCoupon;
    }

    public Coupon getByID(int id){
        Coupon coupon = null;
        for (Coupon cc : listaCoupon) {
            if (cc.getIdCoupon() == id)
                coupon = cc;
        }
        if (coupon == null) {
            return null;
        }
        return coupon;
    }


    @Override
    public String toString() {
        String string ="";
        for (Coupon coupon : listaCoupon ){
            string+= "id: ["+coupon.getIdCoupon()+"] \n" +
                    "nome: ["+coupon.getNomeCoupon()+"] \n" +
                    "costo punti: ["+coupon.getCostoPunti()+"]\n" +
                    "id del programma di appartenenza: "+coupon+"\n" +
                    "id del cliente che ha riscattato il coupon: "+coupon+"" +
                    "------------------------------------\n";
        }
        return string;
    }
}
