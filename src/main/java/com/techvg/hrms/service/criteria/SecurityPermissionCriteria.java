package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.SecurityPermission} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.SecurityPermissionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /security-permissions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SecurityPermissionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter permissionName;

    private StringFilter description;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter securityRoleId;

    private LongFilter employeeId;

    private Boolean distinct;

    public SecurityPermissionCriteria() {}

    public SecurityPermissionCriteria(SecurityPermissionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.permissionName = other.permissionName == null ? null : other.permissionName.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.securityRoleId = other.securityRoleId == null ? null : other.securityRoleId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SecurityPermissionCriteria copy() {
        return new SecurityPermissionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPermissionName() {
        return permissionName;
    }

    public StringFilter permissionName() {
        if (permissionName == null) {
            permissionName = new StringFilter();
        }
        return permissionName;
    }

    public void setPermissionName(StringFilter permissionName) {
        this.permissionName = permissionName;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public InstantFilter getLastModified() {
        return lastModified;
    }

    public InstantFilter lastModified() {
        if (lastModified == null) {
            lastModified = new InstantFilter();
        }
        return lastModified;
    }

    public void setLastModified(InstantFilter lastModified) {
        this.lastModified = lastModified;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            lastModifiedBy = new StringFilter();
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LongFilter getSecurityRoleId() {
        return securityRoleId;
    }

    public LongFilter securityRoleId() {
        if (securityRoleId == null) {
            securityRoleId = new LongFilter();
        }
        return securityRoleId;
    }

    public void setSecurityRoleId(LongFilter securityRoleId) {
        this.securityRoleId = securityRoleId;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public LongFilter employeeId() {
        if (employeeId == null) {
            employeeId = new LongFilter();
        }
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SecurityPermissionCriteria that = (SecurityPermissionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(permissionName, that.permissionName) &&
            Objects.equals(description, that.description) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(securityRoleId, that.securityRoleId) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, permissionName, description, lastModified, lastModifiedBy, securityRoleId, employeeId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SecurityPermissionCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (permissionName != null ? "permissionName=" + permissionName + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (securityRoleId != null ? "securityRoleId=" + securityRoleId + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
