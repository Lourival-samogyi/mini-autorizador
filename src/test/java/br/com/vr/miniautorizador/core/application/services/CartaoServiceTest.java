package br.com.vr.miniautorizador.core.application.services;

import br.com.vr.miniautorizador.core.commons.dto.CartaoDto;
import br.com.vr.miniautorizador.core.commons.exception.CartaoJaExistenteException;
import br.com.vr.miniautorizador.core.commons.exception.CartaoNaoEncontratoException;
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
public class CartaoServiceTest {

    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private CartaoService cartaoService;

    @Test
    public void deveCriarNovoCartao() throws Exception {
        CartaoDto cartaoDto = new CartaoDto("99999999999999999", "9999");
        when(cartaoRepository.findByNumeroCartao(anyString())).thenReturn(Optional.empty());
        Assert.assertEquals(cartaoService.criarNovoCartao(cartaoDto), cartaoDto);
    }

    @Test(expected = CartaoJaExistenteException.class)
    public void naoDeveCriarNovoCartao() throws Exception {
        CartaoDto cartaoDto = new CartaoDto("99999999999999999", "9999");
        when(cartaoRepository.findByNumeroCartao(anyString())).thenReturn(Optional.of(new Cartao()));
        cartaoService.criarNovoCartao(cartaoDto);
    }

    @Test
    public void deveRetornarSaldoCartao(){
        String numeroCartao = "99999999999999999";
        Cartao cartao = new Cartao(numeroCartao, "1234", BigDecimal.valueOf(498));
        when(cartaoRepository.findByNumeroCartao(anyString())).thenReturn(Optional.of(cartao));
        BigDecimal saldo = cartaoService.getSaldo(numeroCartao);
        Assert.assertEquals(saldo, BigDecimal.valueOf(498));
    }

    @Test(expected = CartaoNaoEncontratoException.class)
    public void naoDeveRetornarSaldoCartao(){
        String numeroCartao = "99999999999999999";
        when(cartaoRepository.findByNumeroCartao(anyString())).thenReturn(Optional.empty());
        BigDecimal saldo = cartaoService.getSaldo(numeroCartao);
    }
}
