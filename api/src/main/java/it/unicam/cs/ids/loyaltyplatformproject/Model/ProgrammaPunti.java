package it.unicam.cs.ids.loyaltyplatformproject.Model;



public class ProgrammaPunti extends ProgrammaFedelta {

    private int valoreXPunto;    //Valore in euro per singolo punto
    private int totPunti;    //Punti da totalizzare per ottenere coupon

    public ProgrammaPunti(int id, String nome, String descrizione,  int valoreXPunto, int totPunti) {
        super(id, nome, descrizione);
        this.valoreXPunto= valoreXPunto;
        this.totPunti= totPunti;
    }

    public ProgrammaPunti(String nome, String descrizione) {
        super(nome, descrizione);
        this.valoreXPunto=0;
        this.totPunti=0;
    }

    public ProgrammaPunti(String nome, int id) {
        super(nome, id);
        this.valoreXPunto=0;
        this.totPunti=0;
    }


    public int getValoreXPunto() {
        return valoreXPunto;
    }

    public int getTotPunti() {
        return totPunti;
    }



    public void setValoreXPunto(int valoreXPunto) {
        this.valoreXPunto = valoreXPunto;
    }

    public void setTotPunti(int totPunti) {
        this.totPunti = totPunti;
    }

}