package com.techvg.hrms.service;

import com.techvg.hrms.domain.ExitFormalties;
import com.techvg.hrms.repository.ExitFormaltiesRepository;
import com.techvg.hrms.service.dto.ExitFormaltiesDTO;
import com.techvg.hrms.service.mapper.ExitFormaltiesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ExitFormalties}.
 */
@Service
@Transactional
public class ExitFormaltiesService {

    private final Logger log = LoggerFactory.getLogger(ExitFormaltiesService.class);

    private final ExitFormaltiesRepository exitFormaltiesRepository;

    private final ExitFormaltiesMapper exitFormaltiesMapper;

    public ExitFormaltiesService(ExitFormaltiesRepository exitFormaltiesRepository, ExitFormaltiesMapper exitFormaltiesMapper) {
        this.exitFormaltiesRepository = exitFormaltiesRepository;
        this.exitFormaltiesMapper = exitFormaltiesMapper;
    }

    /**
     * Save a exitFormalties.
     *
     * @param exitFormaltiesDTO the entity to save.
     * @return the persisted entity.
     */
    public ExitFormaltiesDTO save(ExitFormaltiesDTO exitFormaltiesDTO) {
        log.debug("Request to save ExitFormalties : {}", exitFormaltiesDTO);
        ExitFormalties exitFormalties = exitFormaltiesMapper.toEntity(exitFormaltiesDTO);
        exitFormalties = exitFormaltiesRepository.save(exitFormalties);
        return exitFormaltiesMapper.toDto(exitFormalties);
    }

    /**
     * Update a exitFormalties.
     *
     * @param exitFormaltiesDTO the entity to save.
     * @return the persisted entity.
     */
    public ExitFormaltiesDTO update(ExitFormaltiesDTO exitFormaltiesDTO) {
        log.debug("Request to update ExitFormalties : {}", exitFormaltiesDTO);
        ExitFormalties exitFormalties = exitFormaltiesMapper.toEntity(exitFormaltiesDTO);
        exitFormalties = exitFormaltiesRepository.save(exitFormalties);
        return exitFormaltiesMapper.toDto(exitFormalties);
    }

    /**
     * Partially update a exitFormalties.
     *
     * @param exitFormaltiesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ExitFormaltiesDTO> partialUpdate(ExitFormaltiesDTO exitFormaltiesDTO) {
        log.debug("Request to partially update ExitFormalties : {}", exitFormaltiesDTO);

        return exitFormaltiesRepository
            .findById(exitFormaltiesDTO.getId())
            .map(existingExitFormalties -> {
                exitFormaltiesMapper.partialUpdate(existingExitFormalties, exitFormaltiesDTO);

                return existingExitFormalties;
            })
            .map(exitFormaltiesRepository::save)
            .map(exitFormaltiesMapper::toDto);
    }

    /**
     * Get all the exitFormalties.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ExitFormaltiesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExitFormalties");
        return exitFormaltiesRepository.findAll(pageable).map(exitFormaltiesMapper::toDto);
    }

    /**
     * Get one exitFormalties by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ExitFormaltiesDTO> findOne(Long id) {
        log.debug("Request to get ExitFormalties : {}", id);
        return exitFormaltiesRepository.findById(id).map(exitFormaltiesMapper::toDto);
    }

    /**
     * Delete the exitFormalties by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ExitFormalties : {}", id);
        exitFormaltiesRepository.deleteById(id);
    }
}
