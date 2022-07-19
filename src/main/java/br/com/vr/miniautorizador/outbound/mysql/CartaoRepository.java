package br.com.vr.miniautorizador.outbound.mysql;

import br.com.vr.miniautorizador.core.commons.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Optional<Cartao> findByNumeroCartao(String numeroCartao);

}
