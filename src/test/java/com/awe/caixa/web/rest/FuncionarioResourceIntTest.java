package com.awe.caixa.web.rest;

import com.awe.caixa.CaixaApp;

import com.awe.caixa.domain.Funcionario;
import com.awe.caixa.repository.FuncionarioRepository;
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
 * Test class for the FuncionarioResource REST controller.
 *
 * @see FuncionarioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaixaApp.class)
public class FuncionarioResourceIntTest {

    private static final Integer DEFAULT_COD_FUNCIONARIO = 1;
    private static final Integer UPDATED_COD_FUNCIONARIO = 2;

    private static final Integer DEFAULT_CARTEIRA_TRABALHO = 1;
    private static final Integer UPDATED_CARTEIRA_TRABALHO = 2;

    private static final LocalDate DEFAULT_DATA_ENTRADA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_ENTRADA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_SAIDA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_SAIDA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFuncionarioMockMvc;

    private Funcionario funcionario;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FuncionarioResource funcionarioResource = new FuncionarioResource(funcionarioRepository);
        this.restFuncionarioMockMvc = MockMvcBuilders.standaloneSetup(funcionarioResource)
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
    public static Funcionario createEntity(EntityManager em) {
        Funcionario funcionario = new Funcionario()
            .codFuncionario(DEFAULT_COD_FUNCIONARIO)
            .carteiraTrabalho(DEFAULT_CARTEIRA_TRABALHO)
            .dataEntrada(DEFAULT_DATA_ENTRADA)
            .dataSaida(DEFAULT_DATA_SAIDA);
        return funcionario;
    }

    @Before
    public void initTest() {
        funcionario = createEntity(em);
    }

    @Test
    @Transactional
    public void createFuncionario() throws Exception {
        int databaseSizeBeforeCreate = funcionarioRepository.findAll().size();

        // Create the Funcionario
        restFuncionarioMockMvc.perform(post("/api/funcionarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(funcionario)))
            .andExpect(status().isCreated());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeCreate + 1);
        Funcionario testFuncionario = funcionarioList.get(funcionarioList.size() - 1);
        assertThat(testFuncionario.getCodFuncionario()).isEqualTo(DEFAULT_COD_FUNCIONARIO);
        assertThat(testFuncionario.getCarteiraTrabalho()).isEqualTo(DEFAULT_CARTEIRA_TRABALHO);
        assertThat(testFuncionario.getDataEntrada()).isEqualTo(DEFAULT_DATA_ENTRADA);
        assertThat(testFuncionario.getDataSaida()).isEqualTo(DEFAULT_DATA_SAIDA);
    }

    @Test
    @Transactional
    public void createFuncionarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = funcionarioRepository.findAll().size();

        // Create the Funcionario with an existing ID
        funcionario.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuncionarioMockMvc.perform(post("/api/funcionarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(funcionario)))
            .andExpect(status().isBadRequest());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFuncionarios() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList
        restFuncionarioMockMvc.perform(get("/api/funcionarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funcionario.getId().intValue())))
            .andExpect(jsonPath("$.[*].codFuncionario").value(hasItem(DEFAULT_COD_FUNCIONARIO)))
            .andExpect(jsonPath("$.[*].carteiraTrabalho").value(hasItem(DEFAULT_CARTEIRA_TRABALHO)))
            .andExpect(jsonPath("$.[*].dataEntrada").value(hasItem(DEFAULT_DATA_ENTRADA.toString())))
            .andExpect(jsonPath("$.[*].dataSaida").value(hasItem(DEFAULT_DATA_SAIDA.toString())));
    }
    
    @Test
    @Transactional
    public void getFuncionario() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get the funcionario
        restFuncionarioMockMvc.perform(get("/api/funcionarios/{id}", funcionario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(funcionario.getId().intValue()))
            .andExpect(jsonPath("$.codFuncionario").value(DEFAULT_COD_FUNCIONARIO))
            .andExpect(jsonPath("$.carteiraTrabalho").value(DEFAULT_CARTEIRA_TRABALHO))
            .andExpect(jsonPath("$.dataEntrada").value(DEFAULT_DATA_ENTRADA.toString()))
            .andExpect(jsonPath("$.dataSaida").value(DEFAULT_DATA_SAIDA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFuncionario() throws Exception {
        // Get the funcionario
        restFuncionarioMockMvc.perform(get("/api/funcionarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFuncionario() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();

        // Update the funcionario
        Funcionario updatedFuncionario = funcionarioRepository.findById(funcionario.getId()).get();
        // Disconnect from session so that the updates on updatedFuncionario are not directly saved in db
        em.detach(updatedFuncionario);
        updatedFuncionario
            .codFuncionario(UPDATED_COD_FUNCIONARIO)
            .carteiraTrabalho(UPDATED_CARTEIRA_TRABALHO)
            .dataEntrada(UPDATED_DATA_ENTRADA)
            .dataSaida(UPDATED_DATA_SAIDA);

        restFuncionarioMockMvc.perform(put("/api/funcionarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFuncionario)))
            .andExpect(status().isOk());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
        Funcionario testFuncionario = funcionarioList.get(funcionarioList.size() - 1);
        assertThat(testFuncionario.getCodFuncionario()).isEqualTo(UPDATED_COD_FUNCIONARIO);
        assertThat(testFuncionario.getCarteiraTrabalho()).isEqualTo(UPDATED_CARTEIRA_TRABALHO);
        assertThat(testFuncionario.getDataEntrada()).isEqualTo(UPDATED_DATA_ENTRADA);
        assertThat(testFuncionario.getDataSaida()).isEqualTo(UPDATED_DATA_SAIDA);
    }

    @Test
    @Transactional
    public void updateNonExistingFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();

        // Create the Funcionario

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc.perform(put("/api/funcionarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(funcionario)))
            .andExpect(status().isBadRequest());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFuncionario() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        int databaseSizeBeforeDelete = funcionarioRepository.findAll().size();

        // Get the funcionario
        restFuncionarioMockMvc.perform(delete("/api/funcionarios/{id}", funcionario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Funcionario.class);
        Funcionario funcionario1 = new Funcionario();
        funcionario1.setId(1L);
        Funcionario funcionario2 = new Funcionario();
        funcionario2.setId(funcionario1.getId());
        assertThat(funcionario1).isEqualTo(funcionario2);
        funcionario2.setId(2L);
        assertThat(funcionario1).isNotEqualTo(funcionario2);
        funcionario1.setId(null);
        assertThat(funcionario1).isNotEqualTo(funcionario2);
    }
}
