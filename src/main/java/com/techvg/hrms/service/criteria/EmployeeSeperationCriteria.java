package com.techvg.hrms.service.criteria;

import com.techvg.hrms.domain.enumeration.SeperationStatus;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.EmployeeSeperation} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.EmployeeSeperationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-seperations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeSeperationCriteria implements Serializable, Criteria {

    /**
     * Class for filtering SeperationStatus
     */
    public static class SeperationStatusFilter extends Filter<SeperationStatus> {

        public SeperationStatusFilter() {}

        public SeperationStatusFilter(SeperationStatusFilter filter) {
            super(filter);
        }

        @Override
        public SeperationStatusFilter copy() {
            return new SeperationStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter reasonForSeperation;

    private InstantFilter seperationDate;

    private StringFilter comment;

    private SeperationStatusFilter seperationStatus;

    private StringFilter otherReason;

    private LongFilter nagotiatedPeriod;

    private StringFilter createdBy;

    private InstantFilter updatedOn;

    private InstantFilter createdOn;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter freefield1;

    private StringFilter freefield2;

    private StringFilter freefield3;

    private StringFilter freefield4;

    private StringFilter freefield5;

    private LongFilter employeeId;

    private Boolean distinct;

    public EmployeeSeperationCriteria() {}

    public EmployeeSeperationCriteria(EmployeeSeperationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.reasonForSeperation = other.reasonForSeperation == null ? null : other.reasonForSeperation.copy();
        this.seperationDate = other.seperationDate == null ? null : other.seperationDate.copy();
        this.comment = other.comment == null ? null : other.comment.copy();
        this.seperationStatus = other.seperationStatus == null ? null : other.seperationStatus.copy();
        this.otherReason = other.otherReason == null ? null : other.otherReason.copy();
        this.nagotiatedPeriod = other.nagotiatedPeriod == null ? null : other.nagotiatedPeriod.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.updatedOn = other.updatedOn == null ? null : other.updatedOn.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.freefield1 = other.freefield1 == null ? null : other.freefield1.copy();
        this.freefield2 = other.freefield2 == null ? null : other.freefield2.copy();
        this.freefield3 = other.freefield3 == null ? null : other.freefield3.copy();
        this.freefield4 = other.freefield4 == null ? null : other.freefield4.copy();
        this.freefield5 = other.freefield5 == null ? null : other.freefield5.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeSeperationCriteria copy() {
        return new EmployeeSeperationCriteria(this);
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

    public StringFilter getReasonForSeperation() {
        return reasonForSeperation;
    }

    public StringFilter reasonForSeperation() {
        if (reasonForSeperation == null) {
            reasonForSeperation = new StringFilter();
        }
        return reasonForSeperation;
    }

    public void setReasonForSeperation(StringFilter reasonForSeperation) {
        this.reasonForSeperation = reasonForSeperation;
    }

    public InstantFilter getSeperationDate() {
        return seperationDate;
    }

    public InstantFilter seperationDate() {
        if (seperationDate == null) {
            seperationDate = new InstantFilter();
        }
        return seperationDate;
    }

    public void setSeperationDate(InstantFilter seperationDate) {
        this.seperationDate = seperationDate;
    }

    public StringFilter getComment() {
        return comment;
    }

    public StringFilter comment() {
        if (comment == null) {
            comment = new StringFilter();
        }
        return comment;
    }

    public void setComment(StringFilter comment) {
        this.comment = comment;
    }

    public SeperationStatusFilter getSeperationStatus() {
        return seperationStatus;
    }

    public SeperationStatusFilter seperationStatus() {
        if (seperationStatus == null) {
            seperationStatus = new SeperationStatusFilter();
        }
        return seperationStatus;
    }

    public void setSeperationStatus(SeperationStatusFilter seperationStatus) {
        this.seperationStatus = seperationStatus;
    }

    public StringFilter getOtherReason() {
        return otherReason;
    }

    public StringFilter otherReason() {
        if (otherReason == null) {
            otherReason = new StringFilter();
        }
        return otherReason;
    }

    public void setOtherReason(StringFilter otherReason) {
        this.otherReason = otherReason;
    }

    public LongFilter getNagotiatedPeriod() {
        return nagotiatedPeriod;
    }

    public LongFilter nagotiatedPeriod() {
        if (nagotiatedPeriod == null) {
            nagotiatedPeriod = new LongFilter();
        }
        return nagotiatedPeriod;
    }

    public void setNagotiatedPeriod(LongFilter nagotiatedPeriod) {
        this.nagotiatedPeriod = nagotiatedPeriod;
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

    public InstantFilter getUpdatedOn() {
        return updatedOn;
    }

    public InstantFilter updatedOn() {
        if (updatedOn == null) {
            updatedOn = new InstantFilter();
        }
        return updatedOn;
    }

    public void setUpdatedOn(InstantFilter updatedOn) {
        this.updatedOn = updatedOn;
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

    public StringFilter getFreefield1() {
        return freefield1;
    }

    public StringFilter freefield1() {
        if (freefield1 == null) {
            freefield1 = new StringFilter();
        }
        return freefield1;
    }

    public void setFreefield1(StringFilter freefield1) {
        this.freefield1 = freefield1;
    }

    public StringFilter getFreefield2() {
        return freefield2;
    }

    public StringFilter freefield2() {
        if (freefield2 == null) {
            freefield2 = new StringFilter();
        }
        return freefield2;
    }

    public void setFreefield2(StringFilter freefield2) {
        this.freefield2 = freefield2;
    }

    public StringFilter getFreefield3() {
        return freefield3;
    }

    public StringFilter freefield3() {
        if (freefield3 == null) {
            freefield3 = new StringFilter();
        }
        return freefield3;
    }

    public void setFreefield3(StringFilter freefield3) {
        this.freefield3 = freefield3;
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
        final EmployeeSeperationCriteria that = (EmployeeSeperationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(reasonForSeperation, that.reasonForSeperation) &&
            Objects.equals(seperationDate, that.seperationDate) &&
            Objects.equals(comment, that.comment) &&
            Objects.equals(seperationStatus, that.seperationStatus) &&
            Objects.equals(otherReason, that.otherReason) &&
            Objects.equals(nagotiatedPeriod, that.nagotiatedPeriod) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(updatedOn, that.updatedOn) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(freefield1, that.freefield1) &&
            Objects.equals(freefield2, that.freefield2) &&
            Objects.equals(freefield3, that.freefield3) &&
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
            reasonForSeperation,
            seperationDate,
            comment,
            seperationStatus,
            otherReason,
            nagotiatedPeriod,
            createdBy,
            updatedOn,
            createdOn,
            lastModified,
            lastModifiedBy,
            freefield1,
            freefield2,
            freefield3,
            freefield4,
            freefield5,
            employeeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeSeperationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (reasonForSeperation != null ? "reasonForSeperation=" + reasonForSeperation + ", " : "") +
            (seperationDate != null ? "seperationDate=" + seperationDate + ", " : "") +
            (comment != null ? "comment=" + comment + ", " : "") +
            (seperationStatus != null ? "seperationStatus=" + seperationStatus + ", " : "") +
            (otherReason != null ? "otherReason=" + otherReason + ", " : "") +
            (nagotiatedPeriod != null ? "nagotiatedPeriod=" + nagotiatedPeriod + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (freefield1 != null ? "freefield1=" + freefield1 + ", " : "") +
            (freefield2 != null ? "freefield2=" + freefield2 + ", " : "") +
            (freefield3 != null ? "freefield3=" + freefield3 + ", " : "") +
            (freefield4 != null ? "freefield4=" + freefield4 + ", " : "") +
            (freefield5 != null ? "freefield5=" + freefield5 + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
