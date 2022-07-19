package br.com.vr.miniautorizador.core.application.services;

import br.com.vr.miniautorizador.core.commons.dto.CartaoDto;
import br.com.vr.miniautorizador.core.commons.exception.CartaoJaExistenteException;
import br.com.vr.miniautorizador.core.commons.exception.CartaoNaoEncontratoException;
import br.com.vr.miniautorizador.core.commons.model.Cartao;
import br.com.vr.miniautorizador.outbound.mysql.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CartaoService {

    @Autowired
    CartaoRepository cartaoRepository;

    @Transactional
    public CartaoDto criarNovoCartao(CartaoDto cartaoDto) {
        cartaoRepository.findByNumeroCartao(cartaoDto.getNumeroCartao()).ifPresent(cartao -> {
            throw new CartaoJaExistenteException("Cartao já existente na base de dados");
        });

        Cartao novoCartao = new Cartao(cartaoDto.getNumeroCartao(), cartaoDto.getSenha(), BigDecimal.valueOf(500L));
        cartaoRepository.save(novoCartao);
        return  cartaoDto;
    }

    @Transactional
    public BigDecimal getSaldo(String numeroCartao){
        AtomicReference<BigDecimal> retorno = new AtomicReference<>(BigDecimal.ZERO);

        cartaoRepository.findByNumeroCartao(numeroCartao).ifPresentOrElse(cartao -> {
            retorno.set(cartao.getSaldo());
        }, () -> {
            throw new CartaoNaoEncontratoException("Cartao nao encontrado na base dados");
        });

        return retorno.get();
    }
}
