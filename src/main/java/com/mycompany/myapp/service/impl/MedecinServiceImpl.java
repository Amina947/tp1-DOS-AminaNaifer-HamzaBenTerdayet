package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.MedecinService;
import com.mycompany.myapp.domain.Medecin;
import com.mycompany.myapp.repository.MedecinRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Medecin}.
 */
@Service
@Transactional
public class MedecinServiceImpl implements MedecinService {

    private final Logger log = LoggerFactory.getLogger(MedecinServiceImpl.class);

    private final MedecinRepository medecinRepository;

    public MedecinServiceImpl(MedecinRepository medecinRepository) {
        this.medecinRepository = medecinRepository;
    }

    @Override
    public Medecin save(Medecin medecin) {
        log.debug("Request to save Medecin : {}", medecin);
        return medecinRepository.save(medecin);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medecin> findAll() {
        log.debug("Request to get all Medecins");
        return medecinRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Medecin> findOne(Long id) {
        log.debug("Request to get Medecin : {}", id);
        return medecinRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Medecin : {}", id);
        medecinRepository.deleteById(id);
    }
}
