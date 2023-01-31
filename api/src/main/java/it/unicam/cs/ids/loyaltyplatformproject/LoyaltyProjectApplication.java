package it.unicam.cs.ids.loyaltyplatformproject;

import it.unicam.cs.ids.loyaltyplatformproject.Controller.*;
import it.unicam.cs.ids.loyaltyplatformproject.Model.*;
import it.unicam.cs.ids.loyaltyplatformproject.Services.DBMSController;

import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;


public class LoyaltyProjectApplication {

    private static final Scanner sc = new Scanner(System.in);
    private static final ControllerProgrammaFedelta controllerProgrammaFedelta = new ControllerProgrammaFedelta();
    private static final ControllerRegistrazione controllerRegistrazioni = new ControllerRegistrazione();
    private static final ControllerCarta controllerCarta = new ControllerCarta();
    private static final ControllerPuntoVendita controllerPuntoVendita = new ControllerPuntoVendita();
    private static final ControllerPagamento controllerPagamento = new ControllerPagamento();
    private static final ControllerCoupon controllerCoupon = new ControllerCoupon();

    public static void main(String[] args) throws SQLException, DateMistake, AbilitationException {
        DBMSController.init();
        boolean flag = false;
        do {
            System.out.println("Welcome to Loyalty Platform!!/n");
            System.out.println("Seleziona il numero per scegliere l'azione da eseguire: /n");
            System.out.println("1-Effettua il login");
            System.out.println("2-Effettua la registrazione");
            System.out.println("3-per uscire dalla piattaforma");
            switch (provaScannerInt()) {
                case 1 -> login();
                case 2 -> registrazione();
                case 3 -> flag = true;
            }
        } while (!flag);

        System.out.println("Loyalty platform ti ringrazia e ti augura un buon proseguimento:)");
    }

    private static void registrazione() throws SQLException, DateMistake, AbilitationException {
        boolean flag = false;
        do {
            System.out.println("Inserire il nome");
            String nome = sc.nextLine();
            System.out.println("Inserire il cognome");
            String cognome = sc.nextLine();
            System.out.println("Inserire il indirizzo");
            String indirizzo = sc.nextLine();
            System.out.println("Inserire il email business");
            String email = sc.nextLine();
            System.out.println("Inserire il username: /n");
            String username = sc.nextLine();
            System.out.println("Inserire la password");
            String password = sc.nextLine();
            System.out.println("Inserire il numero di telefono");
            int telefono = sc.nextInt();

            System.out.println("Inserisci il numero per il ruolo con cui ti vuoi registrare: /n");
            System.out.println("1-Se sei un cliente");
            System.out.println("2-Se sei un Titolare Punto Vendita");
            System.out.println("3-Se sei un Commesso Punto Vendita");
            switch (provaScannerInt()) {
                case 1 -> {
                    Cliente c = new Cliente(nome, cognome, indirizzo, email, username, password, telefono);
                    controllerRegistrazioni.registrazioneClienti(c);
                    System.out.println("Magnifico, la registrazione alla Loyalty platform é avvenuta con successo" +
                            ", ora il tuo id é: " + c.getId() + " ");
                    flag = true;
                }
                case 2 -> {
                    System.out.println("Inserire il nome del punto vendita che gestisci");
                    String nomePuntoVendita = sc.nextLine();
                    System.out.println("Inserire indirizzo del punto vendita:");
                    String indirizzoPuntoVendita = sc.nextLine();
                    TitolarePuntoVendita t = new TitolarePuntoVendita(nome, cognome, indirizzo, email, username, password, telefono);
                    controllerRegistrazioni.registrazioneTitolare(t);
                    PuntoVendita pv = new PuntoVendita(nomePuntoVendita, indirizzoPuntoVendita, t);
                    controllerPuntoVendita.addPuntoVendita(pv);
                    System.out.println("Magnifico, la registrazione alla Loyalty platform é avvenuta con successo" +
                            ", ora il tuo id é: " + t.getId() + " ");
                    flag = true;
                }

                case 3 -> {
                    System.out.println("Inserisci il nome del punto vendita");
                    String nomePv = sc.nextLine();
                    controllerRegistrazioni.getAllEsercenti();
                    controllerPuntoVendita.visualizzaPuntoVendita();
                    PuntoVendita pv = controllerPuntoVendita.findById(nomePv);
                    CommessoPuntoVendita cpv = new CommessoPuntoVendita(nome, cognome, indirizzo, email, username, password, telefono, pv);
                    controllerRegistrazioni.registrazioneCommessoPuntoVendita(cpv);
                    System.out.println("Magnifico, la registrazione alla Loyalty platform é avvenuta con successo" +
                            ", ora il tuo id é: " + cpv.getId() + " ");
                    flag = true;
                }
            }
        } while (!flag);
    }


