package com.awe.caixa.repository;

import com.awe.caixa.domain.TipoEntrada;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoEntrada entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoEntradaRepository extends JpaRepository<TipoEntrada, Long> {

}
