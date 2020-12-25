package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.ProjectJhipsterrrApp;
import com.mycompany.myapp.domain.Ordonnance;
import com.mycompany.myapp.repository.OrdonnanceRepository;
import com.mycompany.myapp.service.OrdonnanceService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OrdonnanceResource} REST controller.
 */
@SpringBootTest(classes = ProjectJhipsterrrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OrdonnanceResourceIT {

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final String DEFAULT_MEDICAMENT = "AAAAAAAAAA";
    private static final String UPDATED_MEDICAMENT = "BBBBBBBBBB";

    @Autowired
    private OrdonnanceRepository ordonnanceRepository;

    @Autowired
    private OrdonnanceService ordonnanceService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdonnanceMockMvc;

    private Ordonnance ordonnance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ordonnance createEntity(EntityManager em) {
        Ordonnance ordonnance = new Ordonnance()
            .numero(DEFAULT_NUMERO)
            .medicament(DEFAULT_MEDICAMENT);
        return ordonnance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ordonnance createUpdatedEntity(EntityManager em) {
        Ordonnance ordonnance = new Ordonnance()
            .numero(UPDATED_NUMERO)
            .medicament(UPDATED_MEDICAMENT);
        return ordonnance;
    }

    @BeforeEach
    public void initTest() {
        ordonnance = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrdonnance() throws Exception {
        int databaseSizeBeforeCreate = ordonnanceRepository.findAll().size();
        // Create the Ordonnance
        restOrdonnanceMockMvc.perform(post("/api/ordonnances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ordonnance)))
            .andExpect(status().isCreated());

        // Validate the Ordonnance in the database
        List<Ordonnance> ordonnanceList = ordonnanceRepository.findAll();
        assertThat(ordonnanceList).hasSize(databaseSizeBeforeCreate + 1);
        Ordonnance testOrdonnance = ordonnanceList.get(ordonnanceList.size() - 1);
        assertThat(testOrdonnance.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testOrdonnance.getMedicament()).isEqualTo(DEFAULT_MEDICAMENT);
    }

    @Test
    @Transactional
    public void createOrdonnanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ordonnanceRepository.findAll().size();

        // Create the Ordonnance with an existing ID
        ordonnance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdonnanceMockMvc.perform(post("/api/ordonnances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ordonnance)))
            .andExpect(status().isBadRequest());

        // Validate the Ordonnance in the database
        List<Ordonnance> ordonnanceList = ordonnanceRepository.findAll();
        assertThat(ordonnanceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrdonnances() throws Exception {
        // Initialize the database
        ordonnanceRepository.saveAndFlush(ordonnance);

        // Get all the ordonnanceList
        restOrdonnanceMockMvc.perform(get("/api/ordonnances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordonnance.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].medicament").value(hasItem(DEFAULT_MEDICAMENT)));
    }
    
    @Test
    @Transactional
    public void getOrdonnance() throws Exception {
        // Initialize the database
        ordonnanceRepository.saveAndFlush(ordonnance);

        // Get the ordonnance
        restOrdonnanceMockMvc.perform(get("/api/ordonnances/{id}", ordonnance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordonnance.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.medicament").value(DEFAULT_MEDICAMENT));
    }
    @Test
    @Transactional
    public void getNonExistingOrdonnance() throws Exception {
        // Get the ordonnance
        restOrdonnanceMockMvc.perform(get("/api/ordonnances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrdonnance() throws Exception {
        // Initialize the database
        ordonnanceService.save(ordonnance);

        int databaseSizeBeforeUpdate = ordonnanceRepository.findAll().size();

        // Update the ordonnance
        Ordonnance updatedOrdonnance = ordonnanceRepository.findById(ordonnance.getId()).get();
        // Disconnect from session so that the updates on updatedOrdonnance are not directly saved in db
        em.detach(updatedOrdonnance);
        updatedOrdonnance
            .numero(UPDATED_NUMERO)
            .medicament(UPDATED_MEDICAMENT);

        restOrdonnanceMockMvc.perform(put("/api/ordonnances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrdonnance)))
            .andExpect(status().isOk());

        // Validate the Ordonnance in the database
        List<Ordonnance> ordonnanceList = ordonnanceRepository.findAll();
        assertThat(ordonnanceList).hasSize(databaseSizeBeforeUpdate);
        Ordonnance testOrdonnance = ordonnanceList.get(ordonnanceList.size() - 1);
        assertThat(testOrdonnance.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testOrdonnance.getMedicament()).isEqualTo(UPDATED_MEDICAMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingOrdonnance() throws Exception {
        int databaseSizeBeforeUpdate = ordonnanceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdonnanceMockMvc.perform(put("/api/ordonnances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ordonnance)))
            .andExpect(status().isBadRequest());

        // Validate the Ordonnance in the database
        List<Ordonnance> ordonnanceList = ordonnanceRepository.findAll();
        assertThat(ordonnanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrdonnance() throws Exception {
        // Initialize the database
        ordonnanceService.save(ordonnance);

        int databaseSizeBeforeDelete = ordonnanceRepository.findAll().size();

        // Delete the ordonnance
        restOrdonnanceMockMvc.perform(delete("/api/ordonnances/{id}", ordonnance.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ordonnance> ordonnanceList = ordonnanceRepository.findAll();
        assertThat(ordonnanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
