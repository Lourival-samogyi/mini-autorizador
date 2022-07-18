package br.com.vr.miniautorizador.core.application.services;

import br.com.vr.miniautorizador.core.commons.dto.CartaoDto;
import br.com.vr.miniautorizador.core.commons.exception.CartaoJaExistenteException;
import br.com.vr.miniautorizador.core.commons.model.Cartao;
import br.com.vr.miniautorizador.outbound.mysql.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartaoService {

    @Autowired
    CartaoRepository cartaoRepository;

    public CartaoDto criarNovoCartao(CartaoDto cartaoDto) {
        cartaoRepository.findByNumeroCartao(cartaoDto.getNumeroCartao()).ifPresent(cartao -> {
            throw new CartaoJaExistenteException("Cartao já existente na base de dados");
        });

        Cartao novoCartao = new Cartao(cartaoDto.getNumeroCartao(), cartaoDto.getSenha(), BigDecimal.valueOf(500L));
        cartaoRepository.save(novoCartao);
        return  cartaoDto;
    }
}
