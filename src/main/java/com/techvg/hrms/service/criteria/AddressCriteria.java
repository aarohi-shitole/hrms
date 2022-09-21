package com.techvg.hrms.service.criteria;

import com.techvg.hrms.domain.enumeration.AddressType;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.Address} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.AddressResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /addresses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AddressCriteria implements Serializable, Criteria {

    /**
     * Class for filtering AddressType
     */
    public static class AddressTypeFilter extends Filter<AddressType> {

        public AddressTypeFilter() {}

        public AddressTypeFilter(AddressTypeFilter filter) {
            super(filter);
        }

        @Override
        public AddressTypeFilter copy() {
            return new AddressTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private AddressTypeFilter type;

    private StringFilter address1;

    private BooleanFilter hasPrefered;

    private StringFilter landMark;

    private StringFilter pincode;

    private DoubleFilter lattitude;

    private DoubleFilter longitude;

    private StringFilter mapNavUrl;

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

    private Boolean distinct;

    public AddressCriteria() {}

    public AddressCriteria(AddressCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.address1 = other.address1 == null ? null : other.address1.copy();
        this.hasPrefered = other.hasPrefered == null ? null : other.hasPrefered.copy();
        this.landMark = other.landMark == null ? null : other.landMark.copy();
        this.pincode = other.pincode == null ? null : other.pincode.copy();
        this.lattitude = other.lattitude == null ? null : other.lattitude.copy();
        this.longitude = other.longitude == null ? null : other.longitude.copy();
        this.mapNavUrl = other.mapNavUrl == null ? null : other.mapNavUrl.copy();
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
        this.distinct = other.distinct;
    }

    @Override
    public AddressCriteria copy() {
        return new AddressCriteria(this);
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

    public AddressTypeFilter getType() {
        return type;
    }

    public AddressTypeFilter type() {
        if (type == null) {
            type = new AddressTypeFilter();
        }
        return type;
    }

    public void setType(AddressTypeFilter type) {
        this.type = type;
    }

    public StringFilter getAddress1() {
        return address1;
    }

    public StringFilter address1() {
        if (address1 == null) {
            address1 = new StringFilter();
        }
        return address1;
    }

    public void setAddress1(StringFilter address1) {
        this.address1 = address1;
    }

    public BooleanFilter getHasPrefered() {
        return hasPrefered;
    }

    public BooleanFilter hasPrefered() {
        if (hasPrefered == null) {
            hasPrefered = new BooleanFilter();
        }
        return hasPrefered;
    }

    public void setHasPrefered(BooleanFilter hasPrefered) {
        this.hasPrefered = hasPrefered;
    }

    public StringFilter getLandMark() {
        return landMark;
    }

    public StringFilter landMark() {
        if (landMark == null) {
            landMark = new StringFilter();
        }
        return landMark;
    }

    public void setLandMark(StringFilter landMark) {
        this.landMark = landMark;
    }

    public StringFilter getPincode() {
        return pincode;
    }

    public StringFilter pincode() {
        if (pincode == null) {
            pincode = new StringFilter();
        }
        return pincode;
    }

    public void setPincode(StringFilter pincode) {
        this.pincode = pincode;
    }

    public DoubleFilter getLattitude() {
        return lattitude;
    }

    public DoubleFilter lattitude() {
        if (lattitude == null) {
            lattitude = new DoubleFilter();
        }
        return lattitude;
    }

    public void setLattitude(DoubleFilter lattitude) {
        this.lattitude = lattitude;
    }

    public DoubleFilter getLongitude() {
        return longitude;
    }

    public DoubleFilter longitude() {
        if (longitude == null) {
            longitude = new DoubleFilter();
        }
        return longitude;
    }

    public void setLongitude(DoubleFilter longitude) {
        this.longitude = longitude;
    }

    public StringFilter getMapNavUrl() {
        return mapNavUrl;
    }

    public StringFilter mapNavUrl() {
        if (mapNavUrl == null) {
            mapNavUrl = new StringFilter();
        }
        return mapNavUrl;
    }

    public void setMapNavUrl(StringFilter mapNavUrl) {
        this.mapNavUrl = mapNavUrl;
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
        final AddressCriteria that = (AddressCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(type, that.type) &&
            Objects.equals(address1, that.address1) &&
            Objects.equals(hasPrefered, that.hasPrefered) &&
            Objects.equals(landMark, that.landMark) &&
            Objects.equals(pincode, that.pincode) &&
            Objects.equals(lattitude, that.lattitude) &&
            Objects.equals(longitude, that.longitude) &&
            Objects.equals(mapNavUrl, that.mapNavUrl) &&
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
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            type,
            address1,
            hasPrefered,
            landMark,
            pincode,
            lattitude,
            longitude,
            mapNavUrl,
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
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AddressCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (address1 != null ? "address1=" + address1 + ", " : "") +
            (hasPrefered != null ? "hasPrefered=" + hasPrefered + ", " : "") +
            (landMark != null ? "landMark=" + landMark + ", " : "") +
            (pincode != null ? "pincode=" + pincode + ", " : "") +
            (lattitude != null ? "lattitude=" + lattitude + ", " : "") +
            (longitude != null ? "longitude=" + longitude + ", " : "") +
            (mapNavUrl != null ? "mapNavUrl=" + mapNavUrl + ", " : "") +
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
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
