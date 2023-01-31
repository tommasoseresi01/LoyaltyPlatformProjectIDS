package it.unicam.cs.ids.loyaltyplatformproject.Controller;

import it.unicam.cs.ids.loyaltyplatformproject.Services.DBMSController;
import it.unicam.cs.ids.loyaltyplatformproject.Model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ControllerPuntoVendita {

    private Set<ProgrammaFedelta> listaProgrammi;
    private List<Cliente> listaClienti;
    private Set<PuntoVendita> listaPuntoVendita;

    private int countPunti;
    private int countLivelli;

    public ControllerPuntoVendita() {
        this.listaProgrammi= new HashSet<>();
        this.listaClienti= new ArrayList<>();
        this.listaPuntoVendita=new HashSet<>();
        countPunti=0;
        countLivelli=0;
    }

    public Set<ProgrammaFedelta> getListaProgrammi() {
        return listaProgrammi;
    }

    public int getCountPunti() {
        return countPunti;
    }

    public int getCountLivelli() {
        return countLivelli;
    }

    /**
     * Metodo che visualizza il programma a
     * punti del titolare della proprio punto vendita
     * @return il set dei programmi a punti
     * @throws SQLException
     */
    public Set<ProgrammaFedelta> visualizzaProgrammiPuntiTitolare(PuntoVendita pv) throws SQLException {
        String table="programpuntititolare";
        ResultSet resultset= DBMSController.selectAllFromTable(table);
        while (resultset.next()){
            int id_titolare=resultset.getInt("titolariid_t");
            if(id_titolare==pv.getTitolare().getId()) {
                ProgrammaFedelta pp = new ProgrammaPunti(resultset.getInt("id_ppt"), resultset.getString("nome_ppt"),
                        resultset.getString("descrizione_ppt"),
                        resultset.getInt("valorexpunto_ppt"), resultset.getInt("totpunti_ppt"));
                this.listaProgrammi.add(pp);
                countPunti++;
            }
        }
        return this.listaProgrammi;
    }

    /**
     * Metodo che visualizza il programma a
     * livelli del titolare della proprio punto vendita
     * @return il set dei programmi a punti
     * @throws SQLException
     */
    public Set<ProgrammaFedelta> visualizzaProgrammiLivelliTitolare(PuntoVendita pv) throws SQLException {
        String table="programlivellititolare";
        ResultSet resultset= DBMSController.selectAllFromTable(table);
        while (resultset.next()){
            int id_titolare=resultset.getInt("titolariid_t");
            if(id_titolare==pv.getTitolare().getId()) {
                ProgrammaFedelta pp = new ProgrammaLivelli(resultset.getInt("id_plt"), resultset.getString("nome_plt"),
                        resultset.getString("descrizione_plt"),
                        resultset.getInt("livellomax_plt"), resultset.getInt("puntitot_plt"),
                        resultset.getInt("valorexpercentualelivello_plt"));
                this.listaProgrammi.add(pp);
                countLivelli++;
            }
        }
        return this.listaProgrammi;
    }

    public void addPuntoVendita(PuntoVendita pv) throws DateMistake,SQLException {
        for(PuntoVendita p: this.listaPuntoVendita){
            if(findById(pv.getNomePuntoVendita()).equals(p.getNomePuntoVendita())){
                throw new DateMistake("Il punto vendita é gia esistente");
            }
        }
        String query="INSERT INTO puntovendita (nome_pv, indirizzo_pv, titolariid_t ) VALUES('" + pv.getNomePuntoVendita() + "','" + pv.getIndirizzo() + "','" + pv.getTitolare().getId() + "')";
        DBMSController.insertQuery(query);
    }

    public PuntoVendita findById(String nome){
        PuntoVendita puntoVendita = null;
        for (PuntoVendita pv : this.listaPuntoVendita) {
            if (pv.getNomePuntoVendita().equals(nome))
                puntoVendita=pv;
        }
        if (puntoVendita == null) {
            throw new NullPointerException();
        }
        return puntoVendita;
    }

    public Set<PuntoVendita> visualizzaPuntoVendita() throws SQLException, DateMistake {
        String table = "puntovendita";
        ResultSet resultset = DBMSController.selectAllFromTable(table);
        while (resultset.next()) {
            ControllerRegistrazione conn = new ControllerRegistrazione();
            TitolarePuntoVendita titolareDaAggiungere = conn.findById(resultset.getInt("titolariid_t"));
            PuntoVendita puntoVendita = new PuntoVendita(resultset.getString("nome_pv"),
                    resultset.getString("indirizzo_pv"), titolareDaAggiungere);
            this.listaPuntoVendita.add(puntoVendita);
        }
        return this.listaPuntoVendita;
    }

    public int incrementaPuntiCarta(int spesaEffettuata, ProgrammaPunti pp, CartaFedelta cf, Coupon coupon) throws SQLException {
        int puntiTotalizzati=cf.getPuntiCorrenti()+(spesaEffettuata/pp.getValoreXPunto());
        String query="UPDATE cartefedelta SET punticorrenti ='"+puntiTotalizzati+"'WHERE id_cf= '"+cf.getId()+"'";
        if(puntiTotalizzati>=pp.getTotPunti()){
            //sblocca coupon da ritirare
            String query1="UPDATE coupon SET clientiid_c ='"+cf.getCliente().getId()+"'WHERE id_coupon= '"+coupon.getIdCoupon()+"'";
            int differenzapunti=puntiTotalizzati-coupon.getCostoPunti();
            query="UPDATE cartefedelta SET punticorrenti ='"+differenzapunti+"'WHERE id_cf= '"+cf.getId()+"'";
            DBMSController.insertQuery(query1);
        }
        DBMSController.insertQuery(query);
        return puntiTotalizzati;
    }

    public int incrementaLivelloCarta(int spesaEffettuata, ProgrammaLivelli pl, CartaFedelta cf) throws SQLException {
        int percentualeDaIncrementare=cf.getPercentualeLivello()+(spesaEffettuata/pl.getValoreXPercentualeLivello());
        if(percentualeDaIncrementare>=pl.getPuntiTot()){
            if(cf.getLivelliCorrenti()<pl.getLivelloMax()){
                int differenza=percentualeDaIncrementare-pl.getPuntiTot();
                int incrementaLivello=cf.getLivelliCorrenti()+1;
                String query="UPDATE cartefedelta SET livellocorrente ='"+incrementaLivello+"', percentualelivello= '"+differenza+"'WHERE id_cf= '"+cf.getId()+"'";
                DBMSController.insertQuery(query);
            }
        }
        String query="UPDATE cartefedelta SET percentualelivello= '"+percentualeDaIncrementare+"'WHERE id_cf= '"+cf.getId()+"'";
        DBMSController.insertQuery(query);
        return percentualeDaIncrementare;
    }

    public boolean deleteById(int id) throws SQLException {
        if (getById(id) == null) {
            throw new NullPointerException("programma fedelta non esistente");
        }
        for (ProgrammaFedelta p : this.listaProgrammi) {
            if (id == p.getId())
                this.listaProgrammi.remove(p);
            String query = "";
            if (p instanceof ProgrammaPunti pp) {
                query = "DELETE FROM programpuntititolare WHERE nome_ppt='" + pp.getNome() + "'";
            } else if (p instanceof ProgrammaLivelli pl) {
                query = "DELETE FROM programlivellititolare WHERE nome_plt='" + pl.getNome() + "';";
            }
            DBMSController.removeQuery(query);
            return true;
        }
        return false;
    }

    public ProgrammaFedelta getById(int id) {
        ProgrammaFedelta programFel = null;
        for (ProgrammaFedelta p : this.listaProgrammi) {
            if (p.getId() == id)
                programFel = p;
        }
        if (programFel == null) {
            throw new NullPointerException();
        }
        return programFel;
    }
    @Override
    public String toString() {
        String string ="";
        for (ProgrammaFedelta pf : listaProgrammi ){
            string+= "id: ["+pf.getId()+"] \n" +
                    "nome: ["+pf.getNome()+"] \n" +
                    "descrizione: ["+pf.getDescrizione()+"]\n" +
                    "------------------------------------\n";
        }
        return string;
    }

    public String toStringPuntiVendita() {
        String string ="";
        for (PuntoVendita pv : listaPuntoVendita ){
            string+= "id: ["+ pv.getNomePuntoVendita()+"] \n" +
                    "nome: ["+ pv.getIndirizzo()+"] \n" +
                    "descrizione"+ pv.getTitolare()+"]\n" +
                    "------------------------------------\n";
        }
        return string;
    }

}