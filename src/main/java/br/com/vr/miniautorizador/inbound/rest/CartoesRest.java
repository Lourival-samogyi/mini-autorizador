package br.com.vr.miniautorizador.inbound.rest;

import br.com.vr.miniautorizador.core.application.services.CartaoService;
import br.com.vr.miniautorizador.core.commons.dto.CartaoDto;
import br.com.vr.miniautorizador.core.commons.exception.CartaoJaExistenteException;
import br.com.vr.miniautorizador.core.commons.exception.CartaoNaoEncontratoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("cartoes")
public class CartoesRest {

    @Autowired
    private CartaoService cartaoService;

    @PostMapping()
    public ResponseEntity create(@RequestBody CartaoDto cartaoDto){
        try{
            cartaoService.criarNovoCartao(cartaoDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(cartaoDto);
        } catch (CartaoJaExistenteException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(cartaoDto);
        }
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity getSaldo(@PathVariable String numeroCartao){
        try{
            BigDecimal saldoCartao = cartaoService.getSaldo(numeroCartao);
            return ResponseEntity.ok(saldoCartao);
        } catch (CartaoNaoEncontratoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
