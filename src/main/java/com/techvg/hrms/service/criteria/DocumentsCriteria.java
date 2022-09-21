package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.Documents} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.DocumentsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /documents?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DocumentsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter referenceId;

    private StringFilter type;

    private StringFilter subtype;

    private StringFilter fileName;

    private StringFilter filePath;

    private StringFilter fileUrl;

    private InstantFilter issuedDate;

    private InstantFilter validUpTo;

    private StringFilter placeOfIssued;

    private BooleanFilter hasVerified;

    private StringFilter remark;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private BooleanFilter isDeleted;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private LongFilter employeeId;

    private LongFilter organizationId;

    private Boolean distinct;

    public DocumentsCriteria() {}

    public DocumentsCriteria(DocumentsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.referenceId = other.referenceId == null ? null : other.referenceId.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.subtype = other.subtype == null ? null : other.subtype.copy();
        this.fileName = other.fileName == null ? null : other.fileName.copy();
        this.filePath = other.filePath == null ? null : other.filePath.copy();
        this.fileUrl = other.fileUrl == null ? null : other.fileUrl.copy();
        this.issuedDate = other.issuedDate == null ? null : other.issuedDate.copy();
        this.validUpTo = other.validUpTo == null ? null : other.validUpTo.copy();
        this.placeOfIssued = other.placeOfIssued == null ? null : other.placeOfIssued.copy();
        this.hasVerified = other.hasVerified == null ? null : other.hasVerified.copy();
        this.remark = other.remark == null ? null : other.remark.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.organizationId = other.organizationId == null ? null : other.organizationId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DocumentsCriteria copy() {
        return new DocumentsCriteria(this);
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

    public LongFilter getReferenceId() {
        return referenceId;
    }

    public LongFilter referenceId() {
        if (referenceId == null) {
            referenceId = new LongFilter();
        }
        return referenceId;
    }

    public void setReferenceId(LongFilter referenceId) {
        this.referenceId = referenceId;
    }

    public StringFilter getType() {
        return type;
    }

    public StringFilter type() {
        if (type == null) {
            type = new StringFilter();
        }
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public StringFilter getSubtype() {
        return subtype;
    }

    public StringFilter subtype() {
        if (subtype == null) {
            subtype = new StringFilter();
        }
        return subtype;
    }

    public void setSubtype(StringFilter subtype) {
        this.subtype = subtype;
    }

    public StringFilter getFileName() {
        return fileName;
    }

    public StringFilter fileName() {
        if (fileName == null) {
            fileName = new StringFilter();
        }
        return fileName;
    }

    public void setFileName(StringFilter fileName) {
        this.fileName = fileName;
    }

    public StringFilter getFilePath() {
        return filePath;
    }

    public StringFilter filePath() {
        if (filePath == null) {
            filePath = new StringFilter();
        }
        return filePath;
    }

    public void setFilePath(StringFilter filePath) {
        this.filePath = filePath;
    }

    public StringFilter getFileUrl() {
        return fileUrl;
    }

    public StringFilter fileUrl() {
        if (fileUrl == null) {
            fileUrl = new StringFilter();
        }
        return fileUrl;
    }

    public void setFileUrl(StringFilter fileUrl) {
        this.fileUrl = fileUrl;
    }

    public InstantFilter getIssuedDate() {
        return issuedDate;
    }

    public InstantFilter issuedDate() {
        if (issuedDate == null) {
            issuedDate = new InstantFilter();
        }
        return issuedDate;
    }

    public void setIssuedDate(InstantFilter issuedDate) {
        this.issuedDate = issuedDate;
    }

    public InstantFilter getValidUpTo() {
        return validUpTo;
    }

    public InstantFilter validUpTo() {
        if (validUpTo == null) {
            validUpTo = new InstantFilter();
        }
        return validUpTo;
    }

    public void setValidUpTo(InstantFilter validUpTo) {
        this.validUpTo = validUpTo;
    }

    public StringFilter getPlaceOfIssued() {
        return placeOfIssued;
    }

    public StringFilter placeOfIssued() {
        if (placeOfIssued == null) {
            placeOfIssued = new StringFilter();
        }
        return placeOfIssued;
    }

    public void setPlaceOfIssued(StringFilter placeOfIssued) {
        this.placeOfIssued = placeOfIssued;
    }

    public BooleanFilter getHasVerified() {
        return hasVerified;
    }

    public BooleanFilter hasVerified() {
        if (hasVerified == null) {
            hasVerified = new BooleanFilter();
        }
        return hasVerified;
    }

    public void setHasVerified(BooleanFilter hasVerified) {
        this.hasVerified = hasVerified;
    }

    public StringFilter getRemark() {
        return remark;
    }

    public StringFilter remark() {
        if (remark == null) {
            remark = new StringFilter();
        }
        return remark;
    }

    public void setRemark(StringFilter remark) {
        this.remark = remark;
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
        final DocumentsCriteria that = (DocumentsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(referenceId, that.referenceId) &&
            Objects.equals(type, that.type) &&
            Objects.equals(subtype, that.subtype) &&
            Objects.equals(fileName, that.fileName) &&
            Objects.equals(filePath, that.filePath) &&
            Objects.equals(fileUrl, that.fileUrl) &&
            Objects.equals(issuedDate, that.issuedDate) &&
            Objects.equals(validUpTo, that.validUpTo) &&
            Objects.equals(placeOfIssued, that.placeOfIssued) &&
            Objects.equals(hasVerified, that.hasVerified) &&
            Objects.equals(remark, that.remark) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(organizationId, that.organizationId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            referenceId,
            type,
            subtype,
            fileName,
            filePath,
            fileUrl,
            issuedDate,
            validUpTo,
            placeOfIssued,
            hasVerified,
            remark,
            lastModified,
            lastModifiedBy,
            createdBy,
            createdOn,
            isDeleted,
            freeField1,
            freeField2,
            freeField3,
            employeeId,
            organizationId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (referenceId != null ? "referenceId=" + referenceId + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (subtype != null ? "subtype=" + subtype + ", " : "") +
            (fileName != null ? "fileName=" + fileName + ", " : "") +
            (filePath != null ? "filePath=" + filePath + ", " : "") +
            (fileUrl != null ? "fileUrl=" + fileUrl + ", " : "") +
            (issuedDate != null ? "issuedDate=" + issuedDate + ", " : "") +
            (validUpTo != null ? "validUpTo=" + validUpTo + ", " : "") +
            (placeOfIssued != null ? "placeOfIssued=" + placeOfIssued + ", " : "") +
            (hasVerified != null ? "hasVerified=" + hasVerified + ", " : "") +
            (remark != null ? "remark=" + remark + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (organizationId != null ? "organizationId=" + organizationId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
