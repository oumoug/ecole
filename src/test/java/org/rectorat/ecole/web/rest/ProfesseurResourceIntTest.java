package org.rectorat.ecole.web.rest;

import org.rectorat.ecole.EcoleApp;

import org.rectorat.ecole.domain.Professeur;
import org.rectorat.ecole.repository.ProfesseurRepository;
import org.rectorat.ecole.service.ProfesseurService;
import org.rectorat.ecole.service.dto.ProfesseurDTO;
import org.rectorat.ecole.service.mapper.ProfesseurMapper;
import org.rectorat.ecole.web.rest.errors.ExceptionTranslator;

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

import static org.rectorat.ecole.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProfesseurResource REST controller.
 *
 * @see ProfesseurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EcoleApp.class)
public class ProfesseurResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    @Autowired
    private ProfesseurRepository professeurRepository;

    @Autowired
    private ProfesseurMapper professeurMapper;

    @Autowired
    private ProfesseurService professeurService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProfesseurMockMvc;

    private Professeur professeur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfesseurResource professeurResource = new ProfesseurResource(professeurService);
        this.restProfesseurMockMvc = MockMvcBuilders.standaloneSetup(professeurResource)
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
    public static Professeur createEntity(EntityManager em) {
        Professeur professeur = new Professeur()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM);
        return professeur;
    }

    @Before
    public void initTest() {
        professeur = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfesseur() throws Exception {
        int databaseSizeBeforeCreate = professeurRepository.findAll().size();

        // Create the Professeur
        ProfesseurDTO professeurDTO = professeurMapper.toDto(professeur);
        restProfesseurMockMvc.perform(post("/api/professeurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professeurDTO)))
            .andExpect(status().isCreated());

        // Validate the Professeur in the database
        List<Professeur> professeurList = professeurRepository.findAll();
        assertThat(professeurList).hasSize(databaseSizeBeforeCreate + 1);
        Professeur testProfesseur = professeurList.get(professeurList.size() - 1);
        assertThat(testProfesseur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testProfesseur.getPrenom()).isEqualTo(DEFAULT_PRENOM);
    }

    @Test
    @Transactional
    public void createProfesseurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = professeurRepository.findAll().size();

        // Create the Professeur with an existing ID
        professeur.setId(1L);
        ProfesseurDTO professeurDTO = professeurMapper.toDto(professeur);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfesseurMockMvc.perform(post("/api/professeurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professeurDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Professeur in the database
        List<Professeur> professeurList = professeurRepository.findAll();
        assertThat(professeurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = professeurRepository.findAll().size();
        // set the field null
        professeur.setNom(null);

        // Create the Professeur, which fails.
        ProfesseurDTO professeurDTO = professeurMapper.toDto(professeur);

        restProfesseurMockMvc.perform(post("/api/professeurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professeurDTO)))
            .andExpect(status().isBadRequest());

        List<Professeur> professeurList = professeurRepository.findAll();
        assertThat(professeurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = professeurRepository.findAll().size();
        // set the field null
        professeur.setPrenom(null);

        // Create the Professeur, which fails.
        ProfesseurDTO professeurDTO = professeurMapper.toDto(professeur);

        restProfesseurMockMvc.perform(post("/api/professeurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professeurDTO)))
            .andExpect(status().isBadRequest());

        List<Professeur> professeurList = professeurRepository.findAll();
        assertThat(professeurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProfesseurs() throws Exception {
        // Initialize the database
        professeurRepository.saveAndFlush(professeur);

        // Get all the professeurList
        restProfesseurMockMvc.perform(get("/api/professeurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(professeur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())));
    }

    @Test
    @Transactional
    public void getProfesseur() throws Exception {
        // Initialize the database
        professeurRepository.saveAndFlush(professeur);

        // Get the professeur
        restProfesseurMockMvc.perform(get("/api/professeurs/{id}", professeur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(professeur.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProfesseur() throws Exception {
        // Get the professeur
        restProfesseurMockMvc.perform(get("/api/professeurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfesseur() throws Exception {
        // Initialize the database
        professeurRepository.saveAndFlush(professeur);
        int databaseSizeBeforeUpdate = professeurRepository.findAll().size();

        // Update the professeur
        Professeur updatedProfesseur = professeurRepository.findOne(professeur.getId());
        // Disconnect from session so that the updates on updatedProfesseur are not directly saved in db
        em.detach(updatedProfesseur);
        updatedProfesseur
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM);
        ProfesseurDTO professeurDTO = professeurMapper.toDto(updatedProfesseur);

        restProfesseurMockMvc.perform(put("/api/professeurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professeurDTO)))
            .andExpect(status().isOk());

        // Validate the Professeur in the database
        List<Professeur> professeurList = professeurRepository.findAll();
        assertThat(professeurList).hasSize(databaseSizeBeforeUpdate);
        Professeur testProfesseur = professeurList.get(professeurList.size() - 1);
        assertThat(testProfesseur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testProfesseur.getPrenom()).isEqualTo(UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void updateNonExistingProfesseur() throws Exception {
        int databaseSizeBeforeUpdate = professeurRepository.findAll().size();

        // Create the Professeur
        ProfesseurDTO professeurDTO = professeurMapper.toDto(professeur);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProfesseurMockMvc.perform(put("/api/professeurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professeurDTO)))
            .andExpect(status().isCreated());

        // Validate the Professeur in the database
        List<Professeur> professeurList = professeurRepository.findAll();
        assertThat(professeurList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProfesseur() throws Exception {
        // Initialize the database
        professeurRepository.saveAndFlush(professeur);
        int databaseSizeBeforeDelete = professeurRepository.findAll().size();

        // Get the professeur
        restProfesseurMockMvc.perform(delete("/api/professeurs/{id}", professeur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Professeur> professeurList = professeurRepository.findAll();
        assertThat(professeurList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Professeur.class);
        Professeur professeur1 = new Professeur();
        professeur1.setId(1L);
        Professeur professeur2 = new Professeur();
        professeur2.setId(professeur1.getId());
        assertThat(professeur1).isEqualTo(professeur2);
        professeur2.setId(2L);
        assertThat(professeur1).isNotEqualTo(professeur2);
        professeur1.setId(null);
        assertThat(professeur1).isNotEqualTo(professeur2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfesseurDTO.class);
        ProfesseurDTO professeurDTO1 = new ProfesseurDTO();
        professeurDTO1.setId(1L);
        ProfesseurDTO professeurDTO2 = new ProfesseurDTO();
        assertThat(professeurDTO1).isNotEqualTo(professeurDTO2);
        professeurDTO2.setId(professeurDTO1.getId());
        assertThat(professeurDTO1).isEqualTo(professeurDTO2);
        professeurDTO2.setId(2L);
        assertThat(professeurDTO1).isNotEqualTo(professeurDTO2);
        professeurDTO1.setId(null);
        assertThat(professeurDTO1).isNotEqualTo(professeurDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(professeurMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(professeurMapper.fromId(null)).isNull();
    }
}
