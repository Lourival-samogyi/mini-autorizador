package br.com.vr.miniautorizador.core.application.services;

import br.com.vr.miniautorizador.core.commons.dto.TransacaoDto;
import br.com.vr.miniautorizador.core.commons.exception.TransacaoRecusadaException;
import br.com.vr.miniautorizador.core.commons.model.Cartao;
import br.com.vr.miniautorizador.outbound.mysql.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TransacaoService {

    @Autowired
    CartaoRepository cartaoRepository;

    @Transactional
    public TransacaoDto realizarTransacao(TransacaoDto transacaoDto){
        Optional<Cartao> cartaoOptional = cartaoRepository.findByNumeroCartao(transacaoDto.getNumeroCartao());
        Cartao cartao = this.isTransacaoValida(transacaoDto, cartaoOptional);
        cartao.setSaldo(cartao.getSaldo().subtract(transacaoDto.getValor()));
        cartaoRepository.save(cartao);
        return transacaoDto;
    }

    private Cartao isTransacaoValida(TransacaoDto transacaoDto, Optional<Cartao> cartaoOptional){
        cartaoOptional.ifPresentOrElse(cartao -> {
            if(!cartao.getSenha().equals(transacaoDto.getSenhaCartao())){
                throw new TransacaoRecusadaException("SENHA_INVALIDA");
            }else if(cartao.getSaldo().compareTo(transacaoDto.getValor()) < 0){
                throw new TransacaoRecusadaException("SALDO_INSUFICIENTE");
            }
        }, () -> {
            throw new TransacaoRecusadaException("CARTAO_INEXISTENTE");
        });
        return cartaoOptional.get();
    }
}
