package br.com.vr.miniautorizador.core.commons.dto;

import java.math.BigDecimal;

public class TransacaoDto {

    private String numeroCartao;
    private String senhaCartao;
    private BigDecimal valor;

    public TransacaoDto() {
    }

    public TransacaoDto(String numeroCartao, String senhaCartao, BigDecimal valor) {
        this.numeroCartao = numeroCartao;
        this.senhaCartao = senhaCartao;
        this.valor = valor;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public String getSenhaCartao() {
        return senhaCartao;
    }

    public BigDecimal getValor() {
        return valor;
    }
}
