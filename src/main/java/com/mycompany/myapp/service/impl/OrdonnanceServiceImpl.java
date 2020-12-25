package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.OrdonnanceService;
import com.mycompany.myapp.domain.Ordonnance;
import com.mycompany.myapp.repository.OrdonnanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Ordonnance}.
 */
@Service
@Transactional
public class OrdonnanceServiceImpl implements OrdonnanceService {

    private final Logger log = LoggerFactory.getLogger(OrdonnanceServiceImpl.class);

    private final OrdonnanceRepository ordonnanceRepository;

    public OrdonnanceServiceImpl(OrdonnanceRepository ordonnanceRepository) {
        this.ordonnanceRepository = ordonnanceRepository;
    }

    @Override
    public Ordonnance save(Ordonnance ordonnance) {
        log.debug("Request to save Ordonnance : {}", ordonnance);
        return ordonnanceRepository.save(ordonnance);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ordonnance> findAll() {
        log.debug("Request to get all Ordonnances");
        return ordonnanceRepository.findAll();
    }



    /**
     *  Get all the ordonnances where Patient is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<Ordonnance> findAllWherePatientIsNull() {
        log.debug("Request to get all ordonnances where Patient is null");
        return StreamSupport
            .stream(ordonnanceRepository.findAll().spliterator(), false)
            .filter(ordonnance -> ordonnance.getPatient() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Ordonnance> findOne(Long id) {
        log.debug("Request to get Ordonnance : {}", id);
        return ordonnanceRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ordonnance : {}", id);
        ordonnanceRepository.deleteById(id);
    }
}
