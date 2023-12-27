package vn.huydtg.immunizationservice.service;

import jakarta.persistence.criteria.JoinType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;
import vn.huydtg.immunizationservice.domain.FixedSchedule;
import vn.huydtg.immunizationservice.domain.FixedSchedule_;
import vn.huydtg.immunizationservice.domain.ImmunizationUnit_;
import vn.huydtg.immunizationservice.repository.FixedScheduleRepository;
import vn.huydtg.immunizationservice.service.criteria.FixedScheduleCriteria;
import vn.huydtg.immunizationservice.service.dto.FixedScheduleDTO;
import vn.huydtg.immunizationservice.service.mapper.FixedScheduleMapper;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class FixedScheduleQueryService extends QueryService<FixedSchedule> {


    private final FixedScheduleRepository fixedScheduleRepository;

    private final FixedScheduleMapper fixedScheduleMapper;


    public FixedScheduleQueryService(FixedScheduleRepository fixedScheduleRepository, FixedScheduleMapper fixedScheduleMapper) {
        this.fixedScheduleRepository = fixedScheduleRepository;
        this.fixedScheduleMapper = fixedScheduleMapper;
    }


    @Transactional(readOnly = true)
    public List<FixedScheduleDTO> findByCriteria(FixedScheduleCriteria criteria) {
        final Specification<FixedSchedule> specification = createSpecification(criteria);
        return fixedScheduleMapper.toDto(fixedScheduleRepository.findAll(specification));
    }


    @Transactional(readOnly = true)
    public Page<FixedScheduleDTO> findByCriteria(FixedScheduleCriteria criteria, Pageable page) {
        final Specification<FixedSchedule> specification = createSpecification(criteria);
        return fixedScheduleRepository.findAll(specification, page).map(fixedScheduleMapper::toDto);
    }


    @Transactional(readOnly = true)
    public long countByCriteria(FixedScheduleCriteria criteria) {
        final Specification<FixedSchedule> specification = createSpecification(criteria);
        return fixedScheduleRepository.count(specification);
    }


    protected Specification<FixedSchedule> createSpecification(FixedScheduleCriteria criteria) {
        Specification<FixedSchedule> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FixedSchedule_.id));
            }
            if (criteria.getWorkingDay() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWorkingDay(), FixedSchedule_.workingDay));
            }
            if (criteria.getWorkingWeek() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWorkingWeek(), FixedSchedule_.workingWeek));
            }
            if (criteria.getWorkingDayType() != null) {
                specification = specification.and(buildSpecification(criteria.getWorkingDayType(), FixedSchedule_.workingDayType));
            }
            if (criteria.getWorkingWeekType() != null) {
                specification = specification.and(buildSpecification(criteria.getWorkingWeekType(), FixedSchedule_.workingWeekType));
            }
            if (criteria.getIsEnable() != null) {
                specification = specification.and(buildSpecification(criteria.getIsEnable(), FixedSchedule_.isEnable));
            }
            if (criteria.getVaccinationType() != null) {
                specification = specification.and(buildSpecification(criteria.getVaccinationType(), FixedSchedule_.vaccinationType));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), FixedSchedule_.note));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), FixedSchedule_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), FixedSchedule_.endTime));
            }
            if (criteria.getImmunizationUnitId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getImmunizationUnitId(),
                            root -> root.join(FixedSchedule_.immunizationUnit, JoinType.LEFT).get(ImmunizationUnit_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
