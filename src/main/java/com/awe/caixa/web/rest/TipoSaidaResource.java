package com.awe.caixa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.awe.caixa.domain.TipoSaida;
import com.awe.caixa.repository.TipoSaidaRepository;
import com.awe.caixa.web.rest.errors.BadRequestAlertException;
import com.awe.caixa.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TipoSaida.
 */
@RestController
@RequestMapping("/api")
public class TipoSaidaResource {

    private final Logger log = LoggerFactory.getLogger(TipoSaidaResource.class);

    private static final String ENTITY_NAME = "tipoSaida";

    private final TipoSaidaRepository tipoSaidaRepository;

    public TipoSaidaResource(TipoSaidaRepository tipoSaidaRepository) {
        this.tipoSaidaRepository = tipoSaidaRepository;
    }

    /**
     * POST  /tipo-saidas : Create a new tipoSaida.
     *
     * @param tipoSaida the tipoSaida to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoSaida, or with status 400 (Bad Request) if the tipoSaida has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-saidas")
    @Timed
    public ResponseEntity<TipoSaida> createTipoSaida(@Valid @RequestBody TipoSaida tipoSaida) throws URISyntaxException {
        log.debug("REST request to save TipoSaida : {}", tipoSaida);
        if (tipoSaida.getId() != null) {
            throw new BadRequestAlertException("A new tipoSaida cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoSaida result = tipoSaidaRepository.save(tipoSaida);
        return ResponseEntity.created(new URI("/api/tipo-saidas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-saidas : Updates an existing tipoSaida.
     *
     * @param tipoSaida the tipoSaida to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoSaida,
     * or with status 400 (Bad Request) if the tipoSaida is not valid,
     * or with status 500 (Internal Server Error) if the tipoSaida couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-saidas")
    @Timed
    public ResponseEntity<TipoSaida> updateTipoSaida(@Valid @RequestBody TipoSaida tipoSaida) throws URISyntaxException {
        log.debug("REST request to update TipoSaida : {}", tipoSaida);
        if (tipoSaida.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoSaida result = tipoSaidaRepository.save(tipoSaida);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoSaida.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-saidas : get all the tipoSaidas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tipoSaidas in body
     */
    @GetMapping("/tipo-saidas")
    @Timed
    public List<TipoSaida> getAllTipoSaidas() {
        log.debug("REST request to get all TipoSaidas");
        return tipoSaidaRepository.findAll();
    }

    /**
     * GET  /tipo-saidas/:id : get the "id" tipoSaida.
     *
     * @param id the id of the tipoSaida to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoSaida, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-saidas/{id}")
    @Timed
    public ResponseEntity<TipoSaida> getTipoSaida(@PathVariable Long id) {
        log.debug("REST request to get TipoSaida : {}", id);
        Optional<TipoSaida> tipoSaida = tipoSaidaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tipoSaida);
    }

    /**
     * DELETE  /tipo-saidas/:id : delete the "id" tipoSaida.
     *
     * @param id the id of the tipoSaida to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-saidas/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoSaida(@PathVariable Long id) {
        log.debug("REST request to delete TipoSaida : {}", id);

        tipoSaidaRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
