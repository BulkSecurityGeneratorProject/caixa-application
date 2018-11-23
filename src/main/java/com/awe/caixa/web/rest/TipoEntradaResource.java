package com.awe.caixa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.awe.caixa.domain.TipoEntrada;
import com.awe.caixa.repository.TipoEntradaRepository;
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
 * REST controller for managing TipoEntrada.
 */
@RestController
@RequestMapping("/api")
public class TipoEntradaResource {

    private final Logger log = LoggerFactory.getLogger(TipoEntradaResource.class);

    private static final String ENTITY_NAME = "tipoEntrada";

    private final TipoEntradaRepository tipoEntradaRepository;

    public TipoEntradaResource(TipoEntradaRepository tipoEntradaRepository) {
        this.tipoEntradaRepository = tipoEntradaRepository;
    }

    /**
     * POST  /tipo-entradas : Create a new tipoEntrada.
     *
     * @param tipoEntrada the tipoEntrada to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoEntrada, or with status 400 (Bad Request) if the tipoEntrada has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-entradas")
    @Timed
    public ResponseEntity<TipoEntrada> createTipoEntrada(@Valid @RequestBody TipoEntrada tipoEntrada) throws URISyntaxException {
        log.debug("REST request to save TipoEntrada : {}", tipoEntrada);
        if (tipoEntrada.getId() != null) {
            throw new BadRequestAlertException("A new tipoEntrada cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoEntrada result = tipoEntradaRepository.save(tipoEntrada);
        return ResponseEntity.created(new URI("/api/tipo-entradas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-entradas : Updates an existing tipoEntrada.
     *
     * @param tipoEntrada the tipoEntrada to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoEntrada,
     * or with status 400 (Bad Request) if the tipoEntrada is not valid,
     * or with status 500 (Internal Server Error) if the tipoEntrada couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-entradas")
    @Timed
    public ResponseEntity<TipoEntrada> updateTipoEntrada(@Valid @RequestBody TipoEntrada tipoEntrada) throws URISyntaxException {
        log.debug("REST request to update TipoEntrada : {}", tipoEntrada);
        if (tipoEntrada.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoEntrada result = tipoEntradaRepository.save(tipoEntrada);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoEntrada.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-entradas : get all the tipoEntradas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tipoEntradas in body
     */
    @GetMapping("/tipo-entradas")
    @Timed
    public List<TipoEntrada> getAllTipoEntradas() {
        log.debug("REST request to get all TipoEntradas");
        return tipoEntradaRepository.findAll();
    }

    /**
     * GET  /tipo-entradas/:id : get the "id" tipoEntrada.
     *
     * @param id the id of the tipoEntrada to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoEntrada, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-entradas/{id}")
    @Timed
    public ResponseEntity<TipoEntrada> getTipoEntrada(@PathVariable Long id) {
        log.debug("REST request to get TipoEntrada : {}", id);
        Optional<TipoEntrada> tipoEntrada = tipoEntradaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tipoEntrada);
    }

    /**
     * DELETE  /tipo-entradas/:id : delete the "id" tipoEntrada.
     *
     * @param id the id of the tipoEntrada to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-entradas/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoEntrada(@PathVariable Long id) {
        log.debug("REST request to delete TipoEntrada : {}", id);

        tipoEntradaRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
