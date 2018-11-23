package com.awe.caixa.web.rest;

import com.awe.caixa.CaixaApp;

import com.awe.caixa.domain.TipoEntrada;
import com.awe.caixa.repository.TipoEntradaRepository;
import com.awe.caixa.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.awe.caixa.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TipoEntradaResource REST controller.
 *
 * @see TipoEntradaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaixaApp.class)
public class TipoEntradaResourceIntTest {

    private static final Integer DEFAULT_COD_T_ENTRADA = 1;
    private static final Integer UPDATED_COD_T_ENTRADA = 2;

    private static final String DEFAULT_DESC_T_ENTRADA = "AAAAAAAAAA";
    private static final String UPDATED_DESC_T_ENTRADA = "BBBBBBBBBB";

    @Autowired
    private TipoEntradaRepository tipoEntradaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTipoEntradaMockMvc;

    private TipoEntrada tipoEntrada;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoEntradaResource tipoEntradaResource = new TipoEntradaResource(tipoEntradaRepository);
        this.restTipoEntradaMockMvc = MockMvcBuilders.standaloneSetup(tipoEntradaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoEntrada createEntity(EntityManager em) {
        TipoEntrada tipoEntrada = new TipoEntrada()
            .codTEntrada(DEFAULT_COD_T_ENTRADA)
            .descTEntrada(DEFAULT_DESC_T_ENTRADA);
        return tipoEntrada;
    }

    @Before
    public void initTest() {
        tipoEntrada = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoEntrada() throws Exception {
        int databaseSizeBeforeCreate = tipoEntradaRepository.findAll().size();

        // Create the TipoEntrada
        restTipoEntradaMockMvc.perform(post("/api/tipo-entradas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoEntrada)))
            .andExpect(status().isCreated());

        // Validate the TipoEntrada in the database
        List<TipoEntrada> tipoEntradaList = tipoEntradaRepository.findAll();
        assertThat(tipoEntradaList).hasSize(databaseSizeBeforeCreate + 1);
        TipoEntrada testTipoEntrada = tipoEntradaList.get(tipoEntradaList.size() - 1);
        assertThat(testTipoEntrada.getCodTEntrada()).isEqualTo(DEFAULT_COD_T_ENTRADA);
        assertThat(testTipoEntrada.getDescTEntrada()).isEqualTo(DEFAULT_DESC_T_ENTRADA);
    }

    @Test
    @Transactional
    public void createTipoEntradaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoEntradaRepository.findAll().size();

        // Create the TipoEntrada with an existing ID
        tipoEntrada.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoEntradaMockMvc.perform(post("/api/tipo-entradas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoEntrada)))
            .andExpect(status().isBadRequest());

        // Validate the TipoEntrada in the database
        List<TipoEntrada> tipoEntradaList = tipoEntradaRepository.findAll();
        assertThat(tipoEntradaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescTEntradaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoEntradaRepository.findAll().size();
        // set the field null
        tipoEntrada.setDescTEntrada(null);

        // Create the TipoEntrada, which fails.

        restTipoEntradaMockMvc.perform(post("/api/tipo-entradas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoEntrada)))
            .andExpect(status().isBadRequest());

        List<TipoEntrada> tipoEntradaList = tipoEntradaRepository.findAll();
        assertThat(tipoEntradaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoEntradas() throws Exception {
        // Initialize the database
        tipoEntradaRepository.saveAndFlush(tipoEntrada);

        // Get all the tipoEntradaList
        restTipoEntradaMockMvc.perform(get("/api/tipo-entradas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoEntrada.getId().intValue())))
            .andExpect(jsonPath("$.[*].codTEntrada").value(hasItem(DEFAULT_COD_T_ENTRADA)))
            .andExpect(jsonPath("$.[*].descTEntrada").value(hasItem(DEFAULT_DESC_T_ENTRADA.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoEntrada() throws Exception {
        // Initialize the database
        tipoEntradaRepository.saveAndFlush(tipoEntrada);

        // Get the tipoEntrada
        restTipoEntradaMockMvc.perform(get("/api/tipo-entradas/{id}", tipoEntrada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoEntrada.getId().intValue()))
            .andExpect(jsonPath("$.codTEntrada").value(DEFAULT_COD_T_ENTRADA))
            .andExpect(jsonPath("$.descTEntrada").value(DEFAULT_DESC_T_ENTRADA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoEntrada() throws Exception {
        // Get the tipoEntrada
        restTipoEntradaMockMvc.perform(get("/api/tipo-entradas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoEntrada() throws Exception {
        // Initialize the database
        tipoEntradaRepository.saveAndFlush(tipoEntrada);

        int databaseSizeBeforeUpdate = tipoEntradaRepository.findAll().size();

        // Update the tipoEntrada
        TipoEntrada updatedTipoEntrada = tipoEntradaRepository.findById(tipoEntrada.getId()).get();
        // Disconnect from session so that the updates on updatedTipoEntrada are not directly saved in db
        em.detach(updatedTipoEntrada);
        updatedTipoEntrada
            .codTEntrada(UPDATED_COD_T_ENTRADA)
            .descTEntrada(UPDATED_DESC_T_ENTRADA);

        restTipoEntradaMockMvc.perform(put("/api/tipo-entradas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoEntrada)))
            .andExpect(status().isOk());

        // Validate the TipoEntrada in the database
        List<TipoEntrada> tipoEntradaList = tipoEntradaRepository.findAll();
        assertThat(tipoEntradaList).hasSize(databaseSizeBeforeUpdate);
        TipoEntrada testTipoEntrada = tipoEntradaList.get(tipoEntradaList.size() - 1);
        assertThat(testTipoEntrada.getCodTEntrada()).isEqualTo(UPDATED_COD_T_ENTRADA);
        assertThat(testTipoEntrada.getDescTEntrada()).isEqualTo(UPDATED_DESC_T_ENTRADA);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoEntrada() throws Exception {
        int databaseSizeBeforeUpdate = tipoEntradaRepository.findAll().size();

        // Create the TipoEntrada

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoEntradaMockMvc.perform(put("/api/tipo-entradas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoEntrada)))
            .andExpect(status().isBadRequest());

        // Validate the TipoEntrada in the database
        List<TipoEntrada> tipoEntradaList = tipoEntradaRepository.findAll();
        assertThat(tipoEntradaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoEntrada() throws Exception {
        // Initialize the database
        tipoEntradaRepository.saveAndFlush(tipoEntrada);

        int databaseSizeBeforeDelete = tipoEntradaRepository.findAll().size();

        // Get the tipoEntrada
        restTipoEntradaMockMvc.perform(delete("/api/tipo-entradas/{id}", tipoEntrada.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoEntrada> tipoEntradaList = tipoEntradaRepository.findAll();
        assertThat(tipoEntradaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoEntrada.class);
        TipoEntrada tipoEntrada1 = new TipoEntrada();
        tipoEntrada1.setId(1L);
        TipoEntrada tipoEntrada2 = new TipoEntrada();
        tipoEntrada2.setId(tipoEntrada1.getId());
        assertThat(tipoEntrada1).isEqualTo(tipoEntrada2);
        tipoEntrada2.setId(2L);
        assertThat(tipoEntrada1).isNotEqualTo(tipoEntrada2);
        tipoEntrada1.setId(null);
        assertThat(tipoEntrada1).isNotEqualTo(tipoEntrada2);
    }
}
