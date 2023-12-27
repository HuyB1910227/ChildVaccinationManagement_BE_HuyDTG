package vn.huydtg.immunizationservice.service;

import jakarta.persistence.criteria.JoinType;
import vn.huydtg.immunizationservice.domain.*;
import vn.huydtg.immunizationservice.domain.ImmunizationUnit;
import vn.huydtg.immunizationservice.repository.ImmunizationUnitRepository;
import vn.huydtg.immunizationservice.service.criteria.ImmunizationUnitCriteria;
import vn.huydtg.immunizationservice.service.dto.ImmunizationUnitDTO;
import vn.huydtg.immunizationservice.service.mapper.ImmunizationUnitMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ImmunizationUnitQueryService extends QueryService<ImmunizationUnit> {


    private final ImmunizationUnitRepository immunizationUnitRepository;

    private final ImmunizationUnitMapper immunizationUnitMapper;

    public ImmunizationUnitQueryService(
        ImmunizationUnitRepository immunizationUnitRepository,
        ImmunizationUnitMapper immunizationUnitMapper
    ) {
        this.immunizationUnitRepository = immunizationUnitRepository;
        this.immunizationUnitMapper = immunizationUnitMapper;
    }


    @Transactional(readOnly = true)
    public List<ImmunizationUnitDTO> findByCriteria(ImmunizationUnitCriteria criteria) {
        final Specification<ImmunizationUnit> specification = createSpecification(criteria);
        return immunizationUnitMapper.toDto(immunizationUnitRepository.findAll(specification));
    }


    @Transactional(readOnly = true)
    public Page<ImmunizationUnitDTO> findByCriteria(ImmunizationUnitCriteria criteria, Pageable page) {
        final Specification<ImmunizationUnit> specification = createSpecification(criteria);
        return immunizationUnitRepository.findAll(specification, page).map(immunizationUnitMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(ImmunizationUnitCriteria criteria) {
        final Specification<ImmunizationUnit> specification = createSpecification(criteria);
        return immunizationUnitRepository.count(specification);
    }


    protected Specification<ImmunizationUnit> createSpecification(ImmunizationUnitCriteria criteria) {
        Specification<ImmunizationUnit> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ImmunizationUnit_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ImmunizationUnit_.name));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), ImmunizationUnit_.address));
            }
            if (criteria.getOperatingLicence() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getOperatingLicence(), ImmunizationUnit_.operatingLicence));
            }
            if (criteria.getHotline() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHotline(), ImmunizationUnit_.hotline));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), ImmunizationUnit_.isActive));
            }
            if (criteria.getLatitude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLatitude(), ImmunizationUnit_.latitude));
            }
            if (criteria.getLongitude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLongitude(), ImmunizationUnit_.longitude));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(ImmunizationUnit_.employees, JoinType.LEFT).get(Employee_.id)
                        )
                    );
            }
            if (criteria.getVaccineLotId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVaccineLotId(),
                            root -> root.join(ImmunizationUnit_.vaccineLots, JoinType.LEFT).get(VaccineLot_.id)
                        )
                    );
            }
            if (criteria.getFixedScheduleId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFixedScheduleId(),
                            root -> root.join(ImmunizationUnit_.fixedSchedules, JoinType.LEFT).get(FixedSchedule_.id)
                        )
                    );
            }
            if (criteria.getAppointmentCardId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAppointmentCardId(),
                            root -> root.join(ImmunizationUnit_.appointmentCards, JoinType.LEFT).get(AppointmentCard_.id)
                        )
                    );
            }
        }
        return specification;
    }

    public Specification<ImmunizationUnit> hasHotline(String hotline) {
        return (root, query, criteriaBuilder) -> {
            if (hotline == null || hotline.isEmpty()) {
                return null;
            }
            return criteriaBuilder.equal(root.get("hotline"), hotline);
        };
    }

}
