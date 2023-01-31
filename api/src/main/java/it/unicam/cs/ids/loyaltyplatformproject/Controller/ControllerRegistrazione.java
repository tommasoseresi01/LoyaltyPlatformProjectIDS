package it.unicam.cs.ids.loyaltyplatformproject.Controller;

import it.unicam.cs.ids.loyaltyplatformproject.Services.DBMSController;
import it.unicam.cs.ids.loyaltyplatformproject.Model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControllerRegistrazione {

    private List<Cliente> clienti;

    private List<TitolarePuntoVendita> titolariAttivita;

    private List<CommessoPuntoVendita> listaCommessi;

    private SistemaBanca sistemaBanca;

    public ControllerRegistrazione() {
        this.clienti = new ArrayList<>();
        this.titolariAttivita = new ArrayList<>();
        this.listaCommessi=new ArrayList<>();
        this.sistemaBanca = new SistemaBanca();
    }

    public List<Cliente> getClienti() {
        return clienti;
    }

    public List<TitolarePuntoVendita> getTitolariAttivita() {
        return titolariAttivita;
    }

    public List<CommessoPuntoVendita> getListaCommessi() {
        return listaCommessi;
    }

    public void registrazioneTitolare(TitolarePuntoVendita t) throws SQLException {
        if (validazioneDati(t)) {
            String query = "INSERT INTO titolari (id_t, nome_t, cognome_t, indirizzo_t, email_t, username_t, password, abilitato, telefono_t) VALUES('" + t.getId() + "','" + t.getNome() + "','" + t.getCognome() + "','" + t.getIndirizzo() + "','" + t.getEmail() + "','" + t.getUsername() + "' ,'" + t.getPassword() + "' ,'" + t.isAbilitato() + "', '" + t.getTelefono() + "' )";
            DBMSController.insertQuery(query);
        }
    }

    public void addTitolare(TitolarePuntoVendita t) throws SQLException, DateMistake {
        if (sistemaBanca.verificaPagamento(t) == StatoPagamento.PAGATO) {
            String query = "UPDATE titolari SET abilitato = 'true' WHERE id_t = '" + t.getId() + "'";
            DBMSController.insertQuery(query);
        } else {
            throw new DateMistake();
        }
    }

    public TitolarePuntoVendita findById(int id) throws SQLException, DateMistake {
        TitolarePuntoVendita titolare = null;
        for (TitolarePuntoVendita t : getAllEsercenti()) {
            if (t.getId() == id)
                titolare = t;
        }
        if (titolare == null) {
            throw new NullPointerException();
        }
        return titolare;
    }

    /**
     * metodo per controllare se i dati inseriti sono corretti
     *
     * @param ug
     * @return true se i dati sono corretti, false altrimenti.
     */
    private boolean validazioneDati(UtenteGenerico ug) {
        if (ug.getNome() == null || ug.getEmail() == null || ug.getUsername() == null || ug.getPassword() == null) {
            return false;
        }
        return true;
    }

    public List<TitolarePuntoVendita> getAllEsercenti() throws SQLException, DateMistake {
        String t = "titolari";
        ResultSet resultset = DBMSController.selectAllFromTable(t);
        while (resultset.next()) {
            ControllerPagamento conn = new ControllerPagamento();
            conn.visualizzaCartaDiCredito();
            CartaDiCredito daAggiungere = conn.getByID(resultset.getInt("cartadicreditoid_cc"));
            TitolarePuntoVendita titolare = new TitolarePuntoVendita(resultset.getInt("id_t"),
                    resultset.getString("nome_t"), resultset.getString("cognome_t"),
                    resultset.getString("indirizzo_t"), resultset.getString("email_t"),
                    resultset.getString("username_t"), resultset.getString("password"),
                    resultset.getInt("telefono_t"), resultset.getBoolean("abilitato"),
                    daAggiungere);
            this.titolariAttivita.add(titolare);
        }
        return titolariAttivita;
    }

    public void registrazioneClienti(Cliente c) throws SQLException {
        if (validazioneDati(c)) {
            String query = "INSERT INTO clienti (id_c, nome_c, cognome_c, indirizzo_c, email_c, username_c, password_c, telefono_c) VALUES('" + c.getId() + "','" + c.getNome() + "','" + c.getCognome() + "','" + c.getIndirizzo() + "','" + c.getEmail() + "','" + c.getUsername() + "' ,'" + c.getPassword() + "' ,'" + c.getTelefono() + "' )";
            DBMSController.insertQuery(query);
        }

    }

    public List<Cliente> visualizzaClienti() throws SQLException {
        String table="clienti";
        ResultSet resultSet= DBMSController.selectAllFromTable(table);
        while (resultSet.next()){
            Cliente c= new Cliente(resultSet.getInt("id_c"), resultSet.getString("nome_c"),resultSet.getString("cognome_c"),
                    resultSet.getString("indirizzo_c"),resultSet.getString("email_c"),
                    resultSet.getString("username_c"), resultSet.getString("password_c"),
                    resultSet.getInt("telefono_c"));
            this.clienti.add(c);
        }
        return clienti;
    }

    public Cliente getByID(int id) throws SQLException {
        visualizzaClienti();
        for(Cliente c: this.clienti){
            if(id==c.getId())
                return c;
        }
        return null;
    }

    public void registrazioneCommessoPuntoVendita(CommessoPuntoVendita cpv) throws SQLException {
        if (validazioneDati(cpv)) {
            String query = "INSERT INTO commessopuntovendita (id_cp, nome_cp, cognome_cp, indirizzo_cp, email_cp, username_cp, password, telefono_cp, puntovenditanome_pv) VALUES('" + cpv.getId() + "','" + cpv.getNome() + "','" + cpv.getCognome() + "','" + cpv.getIndirizzo() + "','" + cpv.getEmail() + "','" + cpv.getUsername() + "' ,'" + cpv.getPassword() + "' ,'" + cpv.getTelefono() + "', '" + cpv.getPv().getNomePuntoVendita() + "' )";
            DBMSController.insertQuery(query);
        }

    }

    public List<CommessoPuntoVendita> visualizzaCommessi() throws SQLException, DateMistake {
        String table="commessopuntovendita";
        ResultSet resultSet= DBMSController.selectAllFromTable(table);
        while (resultSet.next()){
            ControllerPuntoVendita conn= new ControllerPuntoVendita();
            getAllEsercenti();
            conn.visualizzaPuntoVendita();
            PuntoVendita pvAggiungi= conn.findById(resultSet.getString("puntovenditanome_pv"));
            CommessoPuntoVendita cpv= new CommessoPuntoVendita(resultSet.getInt("id_cp"), resultSet.getString("nome_cp"),resultSet.getString("cognome_cp"),
                    resultSet.getString("indirizzo_cp"),resultSet.getString("email_cp"),
                    resultSet.getString("username_cp"), resultSet.getString("password"),
                    resultSet.getInt("telefono_cp"), pvAggiungi);
            this.listaCommessi.add(cpv);
        }
        return this.listaCommessi;
    }

    public CommessoPuntoVendita getById(int id) throws SQLException, DateMistake {
        visualizzaCommessi();
        for(CommessoPuntoVendita c: this.listaCommessi){
            if(id==c.getId())
                return c;
        }
        return null;
    }

    public void updateCarta(TitolarePuntoVendita t, CartaDiCredito cc) throws SQLException {
        String query="UPDATE titolari SET cartadicreditoid_cc = '" + cc.getNumeroCarta() + "' WHERE id_t = '" + t.getId() + "'";
        DBMSController.insertQuery(query);
    }


    public String toStringCliente() {
        String string ="";
        for (Cliente c : clienti ){
            string+= "id: ["+c.getId()+"] \n" +
                    "nome: ["+c.getNome()+"] \n" +
                    "cognome: ["+c.getCognome()+"]\n" +
                    "indirizzo: ["+c.getIndirizzo()+"]\n" +
                    "email: ["+c.getEmail()+"]\n" +
                    "------------------------------------ \n";
        }
        return string;
    }

}}