    private static void login() throws SQLException, DateMistake, AbilitationException {
        boolean flag = false;
        do {
            System.out.println("Seleziona il numero per scegliere il ruolo: /n");
            System.out.println("1-Cliente");
            System.out.println("2-Titolare Punto Vendita");
            System.out.println("3-Commesso Punto Vendita");
            System.out.println("4-gestore Piattaforma");
            System.out.println("5-per uscire dal menu login");
            switch (provaScannerInt()) {
                case 1 -> homeClienti();
                case 2 -> homeTitolare();
                case 3 -> homeCommesso();
                case 4 -> homeGestore();
                case 5 -> flag = true;
            }
        } while (!flag);
    }

    private static void homeGestore() throws SQLException {
        boolean flag = false;
        do {
            System.out.println("Seleziona il numero per scegliere l'azione da eseguire: ");
            System.out.println("1- Aggiungere un programma fedelta");
            System.out.println("2- Elimina programma fedelta");
            System.out.println("3- Ritorna al menu scelta dei ruoli");
            switch (provaScannerInt()) {
                case 1 -> aggiungi();
                case 2 -> elimina();
                case 3 -> {
                    flag = true;
                }
            }
        } while (!flag);
    }

    private static void homeCommesso() throws SQLException, DateMistake {
        boolean flag = false;
        CommessoPuntoVendita cpv1 = null;
        do {
            System.out.println("Inserire il username: /n");
            String username = sc.nextLine();
            System.out.println("Inserire la password");
            String password = sc.nextLine();
            boolean locale = false;
            for (CommessoPuntoVendita cpv : controllerRegistrazioni.visualizzaCommessi()) {
                if (cpv.getUsername().equals(username) && cpv.getPassword().equals(password)) {
                    cpv1 = new CommessoPuntoVendita(cpv.getId(), cpv.getNome(), cpv.getCognome(), cpv.getIndirizzo(), cpv.getEmail(), cpv.getUsername(), cpv.getPassword(), cpv.getTelefono(), cpv.getPv());
                    locale = true;
                }
            }
            if (!locale) {
                System.out.println("Username o password con valori errati");
                flag = true;
            }
            if (locale) {
                System.out.println("Benvenuto " + cpv1.getUsername() + " : id " + cpv1.getId() + "");
                System.out.println("Seleziona l'operazione da eseguire");
                System.out.println("1-Creazione carta");
                System.out.println("2-Incrementa carta");
                System.out.println("3-Ritorna al menu scelta dei ruoli");
                switch (provaScannerInt()) {
                    case 1 -> {
                        controllerRegistrazioni.visualizzaClienti();
                        System.out.println("Inserire il nome della carta da creare");
                        String nomeCarta = sc.nextLine();
                        System.out.println("Inserire la scadenza della carta");
                        long scadenzaCarta = sc.nextLong();
                        System.out.println(controllerRegistrazioni.toStringCliente());
                        System.out.println("Inserire l'id del cliente che vuole creare la carta");
                        int idCliente = sc.nextInt();
                        Date scadenzaCf = new Date(scadenzaCarta);
                        CartaFedelta cf = new CartaFedelta(nomeCarta, scadenzaCf, cpv1.getPv(), controllerRegistrazioni.getByID(idCliente));
                        controllerCarta.addCarta(cf);
                        System.out.println("La carta del cliente " + idCliente + " é stata creata");
                        flag = true;
                    }
                    case 2 -> {
                        controllerRegistrazioni.visualizzaClienti();
                        System.out.println(controllerRegistrazioni.toStringCliente());
                        System.out.println("Inserisci l'id del cliente per aumentare la sua carta");
                        int idCliente = sc.nextInt();
                        controllerCarta.visualizzaCarteFedelta(controllerRegistrazioni.getByID(idCliente));
                        System.out.println(controllerCarta.toString());
                        System.out.println("Inserisci l'id della carta del cliente selezionato");
                        int idCarta = sc.nextInt();
                        controllerPuntoVendita.visualizzaProgrammiPuntiTitolare(cpv1.getPv());
                        controllerPuntoVendita.visualizzaProgrammiLivelliTitolare(cpv1.getPv());
                        System.out.println(controllerPuntoVendita.toString());
                        System.out.println("Inserisci l'id del programma che vuoi utilizzare per la carta");
                        int idPf = sc.nextInt();
                        System.out.println("Inserisci la spesa effettuata dal cliente");
                        int spesa = sc.nextInt();
                        controllerCoupon.visualizzaCoupon(cpv1.getPv());
                        System.out.println(controllerCoupon.toString());
                        System.out.println("Inserisci il coupon che potrebbe essere riscattato");
                        int coupon = sc.nextInt();
                        cpv1.incrementaCarta(spesa, controllerPuntoVendita.getById(idPf), controllerCarta.findById(idCarta), controllerCoupon.getByID(coupon));
                        System.out.println("La carta é stata incrementata con successo");
                        flag = true;
                    }
                    case 3 -> {
                        flag = true;
                    }
                }
            }
        } while (!flag);
    }
    private static void homeTitolare() throws SQLException, DateMistake, AbilitationException {
        boolean flag=false;
        TitolarePuntoVendita titolare= null;
        do {
            System.out.println("Inserire il username: /n");
            String username = sc.nextLine();
            System.out.println("Inserire la password");
            String password = sc.nextLine();
            boolean locale = false;
            for (TitolarePuntoVendita tpv : controllerRegistrazioni.getAllEsercenti()) {
                if (tpv.getUsername().equals(username) && tpv.getPassword().equals(password)) {
                    titolare= new TitolarePuntoVendita(tpv.getId(), tpv.getNome(), tpv.getCognome(), tpv.getIndirizzo(), tpv.getEmail(), tpv.getUsername(), tpv.getPassword(), tpv.getTelefono(), tpv.isAbilitato());
                    locale = true;
                }
            }
            if (!locale) {
                System.out.println("Username o password con valori errati");
                flag = true;
            }
            if(locale)
            {
                System.out.println("Benvenuto "+ titolare.getUsername()+" : id "+titolare.getId()+"");
                System.out.println("Seleziona l'operazione da eseguire");
                System.out.println("1-Effettua pagamento piattaforma");
                System.out.println("2-Aggiungi Programma al proprio punto vendita");
                System.out.println("3-Elimina programma dal proprio punto vendita");
                System.out.println("4-Impostazioni carta di credito");
                System.out.println("5-Ritorna al menu scelta dei ruoli");
                switch (provaScannerInt()){
                    case 1->{
                        titolare=controllerRegistrazioni.findById(titolare.getId());
                        if(!titolare.isAbilitato()){
                            titolare.effettuaPagamento();
                            System.out.println("Ora sei abilitato alla piattaforma");
                            flag=true;}
                        else{System.out.println("Hai gia aderito alla piattaforma");
                            flag=true;}

                    }
                    case 2->{
                        titolare=controllerRegistrazioni.findById(titolare.getId());
                        if(titolare.isAbilitato()){
                            controllerProgrammaFedelta.visualizzaProgrammiPunti();
                            controllerProgrammaFedelta.visualizzaProgrammiLivelli();
                            System.out.println(controllerProgrammaFedelta.toString());
                            System.out.println("Inserisci l'id del programma da aggiungere al proprio punto vendita");
                            int id=sc.nextInt();
                            ProgrammaFedelta pf= controllerProgrammaFedelta.findById(id);
                            if(pf instanceof ProgrammaPunti pp){
                                System.out.println("modifica il campo valore per singolo punto del programma "+id+"");
                                int setValoreXPunto=sc.nextInt();
                                System.out.println("modifica il campo dei punti da totalizzare del programma "+id+"");
                                int setTotPunti=sc.nextInt();
                                pp.setValoreXPunto(setValoreXPunto);
                                pp.setTotPunti(setTotPunti);
                            }
                            else if(pf instanceof ProgrammaLivelli pl){
                                System.out.println("modifica il campo livello massimo del programma "+id+"");
                                int setLivelloMax=sc.nextInt();
                                System.out.println("modifica il campo dei totali punti del programma "+id+"");
                                int setPuntiTot=sc.nextInt();
                                System.out.println("modifica il campo valore per singolo percentuale livello del programma "+id+"");
                                int setValoreXPercentuale=sc.nextInt();
                                pl.setLivelloMax(setLivelloMax);
                                pl.setPuntiTot(setPuntiTot);
                                pl.setValoreXPercentualeLivello(setValoreXPercentuale);
                            }
                            controllerProgrammaFedelta.updateProgrammaGestore(pf);
                            titolare.aggiungiProgrammaFedeltaPuntoVendita(pf.getId());
                            System.out.println("Il programma con id: "+id+" é stato aggiunto");
                            System.out.println("Creazione Coupon");
                            boolean flagCoupon=false;
                            do {
                                String nomeCoupon = "coupon";
                                System.out.println("Inserisci i punti da totalizzare per sbloccare questo coupon");
                                int costoCoupon = sc.nextInt();
                                if (pf instanceof ProgrammaPunti pp) {
                                    Coupon coupon = new Coupon(nomeCoupon, costoCoupon, pp, null);
                                    controllerCoupon.addCoupon(coupon);
                                    System.out.println("Hai inserito il coupon del programma " + pp.getNome() + "");
                                    System.out.println("Inserisci false per inserire un altro coupon, altrimenti true per uscire");
                                    flagCoupon = sc.nextBoolean();
                                }
                            }while (!flagCoupon);
                            flag=true;
                        }
                        else{System.out.println("Non hai ancora aderito alla piattaforma");
                            flag=true;}
                    }
                    case 3->{
                        if(titolare.isAbilitato()){
                            PuntoVendita puntoVendita= null;
                            for(PuntoVendita pv: controllerPuntoVendita.visualizzaPuntoVendita()){
                                if(pv.getTitolare().getId()==titolare.getId()){
                                    puntoVendita= new PuntoVendita(pv.getNomePuntoVendita(), pv.getIndirizzo(), pv.getTitolare());
                                }
                            }
                            controllerPuntoVendita.visualizzaProgrammiPuntiTitolare(puntoVendita);
                            controllerPuntoVendita.visualizzaProgrammiLivelliTitolare(puntoVendita);
                            System.out.println(controllerPuntoVendita.toString());
                            System.out.println("inserire l'id del programma da eliminare");
                            int id=sc.nextInt();
                            controllerPuntoVendita.deleteById(id);
                            System.out.println("Il programma "+id+" é stato rimosso");
                            flag=true;
                        }
                    }
                    case 4->{
                        do {
                            System.out.println("1-Aggiungi i dati della carta di credito");
                            System.out.println("2-Ricarica carta");
                            System.out.println("3-Ritorna al menu operazioni del titolare ");
                            switch (provaScannerInt()) {
                                case 1 -> {
                                    System.out.println("Inserire il cvv");
                                    String cvv = sc.nextLine();
                                    System.out.println("Inserire il pin");
                                    String pin = sc.nextLine();
                                    System.out.println("Inserire il numero della carta");
                                    int numeroCarta = sc.nextInt();
                                    System.out.println("Inserire data di scadenza");
                                    long scadenzaDate = sc.nextInt();
                                    Date scadenza= new Date(scadenzaDate);
                                    CartaDiCredito cc=new CartaDiCredito(numeroCarta, scadenza,cvv,pin);
                                    controllerPagamento.addCarta(cc);
                                    controllerRegistrazioni.updateCarta(titolare,cc);
                                    System.out.println("carta inserita");
                                    flag=true;
                                }
                                case 2 -> {
                                    titolare=controllerRegistrazioni.findById(titolare.getId());
                                    if(titolare.getCarta()!=null){
                                        System.out.println("Seleziona l'importo da caricare sulla carta");
                                        int importo= sc.nextInt();
                                        titolare.getCarta().incrementaSaldo(importo);
                                        System.out.println("Ricarica Effettuata");
                                        flag=true;
                                    }
                                    else{System.out.println("Devi inserire la carta per ricaricare");}
                                    flag=true;
                                }
                                case 3->{flag=true;}
                            }
                        }while(!flag);
                    }
                    case 5->{flag=true;}
                }
            }
        }while(!flag);
    }


