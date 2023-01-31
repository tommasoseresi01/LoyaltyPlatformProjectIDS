package it.unicam.cs.ids.loyaltyplatformproject.Model;

public class GestorePiattaforma extends UtenteGenerico {


    private static int costoAdesionePiattaforma = 500;

    public GestorePiattaforma(String nome, String cognome, String indirizzo, String email, String username, String password, int telefono) {
        super(nome, cognome, indirizzo, email, username, password, telefono);

    }

    public static int getCostoAdesionePiattaforma() {
        return costoAdesionePiattaforma;
    }


}
