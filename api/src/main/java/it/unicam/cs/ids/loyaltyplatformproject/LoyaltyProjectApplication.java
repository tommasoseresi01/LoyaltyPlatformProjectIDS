package it.unicam.cs.ids.loyaltyplatformproject;

import it.unicam.cs.ids.loyaltyplatformproject.Controller.*;
import it.unicam.cs.ids.loyaltyplatformproject.Model.*;
import it.unicam.cs.ids.loyaltyplatformproject.Services.DBMSController;

import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;


public class LoyaltyProjectApplication {

    private static final Scanner sc = new Scanner(System.in);
    private static final ControllerProgrammaFedelta controllerProgrammaFedelta= new ControllerProgrammaFedelta();
    private static final ControllerRegistrazione controllerRegistrazioni= new ControllerRegistrazione();
    private static final ControllerCarta controllerCarta= new ControllerCarta();
    private static final ControllerPuntoVendita controllerPuntoVendita= new ControllerPuntoVendita();
    private static final ControllerPagamento controllerPagamento= new ControllerPagamento();
    private static final ControllerCoupon controllerCoupon= new ControllerCoupon();
    public static void main(String[] args) throws SQLException, DateMistake, AbilitationException {
        DBMSController.init();
        boolean flag=false;
        do {
            System.out.println("Welcome to Loyalty Platform!!/n");
            System.out.println("Seleziona il numero per scegliere l'azione da eseguire: /n");
            System.out.println("1-Effettua il login");
            System.out.println("2-Effettua la registrazione");
            System.out.println("3-per uscire dalla piattaforma");
            switch (provaScannerInt()) {
                case 1 -> login();
                case 2 -> registrazione();
                case 3->flag=true;
            }
        }while(!flag);

        System.out.println("Loyalty platform ti ringrazia e ti augura un buon proseguimento:)");
    }
    private static void registrazione() throws SQLException, DateMistake, AbilitationException {
        boolean flag=false;
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
                            ", ora il tuo id é: "+c.getId()+" ");
                    flag=true;
                }
                case 2 ->{
                    System.out.println("Inserire il nome del punto vendita che gestisci");
                    String nomePuntoVendita= sc.nextLine();
                    System.out.println("Inserire indirizzo del punto vendita:");
                    String indirizzoPuntoVendita=sc.nextLine();
                    TitolarePuntoVendita t=new TitolarePuntoVendita(nome,cognome,indirizzo,email,username,password,telefono);
                    controllerRegistrazioni.registrazioneTitolare(t);
                    PuntoVendita pv= new PuntoVendita(nomePuntoVendita,indirizzoPuntoVendita, t);
                    controllerPuntoVendita.addPuntoVendita(pv);
                    System.out.println("Magnifico, la registrazione alla Loyalty platform é avvenuta con successo" +
                            ", ora il tuo id é: "+t.getId()+" ");
                    flag=true;}

                case 3 ->{
                    System.out.println("Inserisci il nome del punto vendita");
                    String nomePv= sc.nextLine();
                    controllerRegistrazioni.getAllEsercenti();
                    controllerPuntoVendita.visualizzaPuntoVendita();
                    PuntoVendita pv=controllerPuntoVendita.findById(nomePv);
                    CommessoPuntoVendita cpv= new CommessoPuntoVendita(nome, cognome, indirizzo, email, username, password, telefono, pv);
                    controllerRegistrazioni.registrazioneCommessoPuntoVendita(cpv);
                    System.out.println("Magnifico, la registrazione alla Loyalty platform é avvenuta con successo" +
                            ", ora il tuo id é: "+cpv.getId()+" ");
                    flag=true;}
            }
        }while(!flag);
    }


    private static void login() throws SQLException, DateMistake, AbilitationException {
        boolean flag=false;
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
                case 5 -> flag=true;
            }
        }while (!flag);
    }

    private static void homeGestore() throws SQLException {
        boolean flag=false;
        do {
            System.out.println("Seleziona il numero per scegliere l'azione da eseguire: ");
            System.out.println("1- Aggiungere un programma fedelta");
            System.out.println("2- Elimina programma fedelta");
            System.out.println("3- Ritorna al menu scelta dei ruoli");
            switch (provaScannerInt()) {
                case 1 -> aggiungi();
                case 2 -> elimina();
                case 3 -> {flag = true;}
            }
        }while(!flag);
    }
