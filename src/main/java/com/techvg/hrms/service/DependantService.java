package com.techvg.hrms.service;

import com.techvg.hrms.domain.Dependant;
import com.techvg.hrms.repository.DependantRepository;
import com.techvg.hrms.service.dto.DependantDTO;
import com.techvg.hrms.service.mapper.DependantMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Dependant}.
 */
@Service
@Transactional
public class DependantService {

    private final Logger log = LoggerFactory.getLogger(DependantService.class);

    private final DependantRepository dependantRepository;

    private final DependantMapper dependantMapper;

    public DependantService(DependantRepository dependantRepository, DependantMapper dependantMapper) {
        this.dependantRepository = dependantRepository;
        this.dependantMapper = dependantMapper;
    }

    /**
     * Save a dependant.
     *
     * @param dependantDTO the entity to save.
     * @return the persisted entity.
     */
    public DependantDTO save(DependantDTO dependantDTO) {
        log.debug("Request to save Dependant : {}", dependantDTO);
        Dependant dependant = dependantMapper.toEntity(dependantDTO);
        dependant = dependantRepository.save(dependant);
        return dependantMapper.toDto(dependant);
    }

    /**
     * Update a dependant.
     *
     * @param dependantDTO the entity to save.
     * @return the persisted entity.
     */
    public DependantDTO update(DependantDTO dependantDTO) {
        log.debug("Request to update Dependant : {}", dependantDTO);
        Dependant dependant = dependantMapper.toEntity(dependantDTO);
        dependant = dependantRepository.save(dependant);
        return dependantMapper.toDto(dependant);
    }

    /**
     * Partially update a dependant.
     *
     * @param dependantDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DependantDTO> partialUpdate(DependantDTO dependantDTO) {
        log.debug("Request to partially update Dependant : {}", dependantDTO);

        return dependantRepository
            .findById(dependantDTO.getId())
            .map(existingDependant -> {
                dependantMapper.partialUpdate(existingDependant, dependantDTO);

                return existingDependant;
            })
            .map(dependantRepository::save)
            .map(dependantMapper::toDto);
    }

    /**
     * Get all the dependants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DependantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Dependants");
        return dependantRepository.findAll(pageable).map(dependantMapper::toDto);
    }

    /**
     * Get one dependant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DependantDTO> findOne(Long id) {
        log.debug("Request to get Dependant : {}", id);
        return dependantRepository.findById(id).map(dependantMapper::toDto);
    }

    /**
     * Delete the dependant by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Dependant : {}", id);
        dependantRepository.deleteById(id);
    }
}
