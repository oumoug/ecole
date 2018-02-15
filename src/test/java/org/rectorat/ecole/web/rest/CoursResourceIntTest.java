package org.rectorat.ecole.web.rest;

import org.rectorat.ecole.EcoleApp;

import org.rectorat.ecole.domain.Cours;
import org.rectorat.ecole.domain.Professeur;
import org.rectorat.ecole.domain.Classe;
import org.rectorat.ecole.domain.Salle;
import org.rectorat.ecole.repository.CoursRepository;
import org.rectorat.ecole.service.CoursService;
import org.rectorat.ecole.service.dto.CoursDTO;
import org.rectorat.ecole.service.mapper.CoursMapper;
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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.rectorat.ecole.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CoursResource REST controller.
 *
 * @see CoursResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EcoleApp.class)
public class CoursResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_HEURE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HEURE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    private CoursMapper coursMapper;

    @Autowired
    private CoursService coursService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCoursMockMvc;

    private Cours cours;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CoursResource coursResource = new CoursResource(coursService);
        this.restCoursMockMvc = MockMvcBuilders.standaloneSetup(coursResource)
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
    public static Cours createEntity(EntityManager em) {
        Cours cours = new Cours()
            .date(DEFAULT_DATE)
            .heure(DEFAULT_HEURE);
        // Add required entity
        Professeur coursProfesseur = ProfesseurResourceIntTest.createEntity(em);
        em.persist(coursProfesseur);
        em.flush();
        cours.setCoursProfesseur(coursProfesseur);
        // Add required entity
        Classe coursClass = ClasseResourceIntTest.createEntity(em);
        em.persist(coursClass);
        em.flush();
        cours.setCoursClass(coursClass);
        // Add required entity
        Salle salleCours = SalleResourceIntTest.createEntity(em);
        em.persist(salleCours);
        em.flush();
        cours.setSalleCours(salleCours);
        return cours;
    }

    @Before
    public void initTest() {
        cours = createEntity(em);
    }

    @Test
    @Transactional
    public void createCours() throws Exception {
        int databaseSizeBeforeCreate = coursRepository.findAll().size();

        // Create the Cours
        CoursDTO coursDTO = coursMapper.toDto(cours);
        restCoursMockMvc.perform(post("/api/cours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coursDTO)))
            .andExpect(status().isCreated());

        // Validate the Cours in the database
        List<Cours> coursList = coursRepository.findAll();
        assertThat(coursList).hasSize(databaseSizeBeforeCreate + 1);
        Cours testCours = coursList.get(coursList.size() - 1);
        assertThat(testCours.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testCours.getHeure()).isEqualTo(DEFAULT_HEURE);
    }

    @Test
    @Transactional
    public void createCoursWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coursRepository.findAll().size();

        // Create the Cours with an existing ID
        cours.setId(1L);
        CoursDTO coursDTO = coursMapper.toDto(cours);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoursMockMvc.perform(post("/api/cours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coursDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cours in the database
        List<Cours> coursList = coursRepository.findAll();
        assertThat(coursList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCours() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);

        // Get all the coursList
        restCoursMockMvc.perform(get("/api/cours?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cours.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].heure").value(hasItem(DEFAULT_HEURE.toString())));
    }

    @Test
    @Transactional
    public void getCours() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);

        // Get the cours
        restCoursMockMvc.perform(get("/api/cours/{id}", cours.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cours.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.heure").value(DEFAULT_HEURE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCours() throws Exception {
        // Get the cours
        restCoursMockMvc.perform(get("/api/cours/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCours() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);
        int databaseSizeBeforeUpdate = coursRepository.findAll().size();

        // Update the cours
        Cours updatedCours = coursRepository.findOne(cours.getId());
        // Disconnect from session so that the updates on updatedCours are not directly saved in db
        em.detach(updatedCours);
        updatedCours
            .date(UPDATED_DATE)
            .heure(UPDATED_HEURE);
        CoursDTO coursDTO = coursMapper.toDto(updatedCours);

        restCoursMockMvc.perform(put("/api/cours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coursDTO)))
            .andExpect(status().isOk());

        // Validate the Cours in the database
        List<Cours> coursList = coursRepository.findAll();
        assertThat(coursList).hasSize(databaseSizeBeforeUpdate);
        Cours testCours = coursList.get(coursList.size() - 1);
        assertThat(testCours.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCours.getHeure()).isEqualTo(UPDATED_HEURE);
    }

    @Test
    @Transactional
    public void updateNonExistingCours() throws Exception {
        int databaseSizeBeforeUpdate = coursRepository.findAll().size();

        // Create the Cours
        CoursDTO coursDTO = coursMapper.toDto(cours);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCoursMockMvc.perform(put("/api/cours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coursDTO)))
            .andExpect(status().isCreated());

        // Validate the Cours in the database
        List<Cours> coursList = coursRepository.findAll();
        assertThat(coursList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCours() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);
        int databaseSizeBeforeDelete = coursRepository.findAll().size();

        // Get the cours
        restCoursMockMvc.perform(delete("/api/cours/{id}", cours.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cours> coursList = coursRepository.findAll();
        assertThat(coursList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cours.class);
        Cours cours1 = new Cours();
        cours1.setId(1L);
        Cours cours2 = new Cours();
        cours2.setId(cours1.getId());
        assertThat(cours1).isEqualTo(cours2);
        cours2.setId(2L);
        assertThat(cours1).isNotEqualTo(cours2);
        cours1.setId(null);
        assertThat(cours1).isNotEqualTo(cours2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoursDTO.class);
        CoursDTO coursDTO1 = new CoursDTO();
        coursDTO1.setId(1L);
        CoursDTO coursDTO2 = new CoursDTO();
        assertThat(coursDTO1).isNotEqualTo(coursDTO2);
        coursDTO2.setId(coursDTO1.getId());
        assertThat(coursDTO1).isEqualTo(coursDTO2);
        coursDTO2.setId(2L);
        assertThat(coursDTO1).isNotEqualTo(coursDTO2);
        coursDTO1.setId(null);
        assertThat(coursDTO1).isNotEqualTo(coursDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(coursMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(coursMapper.fromId(null)).isNull();
    }
}
