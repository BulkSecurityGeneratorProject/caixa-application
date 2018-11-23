package com.awe.caixa.web.rest;

import com.awe.caixa.CaixaApp;

import com.awe.caixa.domain.Entrada;
import com.awe.caixa.repository.EntradaRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.awe.caixa.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EntradaResource REST controller.
 *
 * @see EntradaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaixaApp.class)
public class EntradaResourceIntTest {

    private static final Integer DEFAULT_COD_ENTRADA = 1;
    private static final Integer UPDATED_COD_ENTRADA = 2;

    private static final Double DEFAULT_VALOR = 1D;
    private static final Double UPDATED_VALOR = 2D;

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_ANO = 1;
    private static final Integer UPDATED_ANO = 2;

    @Autowired
    private EntradaRepository entradaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEntradaMockMvc;

    private Entrada entrada;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EntradaResource entradaResource = new EntradaResource(entradaRepository);
        this.restEntradaMockMvc = MockMvcBuilders.standaloneSetup(entradaResource)
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
    public static Entrada createEntity(EntityManager em) {
        Entrada entrada = new Entrada()
            .codEntrada(DEFAULT_COD_ENTRADA)
            .valor(DEFAULT_VALOR)
            .data(DEFAULT_DATA)
            .ano(DEFAULT_ANO);
        return entrada;
    }

    @Before
    public void initTest() {
        entrada = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntrada() throws Exception {
        int databaseSizeBeforeCreate = entradaRepository.findAll().size();

        // Create the Entrada
        restEntradaMockMvc.perform(post("/api/entradas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrada)))
            .andExpect(status().isCreated());

        // Validate the Entrada in the database
        List<Entrada> entradaList = entradaRepository.findAll();
        assertThat(entradaList).hasSize(databaseSizeBeforeCreate + 1);
        Entrada testEntrada = entradaList.get(entradaList.size() - 1);
        assertThat(testEntrada.getCodEntrada()).isEqualTo(DEFAULT_COD_ENTRADA);
        assertThat(testEntrada.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testEntrada.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testEntrada.getAno()).isEqualTo(DEFAULT_ANO);
    }

    @Test
    @Transactional
    public void createEntradaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entradaRepository.findAll().size();

        // Create the Entrada with an existing ID
        entrada.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntradaMockMvc.perform(post("/api/entradas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrada)))
            .andExpect(status().isBadRequest());

        // Validate the Entrada in the database
        List<Entrada> entradaList = entradaRepository.findAll();
        assertThat(entradaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = entradaRepository.findAll().size();
        // set the field null
        entrada.setValor(null);

        // Create the Entrada, which fails.

        restEntradaMockMvc.perform(post("/api/entradas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrada)))
            .andExpect(status().isBadRequest());

        List<Entrada> entradaList = entradaRepository.findAll();
        assertThat(entradaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEntradas() throws Exception {
        // Initialize the database
        entradaRepository.saveAndFlush(entrada);

        // Get all the entradaList
        restEntradaMockMvc.perform(get("/api/entradas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entrada.getId().intValue())))
            .andExpect(jsonPath("$.[*].codEntrada").value(hasItem(DEFAULT_COD_ENTRADA)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].ano").value(hasItem(DEFAULT_ANO)));
    }
    
    @Test
    @Transactional
    public void getEntrada() throws Exception {
        // Initialize the database
        entradaRepository.saveAndFlush(entrada);

        // Get the entrada
        restEntradaMockMvc.perform(get("/api/entradas/{id}", entrada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entrada.getId().intValue()))
            .andExpect(jsonPath("$.codEntrada").value(DEFAULT_COD_ENTRADA))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.ano").value(DEFAULT_ANO));
    }

    @Test
    @Transactional
    public void getNonExistingEntrada() throws Exception {
        // Get the entrada
        restEntradaMockMvc.perform(get("/api/entradas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntrada() throws Exception {
        // Initialize the database
        entradaRepository.saveAndFlush(entrada);

        int databaseSizeBeforeUpdate = entradaRepository.findAll().size();

        // Update the entrada
        Entrada updatedEntrada = entradaRepository.findById(entrada.getId()).get();
        // Disconnect from session so that the updates on updatedEntrada are not directly saved in db
        em.detach(updatedEntrada);
        updatedEntrada
            .codEntrada(UPDATED_COD_ENTRADA)
            .valor(UPDATED_VALOR)
            .data(UPDATED_DATA)
            .ano(UPDATED_ANO);

        restEntradaMockMvc.perform(put("/api/entradas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEntrada)))
            .andExpect(status().isOk());

        // Validate the Entrada in the database
        List<Entrada> entradaList = entradaRepository.findAll();
        assertThat(entradaList).hasSize(databaseSizeBeforeUpdate);
        Entrada testEntrada = entradaList.get(entradaList.size() - 1);
        assertThat(testEntrada.getCodEntrada()).isEqualTo(UPDATED_COD_ENTRADA);
        assertThat(testEntrada.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testEntrada.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testEntrada.getAno()).isEqualTo(UPDATED_ANO);
    }

    @Test
    @Transactional
    public void updateNonExistingEntrada() throws Exception {
        int databaseSizeBeforeUpdate = entradaRepository.findAll().size();

        // Create the Entrada

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntradaMockMvc.perform(put("/api/entradas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrada)))
            .andExpect(status().isBadRequest());

        // Validate the Entrada in the database
        List<Entrada> entradaList = entradaRepository.findAll();
        assertThat(entradaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEntrada() throws Exception {
        // Initialize the database
        entradaRepository.saveAndFlush(entrada);

        int databaseSizeBeforeDelete = entradaRepository.findAll().size();

        // Get the entrada
        restEntradaMockMvc.perform(delete("/api/entradas/{id}", entrada.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Entrada> entradaList = entradaRepository.findAll();
        assertThat(entradaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Entrada.class);
        Entrada entrada1 = new Entrada();
        entrada1.setId(1L);
        Entrada entrada2 = new Entrada();
        entrada2.setId(entrada1.getId());
        assertThat(entrada1).isEqualTo(entrada2);
        entrada2.setId(2L);
        assertThat(entrada1).isNotEqualTo(entrada2);
        entrada1.setId(null);
        assertThat(entrada1).isNotEqualTo(entrada2);
    }
}
