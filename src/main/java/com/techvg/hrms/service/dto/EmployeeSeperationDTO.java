package com.techvg.hrms.service.dto;

import com.techvg.hrms.domain.enumeration.SeperationStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.hrms.domain.EmployeeSeperation} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeSeperationDTO implements Serializable {

    private Long id;

    private String reasonForSeperation;

    private Instant seperationDate;

    private String comment;

    private SeperationStatus seperationStatus;

    private String otherReason;

    private Long nagotiatedPeriod;

    private String createdBy;

    private Instant updatedOn;

    private Instant createdOn;

    private Instant lastModified;

    private String lastModifiedBy;

    private String freefield1;

    private String freefield2;

    private String freefield3;

    private String freefield4;

    private String freefield5;

    private EmployeeDTO employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReasonForSeperation() {
        return reasonForSeperation;
    }

    public void setReasonForSeperation(String reasonForSeperation) {
        this.reasonForSeperation = reasonForSeperation;
    }

    public Instant getSeperationDate() {
        return seperationDate;
    }

    public void setSeperationDate(Instant seperationDate) {
        this.seperationDate = seperationDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public SeperationStatus getSeperationStatus() {
        return seperationStatus;
    }

    public void setSeperationStatus(SeperationStatus seperationStatus) {
        this.seperationStatus = seperationStatus;
    }

    public String getOtherReason() {
        return otherReason;
    }

    public void setOtherReason(String otherReason) {
        this.otherReason = otherReason;
    }

    public Long getNagotiatedPeriod() {
        return nagotiatedPeriod;
    }

    public void setNagotiatedPeriod(Long nagotiatedPeriod) {
        this.nagotiatedPeriod = nagotiatedPeriod;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getFreefield1() {
        return freefield1;
    }

    public void setFreefield1(String freefield1) {
        this.freefield1 = freefield1;
    }

    public String getFreefield2() {
        return freefield2;
    }

    public void setFreefield2(String freefield2) {
        this.freefield2 = freefield2;
    }

    public String getFreefield3() {
        return freefield3;
    }

    public void setFreefield3(String freefield3) {
        this.freefield3 = freefield3;
    }

    public String getFreefield4() {
        return freefield4;
    }

    public void setFreefield4(String freefield4) {
        this.freefield4 = freefield4;
    }

    public String getFreefield5() {
        return freefield5;
    }

    public void setFreefield5(String freefield5) {
        this.freefield5 = freefield5;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeSeperationDTO)) {
            return false;
        }

        EmployeeSeperationDTO employeeSeperationDTO = (EmployeeSeperationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, employeeSeperationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeSeperationDTO{" +
            "id=" + getId() +
            ", reasonForSeperation='" + getReasonForSeperation() + "'" +
            ", seperationDate='" + getSeperationDate() + "'" +
            ", comment='" + getComment() + "'" +
            ", seperationStatus='" + getSeperationStatus() + "'" +
            ", otherReason='" + getOtherReason() + "'" +
            ", nagotiatedPeriod=" + getNagotiatedPeriod() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", freefield1='" + getFreefield1() + "'" +
            ", freefield2='" + getFreefield2() + "'" +
            ", freefield3='" + getFreefield3() + "'" +
            ", freefield4='" + getFreefield4() + "'" +
            ", freefield5='" + getFreefield5() + "'" +
            ", employee=" + getEmployee() +
            "}";
    }
}
