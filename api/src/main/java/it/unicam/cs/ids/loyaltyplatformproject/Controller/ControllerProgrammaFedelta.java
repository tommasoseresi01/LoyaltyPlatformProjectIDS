package it.unicam.cs.ids.loyaltyplatformproject.Controller;

import it.unicam.cs.ids.loyaltyplatformproject.Services.DBMSController;
import it.unicam.cs.ids.loyaltyplatformproject.Model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ControllerProgrammaFedelta {

    private List<ProgrammaFedelta> listaProgrammi;

    public ControllerProgrammaFedelta() {
        listaProgrammi = new ArrayList<>();
    }

    public List<ProgrammaFedelta> getListaProgrammi() {
        return listaProgrammi;
    }

    public void addProgrammaFedelta(ProgrammaFedelta programFel) throws SQLException {
        listaProgrammi.add(programFel);
        String query = "";
        if (programFel instanceof ProgrammaPunti progPunti) {
            query = "INSERT INTO programpunti (id_pp, nome_pp, descrizione_pp, valorexpunto, totpunti ) VALUES('" + progPunti.getId() + "','" + progPunti.getNome() + "','" + progPunti.getDescrizione() + "', '" + progPunti.getValoreXPunto() + "', '" + progPunti.getTotPunti() + "')";
        }
        if (programFel instanceof ProgrammaLivelli progLivelli) {
            query = "INSERT INTO programlivelli (id_pl, nome_pl, descrizione_pl, livellomax, puntitot_pl, valorexpercentualelivello ) VALUES('" + progLivelli.getId() + "','" + progLivelli.getNome() + "','" + progLivelli.getDescrizione() + "', '" + progLivelli.getLivelloMax() + "', '" + progLivelli.getPuntiTot() + "', '" + progLivelli.getValoreXPercentualeLivello() + "')";
        }
        DBMSController.insertQuery(query);
    }

    public List<ProgrammaFedelta> visualizzaProgrammiPunti() throws SQLException {
        ResultSet resultset1 = DBMSController.selectAllFromTable("programpunti");
        while (resultset1.next()) {
            ProgrammaFedelta progfel = new ProgrammaPunti(resultset1.getInt("id_pp"),
                    resultset1.getString("nome_pp"), resultset1.getString("descrizione_pp"),
                    resultset1.getInt("valorexpunto"), resultset1.getInt("totpunti"));
            this.listaProgrammi.add(progfel);
        }
        return this.listaProgrammi;
    }

    public List<ProgrammaFedelta> visualizzaProgrammiLivelli() throws SQLException {
        ResultSet resultset = DBMSController.selectAllFromTable("programlivelli");
        while (resultset.next()) {
            ProgrammaFedelta proglev = new ProgrammaLivelli(resultset.getInt("id_pl"),
                    resultset.getString("nome_pl"), resultset.getString("descrizione_pl"),
                    resultset.getInt("livellomax"), resultset.getInt("puntitot_pl"),
                    resultset.getInt("valorexpercentualelivello"));
            this.listaProgrammi.add(proglev);
        }
        return this.listaProgrammi;
    }

    public ProgrammaFedelta findById(int id) {
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

    public boolean deleteById(int id) throws SQLException {
        if (findById(id) == null) {
            throw new NullPointerException("programma fedelta non esistente");
        }
        for (ProgrammaFedelta p : this.listaProgrammi) {
            if (id == p.getId())
                this.listaProgrammi.remove(p);
            String query = "";
            if (p instanceof ProgrammaPunti pp) {
                query = "DELETE FROM programpunti WHERE nome_pp='" + pp.getNome() + "'";
            } else if (p instanceof ProgrammaLivelli pl) {
                query = "DELETE FROM programlivelli WHERE nome_pl='" + pl.getNome() + "';";
            }
            DBMSController.removeQuery(query);
            return true;
        }
        return false;
    }

    public void addProgrammiTitolari(TitolarePuntoVendita t, int id) throws SQLException, DateMistake {
        if (findById(id) != null) {
            if (findById(id) instanceof ProgrammaPunti pp) {
                String query = "INSERT INTO programpuntititolare (id_ppt, nome_ppt, descrizione_ppt, valorexpunto_ppt, totpunti_ppt, titolariid_t ) VALUES('" + pp.getId() + "','" + pp.getNome() + "','" + pp.getDescrizione() + "', '" + pp.getValoreXPunto() + "', '" + pp.getTotPunti() + "', '" + t.getId() + "')";
                DBMSController.insertQuery(query);
            } else if (findById(id) instanceof ProgrammaLivelli pl) {
                String query = "INSERT INTO programlivellititolare (id_plt, nome_plt, descrizione_plt, livellomax_plt, puntitot_plt, valorexpercentualelivello_plt, titolariid_t ) VALUES('" + pl.getId() + "','" + pl.getNome() + "','" + pl.getDescrizione() + "', '" + pl.getLivelloMax() + "', '" + pl.getPuntiTot() + "', '" + pl.getValoreXPercentualeLivello() + "', '" + t.getId() + "')";
                DBMSController.insertQuery(query);
            }
        }else  throw new DateMistake();
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

    public void updateProgrammaGestore(ProgrammaFedelta pf) throws SQLException, DateMistake {
        if (findById(pf.getId()) != null) {
            if (findById(pf.getId()) instanceof ProgrammaPunti pp) {
                String query = "UPDATE programpunti SET valorexpunto = '" + pp.getValoreXPunto() + "',totpunti = '" + pp.getTotPunti() + "' WHERE id_pp = '" + pp.getId() + "'";
                DBMSController.insertQuery(query);
            } else if (findById(pf.getId()) instanceof ProgrammaLivelli pl) {
                String query = "UPDATE programlivelli SET livellomax = '" + pl.getLivelloMax() + "',puntitot_pl = '" + pl.getPuntiTot() + "', valorexpercentualelivello ='" + pl.getValoreXPercentualeLivello() + "' WHERE id_pl = '" + pl.getId() + "'";
                DBMSController.insertQuery(query);
            }
        } else throw new DateMistake();

    }



}
