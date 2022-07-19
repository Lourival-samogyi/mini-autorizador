package br.com.vr.miniautorizador.core.application.services;

import br.com.vr.miniautorizador.core.commons.dto.TransacaoDto;
import br.com.vr.miniautorizador.core.commons.exception.TransacaoRecusadaException;
import br.com.vr.miniautorizador.core.commons.model.Cartao;
import br.com.vr.miniautorizador.outbound.mysql.CartaoRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransacaoServiceTest {

    @Mock
    CartaoRepository cartaoRepository;

    @InjectMocks
    TransacaoService transacaoService;

    @Test
    public void deveRealizarTransacaoSaldoSuperior(){
        TransacaoDto transacaoDto = new TransacaoDto("6549873025634501", "9999", BigDecimal.valueOf(50));
        Cartao cartao = new Cartao("6549873025634501", "9999", BigDecimal.valueOf(500));
        when(cartaoRepository.findByNumeroCartao(anyString())).thenReturn(Optional.of(cartao));
        TransacaoDto transacaoRealizada = transacaoService.realizarTransacao(transacaoDto);
        Assert.assertEquals(transacaoDto, transacaoRealizada);
    }

    @Test
    public void deveRealizarTransacaoSaldoIgual(){
        TransacaoDto transacaoDto = new TransacaoDto("6549873025634501", "9999", BigDecimal.valueOf(50));
        Cartao cartao = new Cartao("6549873025634501", "9999", BigDecimal.valueOf(50));
        when(cartaoRepository.findByNumeroCartao(anyString())).thenReturn(Optional.of(cartao));
        TransacaoDto transacaoRealizada = transacaoService.realizarTransacao(transacaoDto);
        Assert.assertEquals(transacaoDto, transacaoRealizada);
    }

    @Test
    public void naoDeveRealizarTransacaoPorCartaoInexistente(){
        try{
            TransacaoDto transacaoDto = new TransacaoDto("6549873025634501", "9999", BigDecimal.valueOf(50));
            when(cartaoRepository.findByNumeroCartao(anyString())).thenReturn(Optional.empty());
            TransacaoDto transacaoRealizada = transacaoService.realizarTransacao(transacaoDto);
        }catch (TransacaoRecusadaException e){
            Assert.assertEquals("CARTAO_INEXISTENTE", e.getMessage());
        }
    }

    @Test
    public void naoDeveRealizarTransacaoPorSenhaInvalida(){
        try{
            TransacaoDto transacaoDto = new TransacaoDto("6549873025634501", "1234", BigDecimal.valueOf(50));
            Cartao cartao = new Cartao("6549873025634501", "9999", BigDecimal.valueOf(500));
            when(cartaoRepository.findByNumeroCartao(anyString())).thenReturn(Optional.of(cartao));
            TransacaoDto transacaoRealizada = transacaoService.realizarTransacao(transacaoDto);
        }catch (TransacaoRecusadaException e){
            Assert.assertEquals("SENHA_INVALIDA", e.getMessage());
        }
    }

    @Test
    public void naoDeveRealizarTransacaoPorSaldoInsuficiente(){
        try{
            TransacaoDto transacaoDto = new TransacaoDto("6549873025634501", "9999", BigDecimal.valueOf(50));
            Cartao cartao = new Cartao("6549873025634501", "9999", BigDecimal.valueOf(49));
            when(cartaoRepository.findByNumeroCartao(anyString())).thenReturn(Optional.of(cartao));
            TransacaoDto transacaoRealizada = transacaoService.realizarTransacao(transacaoDto);
        }catch (TransacaoRecusadaException e){
            Assert.assertEquals("SALDO_INSUFICIENTE", e.getMessage());
        }
    }
}
