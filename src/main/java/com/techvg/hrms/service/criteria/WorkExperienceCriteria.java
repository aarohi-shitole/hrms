package com.techvg.hrms.service.criteria;

import com.techvg.hrms.domain.enumeration.CompanyType;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.WorkExperience} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.WorkExperienceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /work-experiences?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WorkExperienceCriteria implements Serializable, Criteria {

    /**
     * Class for filtering CompanyType
     */
    public static class CompanyTypeFilter extends Filter<CompanyType> {

        public CompanyTypeFilter() {}

        public CompanyTypeFilter(CompanyTypeFilter filter) {
            super(filter);
        }

        @Override
        public CompanyTypeFilter copy() {
            return new CompanyTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter jobTitle;

    private StringFilter jobDesc;

    private StringFilter companyName;

    private CompanyTypeFilter companyType;

    private StringFilter orgAddress;

    private DoubleFilter yearOfExp;

    private InstantFilter startDate;

    private InstantFilter endDate;

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

    public WorkExperienceCriteria() {}

    public WorkExperienceCriteria(WorkExperienceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.jobTitle = other.jobTitle == null ? null : other.jobTitle.copy();
        this.jobDesc = other.jobDesc == null ? null : other.jobDesc.copy();
        this.companyName = other.companyName == null ? null : other.companyName.copy();
        this.companyType = other.companyType == null ? null : other.companyType.copy();
        this.orgAddress = other.orgAddress == null ? null : other.orgAddress.copy();
        this.yearOfExp = other.yearOfExp == null ? null : other.yearOfExp.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
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
    public WorkExperienceCriteria copy() {
        return new WorkExperienceCriteria(this);
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

    public StringFilter getJobTitle() {
        return jobTitle;
    }

    public StringFilter jobTitle() {
        if (jobTitle == null) {
            jobTitle = new StringFilter();
        }
        return jobTitle;
    }

    public void setJobTitle(StringFilter jobTitle) {
        this.jobTitle = jobTitle;
    }

    public StringFilter getJobDesc() {
        return jobDesc;
    }

    public StringFilter jobDesc() {
        if (jobDesc == null) {
            jobDesc = new StringFilter();
        }
        return jobDesc;
    }

    public void setJobDesc(StringFilter jobDesc) {
        this.jobDesc = jobDesc;
    }

    public StringFilter getCompanyName() {
        return companyName;
    }

    public StringFilter companyName() {
        if (companyName == null) {
            companyName = new StringFilter();
        }
        return companyName;
    }

    public void setCompanyName(StringFilter companyName) {
        this.companyName = companyName;
    }

    public CompanyTypeFilter getCompanyType() {
        return companyType;
    }

    public CompanyTypeFilter companyType() {
        if (companyType == null) {
            companyType = new CompanyTypeFilter();
        }
        return companyType;
    }

    public void setCompanyType(CompanyTypeFilter companyType) {
        this.companyType = companyType;
    }

    public StringFilter getOrgAddress() {
        return orgAddress;
    }

    public StringFilter orgAddress() {
        if (orgAddress == null) {
            orgAddress = new StringFilter();
        }
        return orgAddress;
    }

    public void setOrgAddress(StringFilter orgAddress) {
        this.orgAddress = orgAddress;
    }

    public DoubleFilter getYearOfExp() {
        return yearOfExp;
    }

    public DoubleFilter yearOfExp() {
        if (yearOfExp == null) {
            yearOfExp = new DoubleFilter();
        }
        return yearOfExp;
    }

    public void setYearOfExp(DoubleFilter yearOfExp) {
        this.yearOfExp = yearOfExp;
    }

    public InstantFilter getStartDate() {
        return startDate;
    }

    public InstantFilter startDate() {
        if (startDate == null) {
            startDate = new InstantFilter();
        }
        return startDate;
    }

    public void setStartDate(InstantFilter startDate) {
        this.startDate = startDate;
    }

    public InstantFilter getEndDate() {
        return endDate;
    }

    public InstantFilter endDate() {
        if (endDate == null) {
            endDate = new InstantFilter();
        }
        return endDate;
    }

    public void setEndDate(InstantFilter endDate) {
        this.endDate = endDate;
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
        final WorkExperienceCriteria that = (WorkExperienceCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(jobTitle, that.jobTitle) &&
            Objects.equals(jobDesc, that.jobDesc) &&
            Objects.equals(companyName, that.companyName) &&
            Objects.equals(companyType, that.companyType) &&
            Objects.equals(orgAddress, that.orgAddress) &&
            Objects.equals(yearOfExp, that.yearOfExp) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
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
            jobTitle,
            jobDesc,
            companyName,
            companyType,
            orgAddress,
            yearOfExp,
            startDate,
            endDate,
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
        return "WorkExperienceCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (jobTitle != null ? "jobTitle=" + jobTitle + ", " : "") +
            (jobDesc != null ? "jobDesc=" + jobDesc + ", " : "") +
            (companyName != null ? "companyName=" + companyName + ", " : "") +
            (companyType != null ? "companyType=" + companyType + ", " : "") +
            (orgAddress != null ? "orgAddress=" + orgAddress + ", " : "") +
            (yearOfExp != null ? "yearOfExp=" + yearOfExp + ", " : "") +
            (startDate != null ? "startDate=" + startDate + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
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
