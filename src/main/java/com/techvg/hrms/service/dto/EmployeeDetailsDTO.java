package com.techvg.hrms.service.dto;

import com.techvg.hrms.domain.enumeration.BloodGroup;
import com.techvg.hrms.domain.enumeration.MaritalStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.hrms.domain.EmployeeDetails} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeDetailsDTO implements Serializable {

    private Long id;

    private Long age;

    private String fatherName;

    private String motherName;

    private String employeeId;

    private Double yearsOfExperience;

    private String notes;

    private BloodGroup bloodGroup;

    private String birthDate;

    private String designation;

    private String expertise;

    private String jobDescription;

    private MaritalStatus maritalStatus;

    private String secondaryContact;

    private String hobbies;

    private String areaInterest;

    private Long noOfDependent;

    private String languageKnown;

    private String natinality;

    private String description;

    private String department;

    private Instant joiningDate;

    private String createdBy;

    private Instant createdOn;

    private String freefield1;

    private String freefield2;

    private String freefield3;

    private String freefield4;

    private String freefield5;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Double getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(Double yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public BloodGroup getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getSecondaryContact() {
        return secondaryContact;
    }

    public void setSecondaryContact(String secondaryContact) {
        this.secondaryContact = secondaryContact;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getAreaInterest() {
        return areaInterest;
    }

    public void setAreaInterest(String areaInterest) {
        this.areaInterest = areaInterest;
    }

    public Long getNoOfDependent() {
        return noOfDependent;
    }

    public void setNoOfDependent(Long noOfDependent) {
        this.noOfDependent = noOfDependent;
    }

    public String getLanguageKnown() {
        return languageKnown;
    }

    public void setLanguageKnown(String languageKnown) {
        this.languageKnown = languageKnown;
    }

    public String getNatinality() {
        return natinality;
    }

    public void setNatinality(String natinality) {
        this.natinality = natinality;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Instant getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Instant joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeDetailsDTO)) {
            return false;
        }

        EmployeeDetailsDTO employeeDetailsDTO = (EmployeeDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, employeeDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeDetailsDTO{" +
            "id=" + getId() +
            ", age=" + getAge() +
            ", fatherName='" + getFatherName() + "'" +
            ", motherName='" + getMotherName() + "'" +
            ", employeeId='" + getEmployeeId() + "'" +
            ", yearsOfExperience=" + getYearsOfExperience() +
            ", notes='" + getNotes() + "'" +
            ", bloodGroup='" + getBloodGroup() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", designation='" + getDesignation() + "'" +
            ", expertise='" + getExpertise() + "'" +
            ", jobDescription='" + getJobDescription() + "'" +
            ", maritalStatus='" + getMaritalStatus() + "'" +
            ", secondaryContact='" + getSecondaryContact() + "'" +
            ", hobbies='" + getHobbies() + "'" +
            ", areaInterest='" + getAreaInterest() + "'" +
            ", noOfDependent=" + getNoOfDependent() +
            ", languageKnown='" + getLanguageKnown() + "'" +
            ", natinality='" + getNatinality() + "'" +
            ", description='" + getDescription() + "'" +
            ", department='" + getDepartment() + "'" +
            ", joiningDate='" + getJoiningDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", freefield1='" + getFreefield1() + "'" +
            ", freefield2='" + getFreefield2() + "'" +
            ", freefield3='" + getFreefield3() + "'" +
            ", freefield4='" + getFreefield4() + "'" +
            ", freefield5='" + getFreefield5() + "'" +
            "}";
    }
}
