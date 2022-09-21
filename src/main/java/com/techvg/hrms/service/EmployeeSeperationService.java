package com.techvg.hrms.service;

import com.techvg.hrms.domain.EmployeeSeperation;
import com.techvg.hrms.repository.EmployeeSeperationRepository;
import com.techvg.hrms.service.dto.EmployeeSeperationDTO;
import com.techvg.hrms.service.mapper.EmployeeSeperationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmployeeSeperation}.
 */
@Service
@Transactional
public class EmployeeSeperationService {

    private final Logger log = LoggerFactory.getLogger(EmployeeSeperationService.class);

    private final EmployeeSeperationRepository employeeSeperationRepository;

    private final EmployeeSeperationMapper employeeSeperationMapper;

    public EmployeeSeperationService(
        EmployeeSeperationRepository employeeSeperationRepository,
        EmployeeSeperationMapper employeeSeperationMapper
    ) {
        this.employeeSeperationRepository = employeeSeperationRepository;
        this.employeeSeperationMapper = employeeSeperationMapper;
    }

    /**
     * Save a employeeSeperation.
     *
     * @param employeeSeperationDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeeSeperationDTO save(EmployeeSeperationDTO employeeSeperationDTO) {
        log.debug("Request to save EmployeeSeperation : {}", employeeSeperationDTO);
        EmployeeSeperation employeeSeperation = employeeSeperationMapper.toEntity(employeeSeperationDTO);
        employeeSeperation = employeeSeperationRepository.save(employeeSeperation);
        return employeeSeperationMapper.toDto(employeeSeperation);
    }

    /**
     * Update a employeeSeperation.
     *
     * @param employeeSeperationDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeeSeperationDTO update(EmployeeSeperationDTO employeeSeperationDTO) {
        log.debug("Request to update EmployeeSeperation : {}", employeeSeperationDTO);
        EmployeeSeperation employeeSeperation = employeeSeperationMapper.toEntity(employeeSeperationDTO);
        employeeSeperation = employeeSeperationRepository.save(employeeSeperation);
        return employeeSeperationMapper.toDto(employeeSeperation);
    }

    /**
     * Partially update a employeeSeperation.
     *
     * @param employeeSeperationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeSeperationDTO> partialUpdate(EmployeeSeperationDTO employeeSeperationDTO) {
        log.debug("Request to partially update EmployeeSeperation : {}", employeeSeperationDTO);

        return employeeSeperationRepository
            .findById(employeeSeperationDTO.getId())
            .map(existingEmployeeSeperation -> {
                employeeSeperationMapper.partialUpdate(existingEmployeeSeperation, employeeSeperationDTO);

                return existingEmployeeSeperation;
            })
            .map(employeeSeperationRepository::save)
            .map(employeeSeperationMapper::toDto);
    }

    /**
     * Get all the employeeSeperations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeSeperationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeSeperations");
        return employeeSeperationRepository.findAll(pageable).map(employeeSeperationMapper::toDto);
    }

    /**
     * Get one employeeSeperation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeSeperationDTO> findOne(Long id) {
        log.debug("Request to get EmployeeSeperation : {}", id);
        return employeeSeperationRepository.findById(id).map(employeeSeperationMapper::toDto);
    }

    /**
     * Delete the employeeSeperation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeSeperation : {}", id);
        employeeSeperationRepository.deleteById(id);
    }
}
