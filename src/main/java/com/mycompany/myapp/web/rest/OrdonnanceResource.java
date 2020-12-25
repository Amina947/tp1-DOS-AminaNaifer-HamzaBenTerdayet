package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Ordonnance;
import com.mycompany.myapp.service.OrdonnanceService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Ordonnance}.
 */
@RestController
@RequestMapping("/api")
public class OrdonnanceResource {

    private final Logger log = LoggerFactory.getLogger(OrdonnanceResource.class);

    private static final String ENTITY_NAME = "ordonnance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdonnanceService ordonnanceService;

    public OrdonnanceResource(OrdonnanceService ordonnanceService) {
        this.ordonnanceService = ordonnanceService;
    }

    /**
     * {@code POST  /ordonnances} : Create a new ordonnance.
     *
     * @param ordonnance the ordonnance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordonnance, or with status {@code 400 (Bad Request)} if the ordonnance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ordonnances")
    public ResponseEntity<Ordonnance> createOrdonnance(@RequestBody Ordonnance ordonnance) throws URISyntaxException {
        log.debug("REST request to save Ordonnance : {}", ordonnance);
        if (ordonnance.getId() != null) {
            throw new BadRequestAlertException("A new ordonnance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ordonnance result = ordonnanceService.save(ordonnance);
        return ResponseEntity.created(new URI("/api/ordonnances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ordonnances} : Updates an existing ordonnance.
     *
     * @param ordonnance the ordonnance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordonnance,
     * or with status {@code 400 (Bad Request)} if the ordonnance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordonnance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ordonnances")
    public ResponseEntity<Ordonnance> updateOrdonnance(@RequestBody Ordonnance ordonnance) throws URISyntaxException {
        log.debug("REST request to update Ordonnance : {}", ordonnance);
        if (ordonnance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Ordonnance result = ordonnanceService.save(ordonnance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordonnance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ordonnances} : get all the ordonnances.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordonnances in body.
     */
    @GetMapping("/ordonnances")
    public List<Ordonnance> getAllOrdonnances(@RequestParam(required = false) String filter) {
        if ("patient-is-null".equals(filter)) {
            log.debug("REST request to get all Ordonnances where patient is null");
            return ordonnanceService.findAllWherePatientIsNull();
        }
        log.debug("REST request to get all Ordonnances");
        return ordonnanceService.findAll();
    }

    /**
     * {@code GET  /ordonnances/:id} : get the "id" ordonnance.
     *
     * @param id the id of the ordonnance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordonnance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ordonnances/{id}")
    public ResponseEntity<Ordonnance> getOrdonnance(@PathVariable Long id) {
        log.debug("REST request to get Ordonnance : {}", id);
        Optional<Ordonnance> ordonnance = ordonnanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordonnance);
    }

    /**
     * {@code DELETE  /ordonnances/:id} : delete the "id" ordonnance.
     *
     * @param id the id of the ordonnance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ordonnances/{id}")
    public ResponseEntity<Void> deleteOrdonnance(@PathVariable Long id) {
        log.debug("REST request to delete Ordonnance : {}", id);
        ordonnanceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
