package vn.huydtg.immunizationservice.service.dto;

import jakarta.validation.constraints.NotNull;
import vn.huydtg.immunizationservice.domain.enumeration.VaccinationType;
import vn.huydtg.immunizationservice.domain.enumeration.WorkingDayType;
import vn.huydtg.immunizationservice.domain.enumeration.WorkingWeekType;


import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;


public class FixedScheduleDTO implements Serializable {


    private Long id;

    private String workingDay;

    private String workingWeek;



    private WorkingDayType workingDayType;
    private WorkingWeekType workingWeekType;

    @NotNull
    private VaccinationType vaccinationType;

    private String note;

    private Boolean isEnable;

    @NotNull
    private Instant startTime;

    @NotNull
    private Instant endTime;



    private ImmunizationUnitDTO immunizationUnit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkingDay() {
        return workingDay;
    }

    public void setWorkingDay(String workingDay) {
        this.workingDay = workingDay;
    }

    public String getWorkingWeek() {
        return workingWeek;
    }

    public void setWorkingWeek(String workingWeek) {
        this.workingWeek = workingWeek;
    }




    public WorkingDayType getWorkingDayType() {
        return workingDayType;
    }

    public void setWorkingDayType(WorkingDayType workingDayType) {
        this.workingDayType = workingDayType;
    }

    public WorkingWeekType getWorkingWeekType() {
        return workingWeekType;
    }

    public void setWorkingWeekType(WorkingWeekType workingWeekType) {
        this.workingWeekType = workingWeekType;
    }

    public Boolean getIsEnable() {
    return isEnable;
}

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }
    public VaccinationType getVaccinationType() {
        return vaccinationType;
    }

    public void setVaccinationType(VaccinationType vaccinationType) {
        this.vaccinationType = vaccinationType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public ImmunizationUnitDTO getImmunizationUnit() {
        return immunizationUnit;
    }

    public void setImmunizationUnit(ImmunizationUnitDTO immunizationUnit) {
        this.immunizationUnit = immunizationUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FixedScheduleDTO)) {
            return false;
        }

        FixedScheduleDTO fixedScheduleDTO = (FixedScheduleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fixedScheduleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "FixedScheduleDTO{" +
                "id=" + getId() +
                ", workingDay=" + getWorkingDay() +
                ", workingWeek=" + getWorkingWeek() +
                ", workingDayType=" + getWorkingDayType() +
                ", workingWeekType=" + getWorkingWeekType() +
                ", isEnable='" + getIsEnable() + "'" +
                ", vaccinationType='" + getVaccinationType() + "'" +
                ", note='" + getNote() + "'" +
                ", startTime='" + getStartTime() + "'" +
                ", endTime='" + getEndTime() + "'" +
                ", immunizationUnit=" + getImmunizationUnit() +
                "}";
    }
}
