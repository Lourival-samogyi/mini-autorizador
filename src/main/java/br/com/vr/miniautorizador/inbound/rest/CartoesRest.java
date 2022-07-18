package br.com.vr.miniautorizador.inbound.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("cartoes")
public class CartoesRest {

    @PostMapping()
    public ResponseEntity create(){
        return ResponseEntity.ok(true);
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity getSaldo(@PathVariable Long numeroCartao){
        return ResponseEntity.ok(new BigDecimal(500));
    }
}
