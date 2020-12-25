package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Ordonnance;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Ordonnance}.
 */
public interface OrdonnanceService {

    /**
     * Save a ordonnance.
     *
     * @param ordonnance the entity to save.
     * @return the persisted entity.
     */
    Ordonnance save(Ordonnance ordonnance);

    /**
     * Get all the ordonnances.
     *
     * @return the list of entities.
     */
    List<Ordonnance> findAll();
    /**
     * Get all the OrdonnanceDTO where Patient is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Ordonnance> findAllWherePatientIsNull();


    /**
     * Get the "id" ordonnance.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Ordonnance> findOne(Long id);

    /**
     * Delete the "id" ordonnance.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