    private static void homeClienti() throws SQLException, DateMistake {
        boolean flag=false;
        Cliente cliente= null;
        do {
            System.out.println("Inserire il username: /n");
            String username = sc.nextLine();
            System.out.println("Inserire la password");
            String password = sc.nextLine();
            boolean locale = false;
            for (Cliente c : controllerRegistrazioni.visualizzaClienti()) {
                if (c.getUsername().equals(username) && c.getPassword().equals(password)) {
                    cliente= new Cliente(c.getId(), c.getNome(), c.getCognome(), c.getIndirizzo(), c.getEmail(), c.getUsername(), c.getPassword(), c.getTelefono());
                    locale = true;
                }
            }
            if (!locale) {
                System.out.println("Username o password con valori errati");
                flag = true;
            }
            if(locale)
            {
                System.out.println("Benvenuto "+ cliente.getUsername()+" : id "+cliente.getId()+"");
                System.out.println("Seleziona l'operazione da eseguire");
                System.out.println("1-Cerca Punto vendita");
                System.out.println("2-Visualizza profilo");
                System.out.println("3-Ritorna al menu scelta dei ruoli");
                switch (provaScannerInt()){
                    case 1->{
                        controllerPuntoVendita.visualizzaPuntoVendita();
                        System.out.println(controllerPuntoVendita.toStringPuntiVendita());
                        System.out.println("inserire il nome di un punto vendita esistente");
                        String nomePv=sc.nextLine();
                        System.out.println("Inserire il nome della carta da creare");
                        String nomeCarta=sc.nextLine();
                        System.out.println("Inserire la scadenza della carta");
                        long scadenzaCarta= sc.nextLong();
                        Date scadenzaCf= new Date(scadenzaCarta);
                        CartaFedelta cf= new CartaFedelta(nomeCarta,scadenzaCf,controllerPuntoVendita.findById(nomePv),cliente);
                        cliente.creaCarta(cf);
                        System.out.println("Ottimo Hai creato la carta fedelta con "+nomePv+"");
                        flag=true;
                    }
                    case 2->{
                        controllerCarta.visualizzaCarteFedelta(cliente);
                        System.out.println("Lista delle proprie carte fedelta");
                        System.out.println(controllerCarta.toString());
                        flag=true;
                    }
                    case 3->{flag=true;}
                }
            }
        }while(!flag);
    }
    private static void elimina() throws SQLException {
        System.out.println("Inserire il nome");
        String nome = sc.nextLine();
        System.out.println("Inserire id del programma");
        int id = sc.nextInt();
        ProgrammaFedelta programmaFedelta = new ProgrammaFedelta(nome, id);
        controllerProgrammaFedelta.visualizzaProgrammiPunti();
        controllerProgrammaFedelta.visualizzaProgrammiLivelli();
        controllerProgrammaFedelta.deleteById(programmaFedelta.getId());
        System.out.println("IL programma" + nome + " é stato eliminato");
    }

    private static void aggiungi() throws SQLException {
        System.out.println("Inserire il nome");
        String nome = sc.nextLine();
        System.out.println("Inserire la descrizione");
        String descrizione = sc.nextLine();
        System.out.println("Inserire 1- per aggiungere un programma punti /n" +
                "inserire 2- per aggiungere un programma a livelli ");
        int number = sc.nextInt();
        if (number == 1) {
            ProgrammaFedelta programPunti = new ProgrammaPunti(nome, descrizione);
            controllerProgrammaFedelta.addProgrammaFedelta(programPunti);
        } else if (number == 2) {
            ProgrammaFedelta programLivelli = new ProgrammaLivelli(nome, descrizione);
            controllerProgrammaFedelta.addProgrammaFedelta(programLivelli);
        } else System.out.println("Tipologia di programma non esistente");

        System.out.println("Il programma é stato inserito");
    }


    private static int provaScannerInt() {
        while (true) {
            try {
                int intero = sc.nextInt();
                sc.nextLine();
                return intero;
            } catch (Exception e) {
                System.out.println("Cio' che hai inserito non e' un valore numerico, ritenta ");
            }
        }
    }
}