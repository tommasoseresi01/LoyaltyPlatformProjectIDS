package it.unicam.cs.ids.loyaltyplatformproject.Model;

public class Coupon {

    private int idCoupon;
    private String nomeCoupon;
    private int costoPunti;

    private ProgrammaPunti pp;
    private Cliente c;

    public Coupon(int idCoupon, String nomeCoupon, int costoPunti, ProgrammaPunti pp, Cliente c) {
        this.idCoupon = idCoupon;
        this.nomeCoupon = nomeCoupon;
        this.costoPunti = costoPunti;
        this.pp=pp;
        this.c=c;
    }

    public Coupon(String nomeCoupon, int costoPunti, ProgrammaPunti pp, Cliente c) {
        this.idCoupon=randomInt();
        this.nomeCoupon = nomeCoupon;
        this.costoPunti = costoPunti;
        this.pp = pp;
        this.c = c;
    }

    private int randomInt() {
        double doubleRandom=0;

        doubleRandom=Math.random()*4000;

        int intRandom=(int ) doubleRandom;
        return intRandom;
    }

    public int getIdCoupon() {
        return idCoupon;
    }

    public String getNomeCoupon() {
        return nomeCoupon;
    }

    public int getCostoPunti() {
        return costoPunti;
    }

    public ProgrammaPunti getPp() {
        return pp;
    }

    public Cliente getC() {
        return c;
    }
}
