package vn.huydtg.immunizationservice.service.criteria;

import tech.jhipster.service.filter.*;
import vn.huydtg.immunizationservice.domain.enumeration.AgeType;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import vn.huydtg.immunizationservice.domain.enumeration.RequestType;

import java.io.Serializable;
import java.util.Objects;

@ParameterObject
public class AgeCriteria implements Serializable, Criteria {


    public static class AgeTypeFilter extends Filter<AgeType> {

        public AgeTypeFilter() {}

        public AgeTypeFilter(AgeTypeFilter filter) {
            super(filter);
        }

        @Override
        public AgeTypeFilter copy() {
            return new AgeTypeFilter(this);
        }
    }

    public static class RequestTypeFilter extends Filter<RequestType> {

        public RequestTypeFilter() {}

        public RequestTypeFilter(RequestTypeFilter filter) {
            super(filter);
        }

        @Override
        public RequestTypeFilter copy() {
            return new RequestTypeFilter(this);
        }
    }


    private LongFilter id;

    private IntegerFilter minAge;

    private AgeTypeFilter minAgeType;

    private IntegerFilter maxAge;

    private AgeTypeFilter maxAgeType;

    private LongFilter injectionId;

    private UUIDFilter vaccineId;

    private StringFilter note;

    private RequestTypeFilter requestType;

    private Boolean distinct;

    public AgeCriteria() {}

    public AgeCriteria(AgeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.minAge = other.minAge == null ? null : other.minAge.copy();
        this.minAgeType = other.minAgeType == null ? null : other.minAgeType.copy();
        this.maxAge = other.maxAge == null ? null : other.maxAge.copy();
        this.maxAgeType = other.maxAgeType == null ? null : other.maxAgeType.copy();
        this.injectionId = other.injectionId == null ? null : other.injectionId.copy();
        this.vaccineId = other.vaccineId == null ? null : other.vaccineId.copy();
        this.note = other.note == null ? null : other.note.copy();
        this.requestType = other.requestType == null ? null : other.requestType.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AgeCriteria copy() {
        return new AgeCriteria(this);
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

    public IntegerFilter getMinAge() {
        return minAge;
    }

    public IntegerFilter minAge() {
        if (minAge == null) {
            minAge = new IntegerFilter();
        }
        return minAge;
    }

    public void setMinAge(IntegerFilter minAge) {
        this.minAge = minAge;
    }

    public AgeTypeFilter getMinAgeType() {
        return minAgeType;
    }

    public AgeTypeFilter minAgeType() {
        if (minAgeType == null) {
            minAgeType = new AgeTypeFilter();
        }
        return minAgeType;
    }

    public void setMinAgeType(AgeTypeFilter minAgeType) {
        this.minAgeType = minAgeType;
    }

    public IntegerFilter getMaxAge() {
        return maxAge;
    }

    public IntegerFilter maxAge() {
        if (maxAge == null) {
            maxAge = new IntegerFilter();
        }
        return maxAge;
    }

    public void setMaxAge(IntegerFilter maxAge) {
        this.maxAge = maxAge;
    }

    public AgeTypeFilter getMaxAgeType() {
        return maxAgeType;
    }

    public AgeTypeFilter maxAgeType() {
        if (maxAgeType == null) {
            maxAgeType = new AgeTypeFilter();
        }
        return maxAgeType;
    }

    public void setMaxAgeType(AgeTypeFilter maxAgeType) {
        this.maxAgeType = maxAgeType;
    }

    public LongFilter getInjectionId() {
        return injectionId;
    }

    public LongFilter injectionId() {
        if (injectionId == null) {
            injectionId = new LongFilter();
        }
        return injectionId;
    }

    public void setInjectionId(LongFilter injectionId) {
        this.injectionId = injectionId;
    }

    public UUIDFilter getVaccineId() {
        return vaccineId;
    }

    public UUIDFilter vaccineId() {
        if (vaccineId == null) {
            vaccineId = new UUIDFilter();
        }
        return vaccineId;
    }

    public void setVaccineId(UUIDFilter vaccineId) {
        this.vaccineId = vaccineId;
    }

    public StringFilter getNote() {
        return note;
    }

    public StringFilter note() {
        if (note == null) {
            note = new StringFilter();
        }
        return note;
    }

    public void setNote(StringFilter note) {
        this.note = note;
    }


    public RequestTypeFilter getRequestType() {
        return requestType;
    }

    public RequestTypeFilter requestType() {
        if (requestType == null) {
            requestType = new RequestTypeFilter();
        }
        return requestType;
    }

    public void setRequestType(RequestTypeFilter requestType) {
        this.requestType = requestType;
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
        final AgeCriteria that = (AgeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(minAge, that.minAge) &&
            Objects.equals(minAgeType, that.minAgeType) &&
            Objects.equals(maxAge, that.maxAge) &&
            Objects.equals(maxAgeType, that.maxAgeType) &&
            Objects.equals(injectionId, that.injectionId) &&
            Objects.equals(vaccineId, that.vaccineId) &&
                    Objects.equals(note, that.note) &&
                    Objects.equals(requestType, that.requestType) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, minAge, minAgeType, maxAge, maxAgeType, injectionId, vaccineId, note, requestType, distinct);
    }


    @Override
    public String toString() {
        return "AgeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (minAge != null ? "minAge=" + minAge + ", " : "") +
            (minAgeType != null ? "minAgeType=" + minAgeType + ", " : "") +
            (maxAge != null ? "maxAge=" + maxAge + ", " : "") +
            (maxAgeType != null ? "maxAgeType=" + maxAgeType + ", " : "") +
            (injectionId != null ? "injectionId=" + injectionId + ", " : "") +
            (vaccineId != null ? "vaccineId=" + vaccineId + ", " : "") +
                (note != null ? "note=" + note + ", " : "") +
                (requestType != null ? "requestType=" + requestType + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
