package com.techvg.hrms.service.criteria;

import com.techvg.hrms.domain.enumeration.Answers;
import com.techvg.hrms.domain.enumeration.Answers;
import com.techvg.hrms.domain.enumeration.Answers;
import com.techvg.hrms.domain.enumeration.Answers;
import com.techvg.hrms.domain.enumeration.Answers;
import com.techvg.hrms.domain.enumeration.Answers;
import com.techvg.hrms.domain.enumeration.Answers;
import com.techvg.hrms.domain.enumeration.Answers;
import com.techvg.hrms.domain.enumeration.Answers;
import com.techvg.hrms.domain.enumeration.Status;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.ExitFormalties} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.ExitFormaltiesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /exit-formalties?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ExitFormaltiesCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Answers
     */
    public static class AnswersFilter extends Filter<Answers> {

        public AnswersFilter() {}

        public AnswersFilter(AnswersFilter filter) {
            super(filter);
        }

        @Override
        public AnswersFilter copy() {
            return new AnswersFilter(this);
        }
    }

    /**
     * Class for filtering Status
     */
    public static class StatusFilter extends Filter<Status> {

        public StatusFilter() {}

        public StatusFilter(StatusFilter filter) {
            super(filter);
        }

        @Override
        public StatusFilter copy() {
            return new StatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private AnswersFilter security;

    private StringFilter feedback;

    private InstantFilter exitDate;

    private AnswersFilter exitInterview;

    private AnswersFilter libNoDue;

    private AnswersFilter noticePeriodServed;

    private AnswersFilter clearence;

    private AnswersFilter orgAssets;

    private AnswersFilter orgVehical;

    private AnswersFilter resignLetter;

    private StringFilter shares;

    private StringFilter staffWelfare;

    private AnswersFilter workForOrg;

    private StatusFilter status;

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

    public ExitFormaltiesCriteria() {}

    public ExitFormaltiesCriteria(ExitFormaltiesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.security = other.security == null ? null : other.security.copy();
        this.feedback = other.feedback == null ? null : other.feedback.copy();
        this.exitDate = other.exitDate == null ? null : other.exitDate.copy();
        this.exitInterview = other.exitInterview == null ? null : other.exitInterview.copy();
        this.libNoDue = other.libNoDue == null ? null : other.libNoDue.copy();
        this.noticePeriodServed = other.noticePeriodServed == null ? null : other.noticePeriodServed.copy();
        this.clearence = other.clearence == null ? null : other.clearence.copy();
        this.orgAssets = other.orgAssets == null ? null : other.orgAssets.copy();
        this.orgVehical = other.orgVehical == null ? null : other.orgVehical.copy();
        this.resignLetter = other.resignLetter == null ? null : other.resignLetter.copy();
        this.shares = other.shares == null ? null : other.shares.copy();
        this.staffWelfare = other.staffWelfare == null ? null : other.staffWelfare.copy();
        this.workForOrg = other.workForOrg == null ? null : other.workForOrg.copy();
        this.status = other.status == null ? null : other.status.copy();
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
    public ExitFormaltiesCriteria copy() {
        return new ExitFormaltiesCriteria(this);
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

    public AnswersFilter getSecurity() {
        return security;
    }

    public AnswersFilter security() {
        if (security == null) {
            security = new AnswersFilter();
        }
        return security;
    }

    public void setSecurity(AnswersFilter security) {
        this.security = security;
    }

    public StringFilter getFeedback() {
        return feedback;
    }

    public StringFilter feedback() {
        if (feedback == null) {
            feedback = new StringFilter();
        }
        return feedback;
    }

    public void setFeedback(StringFilter feedback) {
        this.feedback = feedback;
    }

    public InstantFilter getExitDate() {
        return exitDate;
    }

    public InstantFilter exitDate() {
        if (exitDate == null) {
            exitDate = new InstantFilter();
        }
        return exitDate;
    }

    public void setExitDate(InstantFilter exitDate) {
        this.exitDate = exitDate;
    }

    public AnswersFilter getExitInterview() {
        return exitInterview;
    }

    public AnswersFilter exitInterview() {
        if (exitInterview == null) {
            exitInterview = new AnswersFilter();
        }
        return exitInterview;
    }

    public void setExitInterview(AnswersFilter exitInterview) {
        this.exitInterview = exitInterview;
    }

    public AnswersFilter getLibNoDue() {
        return libNoDue;
    }

    public AnswersFilter libNoDue() {
        if (libNoDue == null) {
            libNoDue = new AnswersFilter();
        }
        return libNoDue;
    }

    public void setLibNoDue(AnswersFilter libNoDue) {
        this.libNoDue = libNoDue;
    }

    public AnswersFilter getNoticePeriodServed() {
        return noticePeriodServed;
    }

    public AnswersFilter noticePeriodServed() {
        if (noticePeriodServed == null) {
            noticePeriodServed = new AnswersFilter();
        }
        return noticePeriodServed;
    }

    public void setNoticePeriodServed(AnswersFilter noticePeriodServed) {
        this.noticePeriodServed = noticePeriodServed;
    }

    public AnswersFilter getClearence() {
        return clearence;
    }

    public AnswersFilter clearence() {
        if (clearence == null) {
            clearence = new AnswersFilter();
        }
        return clearence;
    }

    public void setClearence(AnswersFilter clearence) {
        this.clearence = clearence;
    }

    public AnswersFilter getOrgAssets() {
        return orgAssets;
    }

    public AnswersFilter orgAssets() {
        if (orgAssets == null) {
            orgAssets = new AnswersFilter();
        }
        return orgAssets;
    }

    public void setOrgAssets(AnswersFilter orgAssets) {
        this.orgAssets = orgAssets;
    }

    public AnswersFilter getOrgVehical() {
        return orgVehical;
    }

    public AnswersFilter orgVehical() {
        if (orgVehical == null) {
            orgVehical = new AnswersFilter();
        }
        return orgVehical;
    }

    public void setOrgVehical(AnswersFilter orgVehical) {
        this.orgVehical = orgVehical;
    }

    public AnswersFilter getResignLetter() {
        return resignLetter;
    }

    public AnswersFilter resignLetter() {
        if (resignLetter == null) {
            resignLetter = new AnswersFilter();
        }
        return resignLetter;
    }

    public void setResignLetter(AnswersFilter resignLetter) {
        this.resignLetter = resignLetter;
    }

    public StringFilter getShares() {
        return shares;
    }

    public StringFilter shares() {
        if (shares == null) {
            shares = new StringFilter();
        }
        return shares;
    }

    public void setShares(StringFilter shares) {
        this.shares = shares;
    }

    public StringFilter getStaffWelfare() {
        return staffWelfare;
    }

    public StringFilter staffWelfare() {
        if (staffWelfare == null) {
            staffWelfare = new StringFilter();
        }
        return staffWelfare;
    }

    public void setStaffWelfare(StringFilter staffWelfare) {
        this.staffWelfare = staffWelfare;
    }

    public AnswersFilter getWorkForOrg() {
        return workForOrg;
    }

    public AnswersFilter workForOrg() {
        if (workForOrg == null) {
            workForOrg = new AnswersFilter();
        }
        return workForOrg;
    }

    public void setWorkForOrg(AnswersFilter workForOrg) {
        this.workForOrg = workForOrg;
    }

    public StatusFilter getStatus() {
        return status;
    }

    public StatusFilter status() {
        if (status == null) {
            status = new StatusFilter();
        }
        return status;
    }

    public void setStatus(StatusFilter status) {
        this.status = status;
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
        final ExitFormaltiesCriteria that = (ExitFormaltiesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(security, that.security) &&
            Objects.equals(feedback, that.feedback) &&
            Objects.equals(exitDate, that.exitDate) &&
            Objects.equals(exitInterview, that.exitInterview) &&
            Objects.equals(libNoDue, that.libNoDue) &&
            Objects.equals(noticePeriodServed, that.noticePeriodServed) &&
            Objects.equals(clearence, that.clearence) &&
            Objects.equals(orgAssets, that.orgAssets) &&
            Objects.equals(orgVehical, that.orgVehical) &&
            Objects.equals(resignLetter, that.resignLetter) &&
            Objects.equals(shares, that.shares) &&
            Objects.equals(staffWelfare, that.staffWelfare) &&
            Objects.equals(workForOrg, that.workForOrg) &&
            Objects.equals(status, that.status) &&
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
            security,
            feedback,
            exitDate,
            exitInterview,
            libNoDue,
            noticePeriodServed,
            clearence,
            orgAssets,
            orgVehical,
            resignLetter,
            shares,
            staffWelfare,
            workForOrg,
            status,
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
        return "ExitFormaltiesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (security != null ? "security=" + security + ", " : "") +
            (feedback != null ? "feedback=" + feedback + ", " : "") +
            (exitDate != null ? "exitDate=" + exitDate + ", " : "") +
            (exitInterview != null ? "exitInterview=" + exitInterview + ", " : "") +
            (libNoDue != null ? "libNoDue=" + libNoDue + ", " : "") +
            (noticePeriodServed != null ? "noticePeriodServed=" + noticePeriodServed + ", " : "") +
            (clearence != null ? "clearence=" + clearence + ", " : "") +
            (orgAssets != null ? "orgAssets=" + orgAssets + ", " : "") +
            (orgVehical != null ? "orgVehical=" + orgVehical + ", " : "") +
            (resignLetter != null ? "resignLetter=" + resignLetter + ", " : "") +
            (shares != null ? "shares=" + shares + ", " : "") +
            (staffWelfare != null ? "staffWelfare=" + staffWelfare + ", " : "") +
            (workForOrg != null ? "workForOrg=" + workForOrg + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
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
