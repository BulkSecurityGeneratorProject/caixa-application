package com.awe.caixa.repository;

import com.awe.caixa.domain.Saida;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Saida entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SaidaRepository extends JpaRepository<Saida, Long> {

}
