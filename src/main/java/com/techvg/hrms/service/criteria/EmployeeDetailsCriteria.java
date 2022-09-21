package com.techvg.hrms.service.criteria;

import com.techvg.hrms.domain.enumeration.BloodGroup;
import com.techvg.hrms.domain.enumeration.MaritalStatus;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.EmployeeDetails} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.EmployeeDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeDetailsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering BloodGroup
     */
    public static class BloodGroupFilter extends Filter<BloodGroup> {

        public BloodGroupFilter() {}

        public BloodGroupFilter(BloodGroupFilter filter) {
            super(filter);
        }

        @Override
        public BloodGroupFilter copy() {
            return new BloodGroupFilter(this);
        }
    }

    /**
     * Class for filtering MaritalStatus
     */
    public static class MaritalStatusFilter extends Filter<MaritalStatus> {

        public MaritalStatusFilter() {}

        public MaritalStatusFilter(MaritalStatusFilter filter) {
            super(filter);
        }

        @Override
        public MaritalStatusFilter copy() {
            return new MaritalStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter age;

    private StringFilter fatherName;

    private StringFilter motherName;

    private StringFilter employeeId;

    private DoubleFilter yearsOfExperience;

    private StringFilter notes;

    private BloodGroupFilter bloodGroup;

    private StringFilter birthDate;

    private StringFilter designation;

    private StringFilter expertise;

    private StringFilter jobDescription;

    private MaritalStatusFilter maritalStatus;

    private StringFilter secondaryContact;

    private StringFilter hobbies;

    private StringFilter areaInterest;

    private LongFilter noOfDependent;

    private StringFilter languageKnown;

    private StringFilter natinality;

    private StringFilter description;

    private StringFilter department;

    private InstantFilter joiningDate;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private StringFilter freefield1;

    private StringFilter freefield2;

    private StringFilter freefield3;

    private StringFilter freefield4;

    private StringFilter freefield5;

    private Boolean distinct;

    public EmployeeDetailsCriteria() {}

    public EmployeeDetailsCriteria(EmployeeDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.age = other.age == null ? null : other.age.copy();
        this.fatherName = other.fatherName == null ? null : other.fatherName.copy();
        this.motherName = other.motherName == null ? null : other.motherName.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.yearsOfExperience = other.yearsOfExperience == null ? null : other.yearsOfExperience.copy();
        this.notes = other.notes == null ? null : other.notes.copy();
        this.bloodGroup = other.bloodGroup == null ? null : other.bloodGroup.copy();
        this.birthDate = other.birthDate == null ? null : other.birthDate.copy();
        this.designation = other.designation == null ? null : other.designation.copy();
        this.expertise = other.expertise == null ? null : other.expertise.copy();
        this.jobDescription = other.jobDescription == null ? null : other.jobDescription.copy();
        this.maritalStatus = other.maritalStatus == null ? null : other.maritalStatus.copy();
        this.secondaryContact = other.secondaryContact == null ? null : other.secondaryContact.copy();
        this.hobbies = other.hobbies == null ? null : other.hobbies.copy();
        this.areaInterest = other.areaInterest == null ? null : other.areaInterest.copy();
        this.noOfDependent = other.noOfDependent == null ? null : other.noOfDependent.copy();
        this.languageKnown = other.languageKnown == null ? null : other.languageKnown.copy();
        this.natinality = other.natinality == null ? null : other.natinality.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.department = other.department == null ? null : other.department.copy();
        this.joiningDate = other.joiningDate == null ? null : other.joiningDate.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.freefield1 = other.freefield1 == null ? null : other.freefield1.copy();
        this.freefield2 = other.freefield2 == null ? null : other.freefield2.copy();
        this.freefield3 = other.freefield3 == null ? null : other.freefield3.copy();
        this.freefield4 = other.freefield4 == null ? null : other.freefield4.copy();
        this.freefield5 = other.freefield5 == null ? null : other.freefield5.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeDetailsCriteria copy() {
        return new EmployeeDetailsCriteria(this);
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

    public LongFilter getAge() {
        return age;
    }

    public LongFilter age() {
        if (age == null) {
            age = new LongFilter();
        }
        return age;
    }

    public void setAge(LongFilter age) {
        this.age = age;
    }

    public StringFilter getFatherName() {
        return fatherName;
    }

    public StringFilter fatherName() {
        if (fatherName == null) {
            fatherName = new StringFilter();
        }
        return fatherName;
    }

    public void setFatherName(StringFilter fatherName) {
        this.fatherName = fatherName;
    }

    public StringFilter getMotherName() {
        return motherName;
    }

    public StringFilter motherName() {
        if (motherName == null) {
            motherName = new StringFilter();
        }
        return motherName;
    }

    public void setMotherName(StringFilter motherName) {
        this.motherName = motherName;
    }

    public StringFilter getEmployeeId() {
        return employeeId;
    }

    public StringFilter employeeId() {
        if (employeeId == null) {
            employeeId = new StringFilter();
        }
        return employeeId;
    }

    public void setEmployeeId(StringFilter employeeId) {
        this.employeeId = employeeId;
    }

    public DoubleFilter getYearsOfExperience() {
        return yearsOfExperience;
    }

    public DoubleFilter yearsOfExperience() {
        if (yearsOfExperience == null) {
            yearsOfExperience = new DoubleFilter();
        }
        return yearsOfExperience;
    }

    public void setYearsOfExperience(DoubleFilter yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public StringFilter getNotes() {
        return notes;
    }

    public StringFilter notes() {
        if (notes == null) {
            notes = new StringFilter();
        }
        return notes;
    }

    public void setNotes(StringFilter notes) {
        this.notes = notes;
    }

    public BloodGroupFilter getBloodGroup() {
        return bloodGroup;
    }

    public BloodGroupFilter bloodGroup() {
        if (bloodGroup == null) {
            bloodGroup = new BloodGroupFilter();
        }
        return bloodGroup;
    }

    public void setBloodGroup(BloodGroupFilter bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public StringFilter getBirthDate() {
        return birthDate;
    }

    public StringFilter birthDate() {
        if (birthDate == null) {
            birthDate = new StringFilter();
        }
        return birthDate;
    }

    public void setBirthDate(StringFilter birthDate) {
        this.birthDate = birthDate;
    }

    public StringFilter getDesignation() {
        return designation;
    }

    public StringFilter designation() {
        if (designation == null) {
            designation = new StringFilter();
        }
        return designation;
    }

    public void setDesignation(StringFilter designation) {
        this.designation = designation;
    }

    public StringFilter getExpertise() {
        return expertise;
    }

    public StringFilter expertise() {
        if (expertise == null) {
            expertise = new StringFilter();
        }
        return expertise;
    }

    public void setExpertise(StringFilter expertise) {
        this.expertise = expertise;
    }

    public StringFilter getJobDescription() {
        return jobDescription;
    }

    public StringFilter jobDescription() {
        if (jobDescription == null) {
            jobDescription = new StringFilter();
        }
        return jobDescription;
    }

    public void setJobDescription(StringFilter jobDescription) {
        this.jobDescription = jobDescription;
    }

    public MaritalStatusFilter getMaritalStatus() {
        return maritalStatus;
    }

    public MaritalStatusFilter maritalStatus() {
        if (maritalStatus == null) {
            maritalStatus = new MaritalStatusFilter();
        }
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatusFilter maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public StringFilter getSecondaryContact() {
        return secondaryContact;
    }

    public StringFilter secondaryContact() {
        if (secondaryContact == null) {
            secondaryContact = new StringFilter();
        }
        return secondaryContact;
    }

    public void setSecondaryContact(StringFilter secondaryContact) {
        this.secondaryContact = secondaryContact;
    }

    public StringFilter getHobbies() {
        return hobbies;
    }

    public StringFilter hobbies() {
        if (hobbies == null) {
            hobbies = new StringFilter();
        }
        return hobbies;
    }

    public void setHobbies(StringFilter hobbies) {
        this.hobbies = hobbies;
    }

    public StringFilter getAreaInterest() {
        return areaInterest;
    }

    public StringFilter areaInterest() {
        if (areaInterest == null) {
            areaInterest = new StringFilter();
        }
        return areaInterest;
    }

    public void setAreaInterest(StringFilter areaInterest) {
        this.areaInterest = areaInterest;
    }

    public LongFilter getNoOfDependent() {
        return noOfDependent;
    }

    public LongFilter noOfDependent() {
        if (noOfDependent == null) {
            noOfDependent = new LongFilter();
        }
        return noOfDependent;
    }

    public void setNoOfDependent(LongFilter noOfDependent) {
        this.noOfDependent = noOfDependent;
    }

    public StringFilter getLanguageKnown() {
        return languageKnown;
    }

    public StringFilter languageKnown() {
        if (languageKnown == null) {
            languageKnown = new StringFilter();
        }
        return languageKnown;
    }

    public void setLanguageKnown(StringFilter languageKnown) {
        this.languageKnown = languageKnown;
    }

    public StringFilter getNatinality() {
        return natinality;
    }

    public StringFilter natinality() {
        if (natinality == null) {
            natinality = new StringFilter();
        }
        return natinality;
    }

    public void setNatinality(StringFilter natinality) {
        this.natinality = natinality;
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

    public StringFilter getDepartment() {
        return department;
    }

    public StringFilter department() {
        if (department == null) {
            department = new StringFilter();
        }
        return department;
    }

    public void setDepartment(StringFilter department) {
        this.department = department;
    }

    public InstantFilter getJoiningDate() {
        return joiningDate;
    }

    public InstantFilter joiningDate() {
        if (joiningDate == null) {
            joiningDate = new InstantFilter();
        }
        return joiningDate;
    }

    public void setJoiningDate(InstantFilter joiningDate) {
        this.joiningDate = joiningDate;
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
        final EmployeeDetailsCriteria that = (EmployeeDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(age, that.age) &&
            Objects.equals(fatherName, that.fatherName) &&
            Objects.equals(motherName, that.motherName) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(yearsOfExperience, that.yearsOfExperience) &&
            Objects.equals(notes, that.notes) &&
            Objects.equals(bloodGroup, that.bloodGroup) &&
            Objects.equals(birthDate, that.birthDate) &&
            Objects.equals(designation, that.designation) &&
            Objects.equals(expertise, that.expertise) &&
            Objects.equals(jobDescription, that.jobDescription) &&
            Objects.equals(maritalStatus, that.maritalStatus) &&
            Objects.equals(secondaryContact, that.secondaryContact) &&
            Objects.equals(hobbies, that.hobbies) &&
            Objects.equals(areaInterest, that.areaInterest) &&
            Objects.equals(noOfDependent, that.noOfDependent) &&
            Objects.equals(languageKnown, that.languageKnown) &&
            Objects.equals(natinality, that.natinality) &&
            Objects.equals(description, that.description) &&
            Objects.equals(department, that.department) &&
            Objects.equals(joiningDate, that.joiningDate) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(freefield1, that.freefield1) &&
            Objects.equals(freefield2, that.freefield2) &&
            Objects.equals(freefield3, that.freefield3) &&
            Objects.equals(freefield4, that.freefield4) &&
            Objects.equals(freefield5, that.freefield5) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            age,
            fatherName,
            motherName,
            employeeId,
            yearsOfExperience,
            notes,
            bloodGroup,
            birthDate,
            designation,
            expertise,
            jobDescription,
            maritalStatus,
            secondaryContact,
            hobbies,
            areaInterest,
            noOfDependent,
            languageKnown,
            natinality,
            description,
            department,
            joiningDate,
            createdBy,
            createdOn,
            freefield1,
            freefield2,
            freefield3,
            freefield4,
            freefield5,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (age != null ? "age=" + age + ", " : "") +
            (fatherName != null ? "fatherName=" + fatherName + ", " : "") +
            (motherName != null ? "motherName=" + motherName + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (yearsOfExperience != null ? "yearsOfExperience=" + yearsOfExperience + ", " : "") +
            (notes != null ? "notes=" + notes + ", " : "") +
            (bloodGroup != null ? "bloodGroup=" + bloodGroup + ", " : "") +
            (birthDate != null ? "birthDate=" + birthDate + ", " : "") +
            (designation != null ? "designation=" + designation + ", " : "") +
            (expertise != null ? "expertise=" + expertise + ", " : "") +
            (jobDescription != null ? "jobDescription=" + jobDescription + ", " : "") +
            (maritalStatus != null ? "maritalStatus=" + maritalStatus + ", " : "") +
            (secondaryContact != null ? "secondaryContact=" + secondaryContact + ", " : "") +
            (hobbies != null ? "hobbies=" + hobbies + ", " : "") +
            (areaInterest != null ? "areaInterest=" + areaInterest + ", " : "") +
            (noOfDependent != null ? "noOfDependent=" + noOfDependent + ", " : "") +
            (languageKnown != null ? "languageKnown=" + languageKnown + ", " : "") +
            (natinality != null ? "natinality=" + natinality + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (department != null ? "department=" + department + ", " : "") +
            (joiningDate != null ? "joiningDate=" + joiningDate + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (freefield1 != null ? "freefield1=" + freefield1 + ", " : "") +
            (freefield2 != null ? "freefield2=" + freefield2 + ", " : "") +
            (freefield3 != null ? "freefield3=" + freefield3 + ", " : "") +
            (freefield4 != null ? "freefield4=" + freefield4 + ", " : "") +
            (freefield5 != null ? "freefield5=" + freefield5 + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
