package com.techvg.hrms.service.criteria;

import com.techvg.hrms.domain.enumeration.Title;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.Employee} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.EmployeeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Title
     */
    public static class TitleFilter extends Filter<Title> {

        public TitleFilter() {}

        public TitleFilter(TitleFilter filter) {
            super(filter);
        }

        @Override
        public TitleFilter copy() {
            return new TitleFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private TitleFilter title;

    private StringFilter firstName;

    private StringFilter middleName;

    private StringFilter lastName;

    private StringFilter grade;

    private StringFilter username;

    private StringFilter passwordHash;

    private StringFilter email;

    private StringFilter mobileNo;

    private StringFilter department;

    private StringFilter imageUrl;

    private BooleanFilter activated;

    private StringFilter langKey;

    private StringFilter activationKey;

    private StringFilter resetKey;

    private InstantFilter resetDate;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private LongFilter employeeDetailsId;

    private LongFilter organizationId;

    private LongFilter incomeTaxSlabId;

    private LongFilter securityPermissionId;

    private LongFilter securityRoleId;

    private LongFilter organizationPoliciesId;

    private Boolean distinct;

    public EmployeeCriteria() {}

    public EmployeeCriteria(EmployeeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.middleName = other.middleName == null ? null : other.middleName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.grade = other.grade == null ? null : other.grade.copy();
        this.username = other.username == null ? null : other.username.copy();
        this.passwordHash = other.passwordHash == null ? null : other.passwordHash.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.mobileNo = other.mobileNo == null ? null : other.mobileNo.copy();
        this.department = other.department == null ? null : other.department.copy();
        this.imageUrl = other.imageUrl == null ? null : other.imageUrl.copy();
        this.activated = other.activated == null ? null : other.activated.copy();
        this.langKey = other.langKey == null ? null : other.langKey.copy();
        this.activationKey = other.activationKey == null ? null : other.activationKey.copy();
        this.resetKey = other.resetKey == null ? null : other.resetKey.copy();
        this.resetDate = other.resetDate == null ? null : other.resetDate.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.employeeDetailsId = other.employeeDetailsId == null ? null : other.employeeDetailsId.copy();
        this.organizationId = other.organizationId == null ? null : other.organizationId.copy();
        this.incomeTaxSlabId = other.incomeTaxSlabId == null ? null : other.incomeTaxSlabId.copy();
        this.securityPermissionId = other.securityPermissionId == null ? null : other.securityPermissionId.copy();
        this.securityRoleId = other.securityRoleId == null ? null : other.securityRoleId.copy();
        this.organizationPoliciesId = other.organizationPoliciesId == null ? null : other.organizationPoliciesId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeCriteria copy() {
        return new EmployeeCriteria(this);
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

    public TitleFilter getTitle() {
        return title;
    }

    public TitleFilter title() {
        if (title == null) {
            title = new TitleFilter();
        }
        return title;
    }

    public void setTitle(TitleFilter title) {
        this.title = title;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public StringFilter firstName() {
        if (firstName == null) {
            firstName = new StringFilter();
        }
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getMiddleName() {
        return middleName;
    }

    public StringFilter middleName() {
        if (middleName == null) {
            middleName = new StringFilter();
        }
        return middleName;
    }

    public void setMiddleName(StringFilter middleName) {
        this.middleName = middleName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public StringFilter lastName() {
        if (lastName == null) {
            lastName = new StringFilter();
        }
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getGrade() {
        return grade;
    }

    public StringFilter grade() {
        if (grade == null) {
            grade = new StringFilter();
        }
        return grade;
    }

    public void setGrade(StringFilter grade) {
        this.grade = grade;
    }

    public StringFilter getUsername() {
        return username;
    }

    public StringFilter username() {
        if (username == null) {
            username = new StringFilter();
        }
        return username;
    }

    public void setUsername(StringFilter username) {
        this.username = username;
    }

    public StringFilter getPasswordHash() {
        return passwordHash;
    }

    public StringFilter passwordHash() {
        if (passwordHash == null) {
            passwordHash = new StringFilter();
        }
        return passwordHash;
    }

    public void setPasswordHash(StringFilter passwordHash) {
        this.passwordHash = passwordHash;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getMobileNo() {
        return mobileNo;
    }

    public StringFilter mobileNo() {
        if (mobileNo == null) {
            mobileNo = new StringFilter();
        }
        return mobileNo;
    }

    public void setMobileNo(StringFilter mobileNo) {
        this.mobileNo = mobileNo;
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

    public StringFilter getImageUrl() {
        return imageUrl;
    }

    public StringFilter imageUrl() {
        if (imageUrl == null) {
            imageUrl = new StringFilter();
        }
        return imageUrl;
    }

    public void setImageUrl(StringFilter imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BooleanFilter getActivated() {
        return activated;
    }

    public BooleanFilter activated() {
        if (activated == null) {
            activated = new BooleanFilter();
        }
        return activated;
    }

    public void setActivated(BooleanFilter activated) {
        this.activated = activated;
    }

    public StringFilter getLangKey() {
        return langKey;
    }

    public StringFilter langKey() {
        if (langKey == null) {
            langKey = new StringFilter();
        }
        return langKey;
    }

    public void setLangKey(StringFilter langKey) {
        this.langKey = langKey;
    }

    public StringFilter getActivationKey() {
        return activationKey;
    }

    public StringFilter activationKey() {
        if (activationKey == null) {
            activationKey = new StringFilter();
        }
        return activationKey;
    }

    public void setActivationKey(StringFilter activationKey) {
        this.activationKey = activationKey;
    }

    public StringFilter getResetKey() {
        return resetKey;
    }

    public StringFilter resetKey() {
        if (resetKey == null) {
            resetKey = new StringFilter();
        }
        return resetKey;
    }

    public void setResetKey(StringFilter resetKey) {
        this.resetKey = resetKey;
    }

    public InstantFilter getResetDate() {
        return resetDate;
    }

    public InstantFilter resetDate() {
        if (resetDate == null) {
            resetDate = new InstantFilter();
        }
        return resetDate;
    }

    public void setResetDate(InstantFilter resetDate) {
        this.resetDate = resetDate;
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

    public LongFilter getEmployeeDetailsId() {
        return employeeDetailsId;
    }

    public LongFilter employeeDetailsId() {
        if (employeeDetailsId == null) {
            employeeDetailsId = new LongFilter();
        }
        return employeeDetailsId;
    }

    public void setEmployeeDetailsId(LongFilter employeeDetailsId) {
        this.employeeDetailsId = employeeDetailsId;
    }

    public LongFilter getOrganizationId() {
        return organizationId;
    }

    public LongFilter organizationId() {
        if (organizationId == null) {
            organizationId = new LongFilter();
        }
        return organizationId;
    }

    public void setOrganizationId(LongFilter organizationId) {
        this.organizationId = organizationId;
    }

    public LongFilter getIncomeTaxSlabId() {
        return incomeTaxSlabId;
    }

    public LongFilter incomeTaxSlabId() {
        if (incomeTaxSlabId == null) {
            incomeTaxSlabId = new LongFilter();
        }
        return incomeTaxSlabId;
    }

    public void setIncomeTaxSlabId(LongFilter incomeTaxSlabId) {
        this.incomeTaxSlabId = incomeTaxSlabId;
    }

    public LongFilter getSecurityPermissionId() {
        return securityPermissionId;
    }

    public LongFilter securityPermissionId() {
        if (securityPermissionId == null) {
            securityPermissionId = new LongFilter();
        }
        return securityPermissionId;
    }

    public void setSecurityPermissionId(LongFilter securityPermissionId) {
        this.securityPermissionId = securityPermissionId;
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

    public LongFilter getOrganizationPoliciesId() {
        return organizationPoliciesId;
    }

    public LongFilter organizationPoliciesId() {
        if (organizationPoliciesId == null) {
            organizationPoliciesId = new LongFilter();
        }
        return organizationPoliciesId;
    }

    public void setOrganizationPoliciesId(LongFilter organizationPoliciesId) {
        this.organizationPoliciesId = organizationPoliciesId;
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
        final EmployeeCriteria that = (EmployeeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(middleName, that.middleName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(grade, that.grade) &&
            Objects.equals(username, that.username) &&
            Objects.equals(passwordHash, that.passwordHash) &&
            Objects.equals(email, that.email) &&
            Objects.equals(mobileNo, that.mobileNo) &&
            Objects.equals(department, that.department) &&
            Objects.equals(imageUrl, that.imageUrl) &&
            Objects.equals(activated, that.activated) &&
            Objects.equals(langKey, that.langKey) &&
            Objects.equals(activationKey, that.activationKey) &&
            Objects.equals(resetKey, that.resetKey) &&
            Objects.equals(resetDate, that.resetDate) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(employeeDetailsId, that.employeeDetailsId) &&
            Objects.equals(organizationId, that.organizationId) &&
            Objects.equals(incomeTaxSlabId, that.incomeTaxSlabId) &&
            Objects.equals(securityPermissionId, that.securityPermissionId) &&
            Objects.equals(securityRoleId, that.securityRoleId) &&
            Objects.equals(organizationPoliciesId, that.organizationPoliciesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            title,
            firstName,
            middleName,
            lastName,
            grade,
            username,
            passwordHash,
            email,
            mobileNo,
            department,
            imageUrl,
            activated,
            langKey,
            activationKey,
            resetKey,
            resetDate,
            createdBy,
            createdOn,
            employeeDetailsId,
            organizationId,
            incomeTaxSlabId,
            securityPermissionId,
            securityRoleId,
            organizationPoliciesId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (firstName != null ? "firstName=" + firstName + ", " : "") +
            (middleName != null ? "middleName=" + middleName + ", " : "") +
            (lastName != null ? "lastName=" + lastName + ", " : "") +
            (grade != null ? "grade=" + grade + ", " : "") +
            (username != null ? "username=" + username + ", " : "") +
            (passwordHash != null ? "passwordHash=" + passwordHash + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (mobileNo != null ? "mobileNo=" + mobileNo + ", " : "") +
            (department != null ? "department=" + department + ", " : "") +
            (imageUrl != null ? "imageUrl=" + imageUrl + ", " : "") +
            (activated != null ? "activated=" + activated + ", " : "") +
            (langKey != null ? "langKey=" + langKey + ", " : "") +
            (activationKey != null ? "activationKey=" + activationKey + ", " : "") +
            (resetKey != null ? "resetKey=" + resetKey + ", " : "") +
            (resetDate != null ? "resetDate=" + resetDate + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (employeeDetailsId != null ? "employeeDetailsId=" + employeeDetailsId + ", " : "") +
            (organizationId != null ? "organizationId=" + organizationId + ", " : "") +
            (incomeTaxSlabId != null ? "incomeTaxSlabId=" + incomeTaxSlabId + ", " : "") +
            (securityPermissionId != null ? "securityPermissionId=" + securityPermissionId + ", " : "") +
            (securityRoleId != null ? "securityRoleId=" + securityRoleId + ", " : "") +
            (organizationPoliciesId != null ? "organizationPoliciesId=" + organizationPoliciesId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
