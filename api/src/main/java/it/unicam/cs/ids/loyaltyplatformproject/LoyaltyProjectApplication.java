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