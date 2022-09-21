package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.EmployeeSeperationRepository;
import com.techvg.hrms.service.EmployeeSeperationQueryService;
import com.techvg.hrms.service.EmployeeSeperationService;
import com.techvg.hrms.service.criteria.EmployeeSeperationCriteria;
import com.techvg.hrms.service.dto.EmployeeSeperationDTO;
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
 * REST controller for managing {@link com.techvg.hrms.domain.EmployeeSeperation}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeSeperationResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeSeperationResource.class);

    private static final String ENTITY_NAME = "employeeSeperation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeSeperationService employeeSeperationService;

    private final EmployeeSeperationRepository employeeSeperationRepository;

    private final EmployeeSeperationQueryService employeeSeperationQueryService;

    public EmployeeSeperationResource(
        EmployeeSeperationService employeeSeperationService,
        EmployeeSeperationRepository employeeSeperationRepository,
        EmployeeSeperationQueryService employeeSeperationQueryService
    ) {
        this.employeeSeperationService = employeeSeperationService;
        this.employeeSeperationRepository = employeeSeperationRepository;
        this.employeeSeperationQueryService = employeeSeperationQueryService;
    }

    /**
     * {@code POST  /employee-seperations} : Create a new employeeSeperation.
     *
     * @param employeeSeperationDTO the employeeSeperationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeSeperationDTO, or with status {@code 400 (Bad Request)} if the employeeSeperation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-seperations")
    public ResponseEntity<EmployeeSeperationDTO> createEmployeeSeperation(@RequestBody EmployeeSeperationDTO employeeSeperationDTO)
        throws URISyntaxException {
        log.debug("REST request to save EmployeeSeperation : {}", employeeSeperationDTO);
        if (employeeSeperationDTO.getId() != null) {
            throw new BadRequestAlertException("A new employeeSeperation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeSeperationDTO result = employeeSeperationService.save(employeeSeperationDTO);
        return ResponseEntity
            .created(new URI("/api/employee-seperations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-seperations/:id} : Updates an existing employeeSeperation.
     *
     * @param id the id of the employeeSeperationDTO to save.
     * @param employeeSeperationDTO the employeeSeperationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeSeperationDTO,
     * or with status {@code 400 (Bad Request)} if the employeeSeperationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeSeperationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-seperations/{id}")
    public ResponseEntity<EmployeeSeperationDTO> updateEmployeeSeperation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmployeeSeperationDTO employeeSeperationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EmployeeSeperation : {}, {}", id, employeeSeperationDTO);
        if (employeeSeperationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeSeperationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeSeperationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeSeperationDTO result = employeeSeperationService.update(employeeSeperationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeSeperationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employee-seperations/:id} : Partial updates given fields of an existing employeeSeperation, field will ignore if it is null
     *
     * @param id the id of the employeeSeperationDTO to save.
     * @param employeeSeperationDTO the employeeSeperationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeSeperationDTO,
     * or with status {@code 400 (Bad Request)} if the employeeSeperationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the employeeSeperationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeSeperationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employee-seperations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeeSeperationDTO> partialUpdateEmployeeSeperation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmployeeSeperationDTO employeeSeperationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmployeeSeperation partially : {}, {}", id, employeeSeperationDTO);
        if (employeeSeperationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeSeperationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeSeperationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeSeperationDTO> result = employeeSeperationService.partialUpdate(employeeSeperationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeSeperationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-seperations} : get all the employeeSeperations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeSeperations in body.
     */
    @GetMapping("/employee-seperations")
    public ResponseEntity<List<EmployeeSeperationDTO>> getAllEmployeeSeperations(
        EmployeeSeperationCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmployeeSeperations by criteria: {}", criteria);
        Page<EmployeeSeperationDTO> page = employeeSeperationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-seperations/count} : count all the employeeSeperations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-seperations/count")
    public ResponseEntity<Long> countEmployeeSeperations(EmployeeSeperationCriteria criteria) {
        log.debug("REST request to count EmployeeSeperations by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeSeperationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-seperations/:id} : get the "id" employeeSeperation.
     *
     * @param id the id of the employeeSeperationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeSeperationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-seperations/{id}")
    public ResponseEntity<EmployeeSeperationDTO> getEmployeeSeperation(@PathVariable Long id) {
        log.debug("REST request to get EmployeeSeperation : {}", id);
        Optional<EmployeeSeperationDTO> employeeSeperationDTO = employeeSeperationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeSeperationDTO);
    }

    /**
     * {@code DELETE  /employee-seperations/:id} : delete the "id" employeeSeperation.
     *
     * @param id the id of the employeeSeperationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-seperations/{id}")
    public ResponseEntity<Void> deleteEmployeeSeperation(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeSeperation : {}", id);
        employeeSeperationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
