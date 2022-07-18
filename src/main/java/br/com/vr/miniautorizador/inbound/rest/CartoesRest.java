package br.com.vr.miniautorizador.inbound.rest;

import br.com.vr.miniautorizador.core.application.services.CartaoService;
import br.com.vr.miniautorizador.core.commons.dto.CartaoDto;
import br.com.vr.miniautorizador.core.commons.exception.CartaoJaExistenteException;
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
            return ResponseEntity.ok(cartaoDto);
        } catch (CartaoJaExistenteException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(cartaoDto);
        }
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity getSaldo(@PathVariable Long numeroCartao){
        return ResponseEntity.ok(new BigDecimal(500));
    }
}
