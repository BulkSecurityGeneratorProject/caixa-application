package com.awe.caixa.web.rest;

import com.awe.caixa.CaixaApp;

import com.awe.caixa.domain.TipoSaida;
import com.awe.caixa.repository.TipoSaidaRepository;
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
 * Test class for the TipoSaidaResource REST controller.
 *
 * @see TipoSaidaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaixaApp.class)
public class TipoSaidaResourceIntTest {

    private static final Integer DEFAULT_COD_T_SAIDA = 1;
    private static final Integer UPDATED_COD_T_SAIDA = 2;

    private static final String DEFAULT_DESC_T_SAIDA = "AAAAAAAAAA";
    private static final String UPDATED_DESC_T_SAIDA = "BBBBBBBBBB";

    @Autowired
    private TipoSaidaRepository tipoSaidaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTipoSaidaMockMvc;

    private TipoSaida tipoSaida;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoSaidaResource tipoSaidaResource = new TipoSaidaResource(tipoSaidaRepository);
        this.restTipoSaidaMockMvc = MockMvcBuilders.standaloneSetup(tipoSaidaResource)
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
    public static TipoSaida createEntity(EntityManager em) {
        TipoSaida tipoSaida = new TipoSaida()
            .codTSaida(DEFAULT_COD_T_SAIDA)
            .descTSaida(DEFAULT_DESC_T_SAIDA);
        return tipoSaida;
    }

    @Before
    public void initTest() {
        tipoSaida = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoSaida() throws Exception {
        int databaseSizeBeforeCreate = tipoSaidaRepository.findAll().size();

        // Create the TipoSaida
        restTipoSaidaMockMvc.perform(post("/api/tipo-saidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoSaida)))
            .andExpect(status().isCreated());

        // Validate the TipoSaida in the database
        List<TipoSaida> tipoSaidaList = tipoSaidaRepository.findAll();
        assertThat(tipoSaidaList).hasSize(databaseSizeBeforeCreate + 1);
        TipoSaida testTipoSaida = tipoSaidaList.get(tipoSaidaList.size() - 1);
        assertThat(testTipoSaida.getCodTSaida()).isEqualTo(DEFAULT_COD_T_SAIDA);
        assertThat(testTipoSaida.getDescTSaida()).isEqualTo(DEFAULT_DESC_T_SAIDA);
    }

    @Test
    @Transactional
    public void createTipoSaidaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoSaidaRepository.findAll().size();

        // Create the TipoSaida with an existing ID
        tipoSaida.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoSaidaMockMvc.perform(post("/api/tipo-saidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoSaida)))
            .andExpect(status().isBadRequest());

        // Validate the TipoSaida in the database
        List<TipoSaida> tipoSaidaList = tipoSaidaRepository.findAll();
        assertThat(tipoSaidaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescTSaidaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoSaidaRepository.findAll().size();
        // set the field null
        tipoSaida.setDescTSaida(null);

        // Create the TipoSaida, which fails.

        restTipoSaidaMockMvc.perform(post("/api/tipo-saidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoSaida)))
            .andExpect(status().isBadRequest());

        List<TipoSaida> tipoSaidaList = tipoSaidaRepository.findAll();
        assertThat(tipoSaidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoSaidas() throws Exception {
        // Initialize the database
        tipoSaidaRepository.saveAndFlush(tipoSaida);

        // Get all the tipoSaidaList
        restTipoSaidaMockMvc.perform(get("/api/tipo-saidas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoSaida.getId().intValue())))
            .andExpect(jsonPath("$.[*].codTSaida").value(hasItem(DEFAULT_COD_T_SAIDA)))
            .andExpect(jsonPath("$.[*].descTSaida").value(hasItem(DEFAULT_DESC_T_SAIDA.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoSaida() throws Exception {
        // Initialize the database
        tipoSaidaRepository.saveAndFlush(tipoSaida);

        // Get the tipoSaida
        restTipoSaidaMockMvc.perform(get("/api/tipo-saidas/{id}", tipoSaida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoSaida.getId().intValue()))
            .andExpect(jsonPath("$.codTSaida").value(DEFAULT_COD_T_SAIDA))
            .andExpect(jsonPath("$.descTSaida").value(DEFAULT_DESC_T_SAIDA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoSaida() throws Exception {
        // Get the tipoSaida
        restTipoSaidaMockMvc.perform(get("/api/tipo-saidas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoSaida() throws Exception {
        // Initialize the database
        tipoSaidaRepository.saveAndFlush(tipoSaida);

        int databaseSizeBeforeUpdate = tipoSaidaRepository.findAll().size();

        // Update the tipoSaida
        TipoSaida updatedTipoSaida = tipoSaidaRepository.findById(tipoSaida.getId()).get();
        // Disconnect from session so that the updates on updatedTipoSaida are not directly saved in db
        em.detach(updatedTipoSaida);
        updatedTipoSaida
            .codTSaida(UPDATED_COD_T_SAIDA)
            .descTSaida(UPDATED_DESC_T_SAIDA);

        restTipoSaidaMockMvc.perform(put("/api/tipo-saidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoSaida)))
            .andExpect(status().isOk());

        // Validate the TipoSaida in the database
        List<TipoSaida> tipoSaidaList = tipoSaidaRepository.findAll();
        assertThat(tipoSaidaList).hasSize(databaseSizeBeforeUpdate);
        TipoSaida testTipoSaida = tipoSaidaList.get(tipoSaidaList.size() - 1);
        assertThat(testTipoSaida.getCodTSaida()).isEqualTo(UPDATED_COD_T_SAIDA);
        assertThat(testTipoSaida.getDescTSaida()).isEqualTo(UPDATED_DESC_T_SAIDA);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoSaida() throws Exception {
        int databaseSizeBeforeUpdate = tipoSaidaRepository.findAll().size();

        // Create the TipoSaida

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoSaidaMockMvc.perform(put("/api/tipo-saidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoSaida)))
            .andExpect(status().isBadRequest());

        // Validate the TipoSaida in the database
        List<TipoSaida> tipoSaidaList = tipoSaidaRepository.findAll();
        assertThat(tipoSaidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoSaida() throws Exception {
        // Initialize the database
        tipoSaidaRepository.saveAndFlush(tipoSaida);

        int databaseSizeBeforeDelete = tipoSaidaRepository.findAll().size();

        // Get the tipoSaida
        restTipoSaidaMockMvc.perform(delete("/api/tipo-saidas/{id}", tipoSaida.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoSaida> tipoSaidaList = tipoSaidaRepository.findAll();
        assertThat(tipoSaidaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoSaida.class);
        TipoSaida tipoSaida1 = new TipoSaida();
        tipoSaida1.setId(1L);
        TipoSaida tipoSaida2 = new TipoSaida();
        tipoSaida2.setId(tipoSaida1.getId());
        assertThat(tipoSaida1).isEqualTo(tipoSaida2);
        tipoSaida2.setId(2L);
        assertThat(tipoSaida1).isNotEqualTo(tipoSaida2);
        tipoSaida1.setId(null);
        assertThat(tipoSaida1).isNotEqualTo(tipoSaida2);
    }
}
