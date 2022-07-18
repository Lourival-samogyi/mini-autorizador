package br.com.vr.miniautorizador.inbound.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transacoes")
public class TransacoesRest {

    @PostMapping()
    public ResponseEntity realizarTransacao(){
        return ResponseEntity.ok().build();
    }
}
