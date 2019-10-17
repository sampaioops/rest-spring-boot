package com.sampaio.cursoestudo.modelo;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.sampaio.cursoestudo.enums.PagamentoStatus;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@JsonTypeName("pagamentoCartao")
public class PagamentoCartao  extends Pagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer numeroParcelas;

    public PagamentoCartao() {
    }

    public PagamentoCartao(PagamentoStatus pagamentoStatus, Pedido pedido, Integer numeroParcelas) {
        super(pagamentoStatus, pedido);
        this.numeroParcelas = numeroParcelas;
    }

    public Integer getNumeroParcelas() {
        return numeroParcelas;
    }

    public void setNumeroParcelas(Integer numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }


}
