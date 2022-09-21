package com.techvg.hrms.service.criteria;

import com.techvg.hrms.domain.enumeration.DependantType;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.Dependant} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.DependantResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /dependants?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DependantCriteria implements Serializable, Criteria {

    /**
     * Class for filtering DependantType
     */
    public static class DependantTypeFilter extends Filter<DependantType> {

        public DependantTypeFilter() {}

        public DependantTypeFilter(DependantTypeFilter filter) {
            super(filter);
        }

        @Override
        public DependantTypeFilter copy() {
            return new DependantTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter age;

    private StringFilter dateOfBirth;

    private DependantTypeFilter type;

    private StringFilter mobile;

    private StringFilter address;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private BooleanFilter isDeleted;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private StringFilter freefield4;

    private StringFilter freefield5;

    private LongFilter employeeId;

    private Boolean distinct;

    public DependantCriteria() {}

    public DependantCriteria(DependantCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.age = other.age == null ? null : other.age.copy();
        this.dateOfBirth = other.dateOfBirth == null ? null : other.dateOfBirth.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.mobile = other.mobile == null ? null : other.mobile.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.freefield4 = other.freefield4 == null ? null : other.freefield4.copy();
        this.freefield5 = other.freefield5 == null ? null : other.freefield5.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DependantCriteria copy() {
        return new DependantCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getAge() {
        return age;
    }

    public StringFilter age() {
        if (age == null) {
            age = new StringFilter();
        }
        return age;
    }

    public void setAge(StringFilter age) {
        this.age = age;
    }

    public StringFilter getDateOfBirth() {
        return dateOfBirth;
    }

    public StringFilter dateOfBirth() {
        if (dateOfBirth == null) {
            dateOfBirth = new StringFilter();
        }
        return dateOfBirth;
    }

    public void setDateOfBirth(StringFilter dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public DependantTypeFilter getType() {
        return type;
    }

    public DependantTypeFilter type() {
        if (type == null) {
            type = new DependantTypeFilter();
        }
        return type;
    }

    public void setType(DependantTypeFilter type) {
        this.type = type;
    }

    public StringFilter getMobile() {
        return mobile;
    }

    public StringFilter mobile() {
        if (mobile == null) {
            mobile = new StringFilter();
        }
        return mobile;
    }

    public void setMobile(StringFilter mobile) {
        this.mobile = mobile;
    }

    public StringFilter getAddress() {
        return address;
    }

    public StringFilter address() {
        if (address == null) {
            address = new StringFilter();
        }
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
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

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public StringFilter createdBy() {
        if (createdBy == null) {
            createdBy = new StringFilter();
        }
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedOn() {
        return createdOn;
    }

    public InstantFilter createdOn() {
        if (createdOn == null) {
            createdOn = new InstantFilter();
        }
        return createdOn;
    }

    public void setCreatedOn(InstantFilter createdOn) {
        this.createdOn = createdOn;
    }

    public BooleanFilter getIsDeleted() {
        return isDeleted;
    }

    public BooleanFilter isDeleted() {
        if (isDeleted == null) {
            isDeleted = new BooleanFilter();
        }
        return isDeleted;
    }

    public void setIsDeleted(BooleanFilter isDeleted) {
        this.isDeleted = isDeleted;
    }

    public StringFilter getFreeField1() {
        return freeField1;
    }

    public StringFilter freeField1() {
        if (freeField1 == null) {
            freeField1 = new StringFilter();
        }
        return freeField1;
    }

    public void setFreeField1(StringFilter freeField1) {
        this.freeField1 = freeField1;
    }

    public StringFilter getFreeField2() {
        return freeField2;
    }

    public StringFilter freeField2() {
        if (freeField2 == null) {
            freeField2 = new StringFilter();
        }
        return freeField2;
    }

    public void setFreeField2(StringFilter freeField2) {
        this.freeField2 = freeField2;
    }

    public StringFilter getFreeField3() {
        return freeField3;
    }

    public StringFilter freeField3() {
        if (freeField3 == null) {
            freeField3 = new StringFilter();
        }
        return freeField3;
    }

    public void setFreeField3(StringFilter freeField3) {
        this.freeField3 = freeField3;
    }

    public StringFilter getFreefield4() {
        return freefield4;
    }

    public StringFilter freefield4() {
        if (freefield4 == null) {
            freefield4 = new StringFilter();
        }
        return freefield4;
    }

    public void setFreefield4(StringFilter freefield4) {
        this.freefield4 = freefield4;
    }

    public StringFilter getFreefield5() {
        return freefield5;
    }

    public StringFilter freefield5() {
        if (freefield5 == null) {
            freefield5 = new StringFilter();
        }
        return freefield5;
    }

    public void setFreefield5(StringFilter freefield5) {
        this.freefield5 = freefield5;
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
        final DependantCriteria that = (DependantCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(age, that.age) &&
            Objects.equals(dateOfBirth, that.dateOfBirth) &&
            Objects.equals(type, that.type) &&
            Objects.equals(mobile, that.mobile) &&
            Objects.equals(address, that.address) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(freefield4, that.freefield4) &&
            Objects.equals(freefield5, that.freefield5) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            age,
            dateOfBirth,
            type,
            mobile,
            address,
            lastModified,
            lastModifiedBy,
            createdBy,
            createdOn,
            isDeleted,
            freeField1,
            freeField2,
            freeField3,
            freefield4,
            freefield5,
            employeeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DependantCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (age != null ? "age=" + age + ", " : "") +
            (dateOfBirth != null ? "dateOfBirth=" + dateOfBirth + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (mobile != null ? "mobile=" + mobile + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (freefield4 != null ? "freefield4=" + freefield4 + ", " : "") +
            (freefield5 != null ? "freefield5=" + freefield5 + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
