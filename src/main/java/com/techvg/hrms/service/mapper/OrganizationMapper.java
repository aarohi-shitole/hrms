package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Address;
import com.techvg.hrms.domain.Organization;
import com.techvg.hrms.service.dto.AddressDTO;
import com.techvg.hrms.service.dto.OrganizationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Organization} and its DTO {@link OrganizationDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrganizationMapper extends EntityMapper<OrganizationDTO, Organization> {
    @Mapping(target = "address", source = "address", qualifiedByName = "addressId")
    @Mapping(target = "organization", source = "organization", qualifiedByName = "organizationId")
    OrganizationDTO toDto(Organization s);

    @Named("addressId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AddressDTO toDtoAddressId(Address address);

    @Named("organizationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganizationDTO toDtoOrganizationId(Organization organization);
}
