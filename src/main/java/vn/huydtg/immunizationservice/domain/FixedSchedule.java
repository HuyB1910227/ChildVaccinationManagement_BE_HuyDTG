package vn.huydtg.immunizationservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import vn.huydtg.immunizationservice.domain.enumeration.VaccinationType;
import vn.huydtg.immunizationservice.domain.enumeration.WorkingDayType;
import vn.huydtg.immunizationservice.domain.enumeration.WorkingWeekType;


import java.io.Serializable;

import java.time.Instant;


@Entity
@Table(name = "fixed_schedule")
public class FixedSchedule implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "working_day")
    private String workingDay;

    @Column(name = "working_week")
    private String workingWeek;

    @NotNull
    @Column(name = "is_enable", nullable = false)
    private Boolean isEnable;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "working_day_type", nullable = false)
    private WorkingDayType workingDayType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "working_week_type", nullable = false)
    private WorkingWeekType workingWeekType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "vaccination_type", nullable = true)
    private VaccinationType vaccinationType;

    @Column(name = "note")
    private String note;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private Instant startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private Instant endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "employees", "vaccineLots", "fixedSchedules", "appointmentCards" }, allowSetters = true)
    private ImmunizationUnit immunizationUnit;



    public Long getId() {
        return this.id;
    }

    public FixedSchedule id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkingDay() {
        return this.workingDay;
    }

    public FixedSchedule workingDay(String workingDay) {
        this.setWorkingDay(workingDay);
        return this;
    }

    public void setWorkingDay(String workingDay) {
        this.workingDay = workingDay;
    }

    public String getWorkingWeek() {
        return this.workingWeek;
    }

    public FixedSchedule workingWeek(String workingWeek) {
        this.setWorkingWeek(workingWeek);
        return this;
    }

    public void setWorkingWeek(String workingWeek) {
        this.workingWeek = workingWeek;
    }



    public WorkingDayType getWorkingDayType() {
        return workingDayType;
    }

    public FixedSchedule workingDayType(WorkingDayType workingDayType) {
        this.setWorkingDayType(workingDayType);
        return this;
    }
    public void setWorkingDayType(WorkingDayType workingDayType) {
        this.workingDayType = workingDayType;
    }

    public WorkingWeekType getWorkingWeekType() {
        return workingWeekType;
    }

    public FixedSchedule workingWeekType(WorkingWeekType workingWeekType) {
        this.setWorkingWeekType(workingWeekType);
        return this;
    }
    public void setWorkingWeekType(WorkingWeekType workingWeekType) {
        this.workingWeekType = workingWeekType;
    }


    public VaccinationType getVaccinationType() {
        return this.vaccinationType;
    }

    public FixedSchedule vaccinationType(VaccinationType vaccinationType) {
        this.setVaccinationType(vaccinationType);
        return this;
    }

    public void setVaccinationType(VaccinationType vaccinationType) {
        this.vaccinationType = vaccinationType;
    }

    public String getNote() {
        return this.note;
    }

    public FixedSchedule note(String note) {
        this.setNote(note);
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public FixedSchedule startTime(Instant startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public FixedSchedule endTime(Instant endTime) {
        this.setEndTime(endTime);
        return this;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public ImmunizationUnit getImmunizationUnit() {
        return this.immunizationUnit;
    }

    public void setImmunizationUnit(ImmunizationUnit immunizationUnit) {
        this.immunizationUnit = immunizationUnit;
    }

    public FixedSchedule immunizationUnit(ImmunizationUnit immunizationUnit) {
        this.setImmunizationUnit(immunizationUnit);
        return this;
    }


    public Boolean getIsEnable() {
        return this.isEnable;
    }

    public FixedSchedule isEnable(Boolean isEnable) {
        this.setIsEnable(isEnable);
        return this;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FixedSchedule)) {
            return false;
        }
        return id != null && id.equals(((FixedSchedule) o).id);
    }

    @Override
    public int hashCode() {

        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "FixedSchedule{" +
                "id=" + getId() +
                ", workingDay=" + getWorkingDay() +
                ", workingWeek=" + getWorkingWeek() +
                ", vaccinationType='" + getVaccinationType() + "'" +
                ", note='" + getNote() + "'" +
                ", startTime='" + getStartTime() + "'" +
                ", endTime='" + getEndTime() + "'" +
                "}";
    }
}
