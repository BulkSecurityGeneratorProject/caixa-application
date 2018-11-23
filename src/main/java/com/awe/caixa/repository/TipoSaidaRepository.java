package com.awe.caixa.repository;

import com.awe.caixa.domain.TipoSaida;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoSaida entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoSaidaRepository extends JpaRepository<TipoSaida, Long> {

}
