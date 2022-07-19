package br.com.vr.miniautorizador.inbound.rest;

import br.com.vr.miniautorizador.core.application.services.TransacaoService;
import br.com.vr.miniautorizador.core.commons.dto.TransacaoDto;
import br.com.vr.miniautorizador.core.commons.exception.TransacaoRecusadaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transacoes")
public class TransacoesRest {

    @Autowired
    private TransacaoService transacaoService;

    @PostMapping()
    public ResponseEntity realizarTransacao(@RequestBody TransacaoDto transacaoDto){
        try{
            transacaoService.realizarTransacao(transacaoDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("OK");
        }catch (TransacaoRecusadaException e){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }
}
