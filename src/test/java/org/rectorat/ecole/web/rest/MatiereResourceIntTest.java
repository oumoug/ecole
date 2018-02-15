package org.rectorat.ecole.web.rest;

import org.rectorat.ecole.EcoleApp;

import org.rectorat.ecole.domain.Matiere;
import org.rectorat.ecole.repository.MatiereRepository;
import org.rectorat.ecole.service.MatiereService;
import org.rectorat.ecole.service.dto.MatiereDTO;
import org.rectorat.ecole.service.mapper.MatiereMapper;
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
 * Test class for the MatiereResource REST controller.
 *
 * @see MatiereResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EcoleApp.class)
public class MatiereResourceIntTest {

    private static final String DEFAULT_NOM_MATIERE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_MATIERE = "BBBBBBBBBB";

    @Autowired
    private MatiereRepository matiereRepository;

    @Autowired
    private MatiereMapper matiereMapper;

    @Autowired
    private MatiereService matiereService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMatiereMockMvc;

    private Matiere matiere;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MatiereResource matiereResource = new MatiereResource(matiereService);
        this.restMatiereMockMvc = MockMvcBuilders.standaloneSetup(matiereResource)
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
    public static Matiere createEntity(EntityManager em) {
        Matiere matiere = new Matiere()
            .nomMatiere(DEFAULT_NOM_MATIERE);
        return matiere;
    }

    @Before
    public void initTest() {
        matiere = createEntity(em);
    }

    @Test
    @Transactional
    public void createMatiere() throws Exception {
        int databaseSizeBeforeCreate = matiereRepository.findAll().size();

        // Create the Matiere
        MatiereDTO matiereDTO = matiereMapper.toDto(matiere);
        restMatiereMockMvc.perform(post("/api/matieres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matiereDTO)))
            .andExpect(status().isCreated());

        // Validate the Matiere in the database
        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeCreate + 1);
        Matiere testMatiere = matiereList.get(matiereList.size() - 1);
        assertThat(testMatiere.getNomMatiere()).isEqualTo(DEFAULT_NOM_MATIERE);
    }

    @Test
    @Transactional
    public void createMatiereWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = matiereRepository.findAll().size();

        // Create the Matiere with an existing ID
        matiere.setId(1L);
        MatiereDTO matiereDTO = matiereMapper.toDto(matiere);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatiereMockMvc.perform(post("/api/matieres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matiereDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Matiere in the database
        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomMatiereIsRequired() throws Exception {
        int databaseSizeBeforeTest = matiereRepository.findAll().size();
        // set the field null
        matiere.setNomMatiere(null);

        // Create the Matiere, which fails.
        MatiereDTO matiereDTO = matiereMapper.toDto(matiere);

        restMatiereMockMvc.perform(post("/api/matieres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matiereDTO)))
            .andExpect(status().isBadRequest());

        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMatieres() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList
        restMatiereMockMvc.perform(get("/api/matieres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matiere.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomMatiere").value(hasItem(DEFAULT_NOM_MATIERE.toString())));
    }

    @Test
    @Transactional
    public void getMatiere() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get the matiere
        restMatiereMockMvc.perform(get("/api/matieres/{id}", matiere.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(matiere.getId().intValue()))
            .andExpect(jsonPath("$.nomMatiere").value(DEFAULT_NOM_MATIERE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMatiere() throws Exception {
        // Get the matiere
        restMatiereMockMvc.perform(get("/api/matieres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatiere() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);
        int databaseSizeBeforeUpdate = matiereRepository.findAll().size();

        // Update the matiere
        Matiere updatedMatiere = matiereRepository.findOne(matiere.getId());
        // Disconnect from session so that the updates on updatedMatiere are not directly saved in db
        em.detach(updatedMatiere);
        updatedMatiere
            .nomMatiere(UPDATED_NOM_MATIERE);
        MatiereDTO matiereDTO = matiereMapper.toDto(updatedMatiere);

        restMatiereMockMvc.perform(put("/api/matieres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matiereDTO)))
            .andExpect(status().isOk());

        // Validate the Matiere in the database
        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeUpdate);
        Matiere testMatiere = matiereList.get(matiereList.size() - 1);
        assertThat(testMatiere.getNomMatiere()).isEqualTo(UPDATED_NOM_MATIERE);
    }

    @Test
    @Transactional
    public void updateNonExistingMatiere() throws Exception {
        int databaseSizeBeforeUpdate = matiereRepository.findAll().size();

        // Create the Matiere
        MatiereDTO matiereDTO = matiereMapper.toDto(matiere);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMatiereMockMvc.perform(put("/api/matieres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matiereDTO)))
            .andExpect(status().isCreated());

        // Validate the Matiere in the database
        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMatiere() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);
        int databaseSizeBeforeDelete = matiereRepository.findAll().size();

        // Get the matiere
        restMatiereMockMvc.perform(delete("/api/matieres/{id}", matiere.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Matiere.class);
        Matiere matiere1 = new Matiere();
        matiere1.setId(1L);
        Matiere matiere2 = new Matiere();
        matiere2.setId(matiere1.getId());
        assertThat(matiere1).isEqualTo(matiere2);
        matiere2.setId(2L);
        assertThat(matiere1).isNotEqualTo(matiere2);
        matiere1.setId(null);
        assertThat(matiere1).isNotEqualTo(matiere2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatiereDTO.class);
        MatiereDTO matiereDTO1 = new MatiereDTO();
        matiereDTO1.setId(1L);
        MatiereDTO matiereDTO2 = new MatiereDTO();
        assertThat(matiereDTO1).isNotEqualTo(matiereDTO2);
        matiereDTO2.setId(matiereDTO1.getId());
        assertThat(matiereDTO1).isEqualTo(matiereDTO2);
        matiereDTO2.setId(2L);
        assertThat(matiereDTO1).isNotEqualTo(matiereDTO2);
        matiereDTO1.setId(null);
        assertThat(matiereDTO1).isNotEqualTo(matiereDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(matiereMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(matiereMapper.fromId(null)).isNull();
    }
}
