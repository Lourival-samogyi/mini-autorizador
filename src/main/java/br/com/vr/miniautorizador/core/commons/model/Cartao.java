package br.com.vr.miniautorizador.core.commons.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cartao")
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @Column
    private Long id;

    @Column(unique = true)
    private String numeroCartao;

    @Column
    private String senha;

    @Column
    private BigDecimal saldo;

    public Cartao() {
    }

    public Cartao(String numeroCartao, String senha, BigDecimal saldo) {
        this.numeroCartao = numeroCartao;
        this.senha = senha;
        this.saldo = saldo;
    }
}
