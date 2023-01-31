package it.unicam.cs.ids.loyaltyplatformproject.Model;


public class ProgrammaLivelli extends ProgrammaFedelta {


    private int livelloMax;                 //livello massimo raggiungibile
    private int puntiTot;                    //punto da totalizzare per passare al livello successivo
    private int valoreXPercentualeLivello;                 //Livello per passare alla fase Vip

    public ProgrammaLivelli(int id, String nome, String descrizione, int livelloMax, int puntiTot, int valoreXPercentualeLivello) {
        super(id, nome, descrizione);
        this.livelloMax = livelloMax;
        this.puntiTot = puntiTot;
        this.valoreXPercentualeLivello = valoreXPercentualeLivello;
    }

    public ProgrammaLivelli(String nome, String descrizione) {
        super(nome, descrizione);
        this.livelloMax = 0;
        this.puntiTot = 0;
        this.valoreXPercentualeLivello = 0;
    }


    public int getLivelloMax() {
        return livelloMax;
    }

    public int getPuntiTot() {
        return puntiTot;
    }

    public int getValoreXPercentualeLivello() {
        return valoreXPercentualeLivello;
    }

    public void setLivelloMax(int livelloMax) {
        this.livelloMax = livelloMax;
    }

    public void setPuntiTot(int puntiTot) {
        this.puntiTot = puntiTot;
    }

    public void setValoreXPercentualeLivello(int valoreXPercentualeLivello) {
        this.valoreXPercentualeLivello = valoreXPercentualeLivello;
    }
}