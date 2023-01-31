package it.unicam.cs.ids.loyaltyplatformproject.Model;

import it.unicam.cs.ids.loyaltyplatformproject.Controller.ControllerPagamento;

import java.sql.SQLException;

public class SistemaBanca {

    private StatoPagamento pagamento;

    private final ControllerPagamento  statoPagamento;

    public SistemaBanca() {
        statoPagamento= new ControllerPagamento();
    }

    public StatoPagamento getPagamento() {
        return pagamento;
    }

    public StatoPagamento verificaPagamento(TitolarePuntoVendita t) throws SQLException {
        if (statoPagamento.payment(t)){
            pagamento=StatoPagamento.PAGATO;
        }else pagamento=StatoPagamento.In_Attesa;

        return pagamento;
    }
}
