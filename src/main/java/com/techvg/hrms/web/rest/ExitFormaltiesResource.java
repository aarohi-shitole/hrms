package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.ExitFormaltiesRepository;
import com.techvg.hrms.service.ExitFormaltiesQueryService;
import com.techvg.hrms.service.ExitFormaltiesService;
import com.techvg.hrms.service.criteria.ExitFormaltiesCriteria;
import com.techvg.hrms.service.dto.ExitFormaltiesDTO;
import com.techvg.hrms.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.techvg.hrms.domain.ExitFormalties}.
 */
@RestController
@RequestMapping("/api")
public class ExitFormaltiesResource {

    private final Logger log = LoggerFactory.getLogger(ExitFormaltiesResource.class);

    private static final String ENTITY_NAME = "exitFormalties";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExitFormaltiesService exitFormaltiesService;

    private final ExitFormaltiesRepository exitFormaltiesRepository;

    private final ExitFormaltiesQueryService exitFormaltiesQueryService;

    public ExitFormaltiesResource(
        ExitFormaltiesService exitFormaltiesService,
        ExitFormaltiesRepository exitFormaltiesRepository,
        ExitFormaltiesQueryService exitFormaltiesQueryService
    ) {
        this.exitFormaltiesService = exitFormaltiesService;
        this.exitFormaltiesRepository = exitFormaltiesRepository;
        this.exitFormaltiesQueryService = exitFormaltiesQueryService;
    }

    /**
     * {@code POST  /exit-formalties} : Create a new exitFormalties.
     *
     * @param exitFormaltiesDTO the exitFormaltiesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new exitFormaltiesDTO, or with status {@code 400 (Bad Request)} if the exitFormalties has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/exit-formalties")
    public ResponseEntity<ExitFormaltiesDTO> createExitFormalties(@RequestBody ExitFormaltiesDTO exitFormaltiesDTO)
        throws URISyntaxException {
        log.debug("REST request to save ExitFormalties : {}", exitFormaltiesDTO);
        if (exitFormaltiesDTO.getId() != null) {
            throw new BadRequestAlertException("A new exitFormalties cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExitFormaltiesDTO result = exitFormaltiesService.save(exitFormaltiesDTO);
        return ResponseEntity
            .created(new URI("/api/exit-formalties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /exit-formalties/:id} : Updates an existing exitFormalties.
     *
     * @param id the id of the exitFormaltiesDTO to save.
     * @param exitFormaltiesDTO the exitFormaltiesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated exitFormaltiesDTO,
     * or with status {@code 400 (Bad Request)} if the exitFormaltiesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the exitFormaltiesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/exit-formalties/{id}")
    public ResponseEntity<ExitFormaltiesDTO> updateExitFormalties(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ExitFormaltiesDTO exitFormaltiesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ExitFormalties : {}, {}", id, exitFormaltiesDTO);
        if (exitFormaltiesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, exitFormaltiesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!exitFormaltiesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ExitFormaltiesDTO result = exitFormaltiesService.update(exitFormaltiesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, exitFormaltiesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /exit-formalties/:id} : Partial updates given fields of an existing exitFormalties, field will ignore if it is null
     *
     * @param id the id of the exitFormaltiesDTO to save.
     * @param exitFormaltiesDTO the exitFormaltiesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated exitFormaltiesDTO,
     * or with status {@code 400 (Bad Request)} if the exitFormaltiesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the exitFormaltiesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the exitFormaltiesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/exit-formalties/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ExitFormaltiesDTO> partialUpdateExitFormalties(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ExitFormaltiesDTO exitFormaltiesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ExitFormalties partially : {}, {}", id, exitFormaltiesDTO);
        if (exitFormaltiesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, exitFormaltiesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!exitFormaltiesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ExitFormaltiesDTO> result = exitFormaltiesService.partialUpdate(exitFormaltiesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, exitFormaltiesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /exit-formalties} : get all the exitFormalties.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of exitFormalties in body.
     */
    @GetMapping("/exit-formalties")
    public ResponseEntity<List<ExitFormaltiesDTO>> getAllExitFormalties(
        ExitFormaltiesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ExitFormalties by criteria: {}", criteria);
        Page<ExitFormaltiesDTO> page = exitFormaltiesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /exit-formalties/count} : count all the exitFormalties.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/exit-formalties/count")
    public ResponseEntity<Long> countExitFormalties(ExitFormaltiesCriteria criteria) {
        log.debug("REST request to count ExitFormalties by criteria: {}", criteria);
        return ResponseEntity.ok().body(exitFormaltiesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /exit-formalties/:id} : get the "id" exitFormalties.
     *
     * @param id the id of the exitFormaltiesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the exitFormaltiesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/exit-formalties/{id}")
    public ResponseEntity<ExitFormaltiesDTO> getExitFormalties(@PathVariable Long id) {
        log.debug("REST request to get ExitFormalties : {}", id);
        Optional<ExitFormaltiesDTO> exitFormaltiesDTO = exitFormaltiesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(exitFormaltiesDTO);
    }

    /**
     * {@code DELETE  /exit-formalties/:id} : delete the "id" exitFormalties.
     *
     * @param id the id of the exitFormaltiesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/exit-formalties/{id}")
    public ResponseEntity<Void> deleteExitFormalties(@PathVariable Long id) {
        log.debug("REST request to delete ExitFormalties : {}", id);
        exitFormaltiesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
