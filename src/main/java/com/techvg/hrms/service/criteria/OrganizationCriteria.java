package com.techvg.hrms.service.criteria;

import com.techvg.hrms.domain.enumeration.OrganizationType;
import com.techvg.hrms.domain.enumeration.Status;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.Organization} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.OrganizationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /organizations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganizationCriteria implements Serializable, Criteria {

    /**
     * Class for filtering OrganizationType
     */
    public static class OrganizationTypeFilter extends Filter<OrganizationType> {

        public OrganizationTypeFilter() {}

        public OrganizationTypeFilter(OrganizationTypeFilter filter) {
            super(filter);
        }

        @Override
        public OrganizationTypeFilter copy() {
            return new OrganizationTypeFilter(this);
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

    private StringFilter orgName;

    private StringFilter branchcode;

    private StringFilter region;

    private StringFilter ifscCode;

    private StringFilter micrCode;

    private StringFilter swiftCode;

    private StringFilter ibanCode;

    private StringFilter routingTransitNo;

    private DoubleFilter regNumber;

    private StringFilter gstinNumber;

    private StringFilter panNumber;

    private StringFilter tanNumber;

    private StringFilter phone;

    private StringFilter email;

    private StringFilter webSite;

    private StringFilter fax;

    private OrganizationTypeFilter orgType;

    private InstantFilter createdOn;

    private StringFilter createdBy;

    private StringFilter description;

    private StringFilter isDeleted;

    private StatusFilter status;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private StringFilter freeField4;

    private LongFilter addressId;

    private LongFilter organizationId;

    private Boolean distinct;

    public OrganizationCriteria() {}

    public OrganizationCriteria(OrganizationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.orgName = other.orgName == null ? null : other.orgName.copy();
        this.branchcode = other.branchcode == null ? null : other.branchcode.copy();
        this.region = other.region == null ? null : other.region.copy();
        this.ifscCode = other.ifscCode == null ? null : other.ifscCode.copy();
        this.micrCode = other.micrCode == null ? null : other.micrCode.copy();
        this.swiftCode = other.swiftCode == null ? null : other.swiftCode.copy();
        this.ibanCode = other.ibanCode == null ? null : other.ibanCode.copy();
        this.routingTransitNo = other.routingTransitNo == null ? null : other.routingTransitNo.copy();
        this.regNumber = other.regNumber == null ? null : other.regNumber.copy();
        this.gstinNumber = other.gstinNumber == null ? null : other.gstinNumber.copy();
        this.panNumber = other.panNumber == null ? null : other.panNumber.copy();
        this.tanNumber = other.tanNumber == null ? null : other.tanNumber.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.webSite = other.webSite == null ? null : other.webSite.copy();
        this.fax = other.fax == null ? null : other.fax.copy();
        this.orgType = other.orgType == null ? null : other.orgType.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.freeField4 = other.freeField4 == null ? null : other.freeField4.copy();
        this.addressId = other.addressId == null ? null : other.addressId.copy();
        this.organizationId = other.organizationId == null ? null : other.organizationId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public OrganizationCriteria copy() {
        return new OrganizationCriteria(this);
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

    public StringFilter getOrgName() {
        return orgName;
    }

    public StringFilter orgName() {
        if (orgName == null) {
            orgName = new StringFilter();
        }
        return orgName;
    }

    public void setOrgName(StringFilter orgName) {
        this.orgName = orgName;
    }

    public StringFilter getBranchcode() {
        return branchcode;
    }

    public StringFilter branchcode() {
        if (branchcode == null) {
            branchcode = new StringFilter();
        }
        return branchcode;
    }

    public void setBranchcode(StringFilter branchcode) {
        this.branchcode = branchcode;
    }

    public StringFilter getRegion() {
        return region;
    }

    public StringFilter region() {
        if (region == null) {
            region = new StringFilter();
        }
        return region;
    }

    public void setRegion(StringFilter region) {
        this.region = region;
    }

    public StringFilter getIfscCode() {
        return ifscCode;
    }

    public StringFilter ifscCode() {
        if (ifscCode == null) {
            ifscCode = new StringFilter();
        }
        return ifscCode;
    }

    public void setIfscCode(StringFilter ifscCode) {
        this.ifscCode = ifscCode;
    }

    public StringFilter getMicrCode() {
        return micrCode;
    }

    public StringFilter micrCode() {
        if (micrCode == null) {
            micrCode = new StringFilter();
        }
        return micrCode;
    }

    public void setMicrCode(StringFilter micrCode) {
        this.micrCode = micrCode;
    }

    public StringFilter getSwiftCode() {
        return swiftCode;
    }

    public StringFilter swiftCode() {
        if (swiftCode == null) {
            swiftCode = new StringFilter();
        }
        return swiftCode;
    }

    public void setSwiftCode(StringFilter swiftCode) {
        this.swiftCode = swiftCode;
    }

    public StringFilter getIbanCode() {
        return ibanCode;
    }

    public StringFilter ibanCode() {
        if (ibanCode == null) {
            ibanCode = new StringFilter();
        }
        return ibanCode;
    }

    public void setIbanCode(StringFilter ibanCode) {
        this.ibanCode = ibanCode;
    }

    public StringFilter getRoutingTransitNo() {
        return routingTransitNo;
    }

    public StringFilter routingTransitNo() {
        if (routingTransitNo == null) {
            routingTransitNo = new StringFilter();
        }
        return routingTransitNo;
    }

    public void setRoutingTransitNo(StringFilter routingTransitNo) {
        this.routingTransitNo = routingTransitNo;
    }

    public DoubleFilter getRegNumber() {
        return regNumber;
    }

    public DoubleFilter regNumber() {
        if (regNumber == null) {
            regNumber = new DoubleFilter();
        }
        return regNumber;
    }

    public void setRegNumber(DoubleFilter regNumber) {
        this.regNumber = regNumber;
    }

    public StringFilter getGstinNumber() {
        return gstinNumber;
    }

    public StringFilter gstinNumber() {
        if (gstinNumber == null) {
            gstinNumber = new StringFilter();
        }
        return gstinNumber;
    }

    public void setGstinNumber(StringFilter gstinNumber) {
        this.gstinNumber = gstinNumber;
    }

    public StringFilter getPanNumber() {
        return panNumber;
    }

    public StringFilter panNumber() {
        if (panNumber == null) {
            panNumber = new StringFilter();
        }
        return panNumber;
    }

    public void setPanNumber(StringFilter panNumber) {
        this.panNumber = panNumber;
    }

    public StringFilter getTanNumber() {
        return tanNumber;
    }

    public StringFilter tanNumber() {
        if (tanNumber == null) {
            tanNumber = new StringFilter();
        }
        return tanNumber;
    }

    public void setTanNumber(StringFilter tanNumber) {
        this.tanNumber = tanNumber;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public StringFilter phone() {
        if (phone == null) {
            phone = new StringFilter();
        }
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
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

    public StringFilter getWebSite() {
        return webSite;
    }

    public StringFilter webSite() {
        if (webSite == null) {
            webSite = new StringFilter();
        }
        return webSite;
    }

    public void setWebSite(StringFilter webSite) {
        this.webSite = webSite;
    }

    public StringFilter getFax() {
        return fax;
    }

    public StringFilter fax() {
        if (fax == null) {
            fax = new StringFilter();
        }
        return fax;
    }

    public void setFax(StringFilter fax) {
        this.fax = fax;
    }

    public OrganizationTypeFilter getOrgType() {
        return orgType;
    }

    public OrganizationTypeFilter orgType() {
        if (orgType == null) {
            orgType = new OrganizationTypeFilter();
        }
        return orgType;
    }

    public void setOrgType(OrganizationTypeFilter orgType) {
        this.orgType = orgType;
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

    public StringFilter getIsDeleted() {
        return isDeleted;
    }

    public StringFilter isDeleted() {
        if (isDeleted == null) {
            isDeleted = new StringFilter();
        }
        return isDeleted;
    }

    public void setIsDeleted(StringFilter isDeleted) {
        this.isDeleted = isDeleted;
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

    public StringFilter getFreeField4() {
        return freeField4;
    }

    public StringFilter freeField4() {
        if (freeField4 == null) {
            freeField4 = new StringFilter();
        }
        return freeField4;
    }

    public void setFreeField4(StringFilter freeField4) {
        this.freeField4 = freeField4;
    }

    public LongFilter getAddressId() {
        return addressId;
    }

    public LongFilter addressId() {
        if (addressId == null) {
            addressId = new LongFilter();
        }
        return addressId;
    }

    public void setAddressId(LongFilter addressId) {
        this.addressId = addressId;
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
        final OrganizationCriteria that = (OrganizationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(orgName, that.orgName) &&
            Objects.equals(branchcode, that.branchcode) &&
            Objects.equals(region, that.region) &&
            Objects.equals(ifscCode, that.ifscCode) &&
            Objects.equals(micrCode, that.micrCode) &&
            Objects.equals(swiftCode, that.swiftCode) &&
            Objects.equals(ibanCode, that.ibanCode) &&
            Objects.equals(routingTransitNo, that.routingTransitNo) &&
            Objects.equals(regNumber, that.regNumber) &&
            Objects.equals(gstinNumber, that.gstinNumber) &&
            Objects.equals(panNumber, that.panNumber) &&
            Objects.equals(tanNumber, that.tanNumber) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(email, that.email) &&
            Objects.equals(webSite, that.webSite) &&
            Objects.equals(fax, that.fax) &&
            Objects.equals(orgType, that.orgType) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(description, that.description) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(status, that.status) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(freeField4, that.freeField4) &&
            Objects.equals(addressId, that.addressId) &&
            Objects.equals(organizationId, that.organizationId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            orgName,
            branchcode,
            region,
            ifscCode,
            micrCode,
            swiftCode,
            ibanCode,
            routingTransitNo,
            regNumber,
            gstinNumber,
            panNumber,
            tanNumber,
            phone,
            email,
            webSite,
            fax,
            orgType,
            createdOn,
            createdBy,
            description,
            isDeleted,
            status,
            lastModified,
            lastModifiedBy,
            freeField1,
            freeField2,
            freeField3,
            freeField4,
            addressId,
            organizationId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganizationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (orgName != null ? "orgName=" + orgName + ", " : "") +
            (branchcode != null ? "branchcode=" + branchcode + ", " : "") +
            (region != null ? "region=" + region + ", " : "") +
            (ifscCode != null ? "ifscCode=" + ifscCode + ", " : "") +
            (micrCode != null ? "micrCode=" + micrCode + ", " : "") +
            (swiftCode != null ? "swiftCode=" + swiftCode + ", " : "") +
            (ibanCode != null ? "ibanCode=" + ibanCode + ", " : "") +
            (routingTransitNo != null ? "routingTransitNo=" + routingTransitNo + ", " : "") +
            (regNumber != null ? "regNumber=" + regNumber + ", " : "") +
            (gstinNumber != null ? "gstinNumber=" + gstinNumber + ", " : "") +
            (panNumber != null ? "panNumber=" + panNumber + ", " : "") +
            (tanNumber != null ? "tanNumber=" + tanNumber + ", " : "") +
            (phone != null ? "phone=" + phone + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (webSite != null ? "webSite=" + webSite + ", " : "") +
            (fax != null ? "fax=" + fax + ", " : "") +
            (orgType != null ? "orgType=" + orgType + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (freeField4 != null ? "freeField4=" + freeField4 + ", " : "") +
            (addressId != null ? "addressId=" + addressId + ", " : "") +
            (organizationId != null ? "organizationId=" + organizationId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
