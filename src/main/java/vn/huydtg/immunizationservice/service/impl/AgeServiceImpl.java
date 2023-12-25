package vn.huydtg.immunizationservice.service.impl;

import vn.huydtg.immunizationservice.domain.Age;
import vn.huydtg.immunizationservice.repository.AgeRepository;
import vn.huydtg.immunizationservice.repository.InjectionRepository;
import vn.huydtg.immunizationservice.service.AgeService;
import vn.huydtg.immunizationservice.service.dto.*;
import vn.huydtg.immunizationservice.service.mapper.AgeMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.logging.Logger;


@Service
@Transactional
public class AgeServiceImpl implements AgeService {

    Logger logger = Logger.getLogger(AgeServiceImpl.class.getName());
    private final AgeRepository ageRepository;

    private final AgeMapper ageMapper;

    private final InjectionRepository injectionRepository;

    public AgeServiceImpl(AgeRepository ageRepository, AgeMapper ageMapper, InjectionRepository injectionRepository) {
        this.ageRepository = ageRepository;
        this.ageMapper = ageMapper;
        this.injectionRepository = injectionRepository;
    }

    @Override
    public AgeDTO save(AgeDTO ageDTO) {
        Age existedAge = ageRepository.findByMinAgeAndMinAgeTypeAndMaxAgeAndMaxAgeTypeAndVaccineId(ageDTO.getMinAge(), ageDTO.getMinAgeType(), ageDTO.getMaxAge(), ageDTO.getMaxAgeType(), ageDTO.getVaccine().getId());
        Age age = null;
        if (existedAge == null) {
            age = ageMapper.toEntity(ageDTO);
            logger.info(age.toString());
            age = ageRepository.save(age);
        } else {
            logger.info("existed" + existedAge);
            age = existedAge;
        }
        return ageMapper.toDto(age);
    }

    @Override
    public AgeDTO update(AgeDTO ageDTO) {
        Age age = ageMapper.toEntity(ageDTO);
        age = ageRepository.save(age);
        return ageMapper.toDto(age);
    }

    @Override
    public Optional<AgeDTO> partialUpdate(AgeDTO ageDTO) {

        return ageRepository
            .findById(ageDTO.getId())
            .map(existingAge -> {
                ageMapper.partialUpdate(existingAge, ageDTO);

                return existingAge;
            })
            .map(ageRepository::save)
            .map(ageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgeDTO> findAll(Pageable pageable) {
        return ageRepository.findAll(pageable).map(ageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AgeDTO> findOne(Long id) {
        return ageRepository.findById(id).map(ageMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        ageRepository.deleteById(id);
    }


    @Override
    public List<AgeFSelectDTO> findAllAgeByVaccine(UUID vaccineId){
        List<Age> ageList = ageRepository.findAllByVaccineId(vaccineId);
        List<AgeFSelectDTO> ageFSelectDTOList = new ArrayList<>();
        if (!ageList.isEmpty()) {
            for (Age age : ageList) {
                List<InjectionFAgeDTO> injectionFAgeDTOS = new ArrayList<>(injectionRepository.findAllByAgeId(age.getId())
                        .stream().map(inj -> {
                            return new InjectionFAgeDTO(inj.getId(), inj.getInjectionTime(), inj.getDistanceFromPrevious(), inj.getDistanceFromPreviousType());
                        }).toList());
                injectionFAgeDTOS.sort(Comparator.comparing(InjectionFAgeDTO::getInjectionTime));
                ageFSelectDTOList.add(new AgeFSelectDTO(age.getId(), age.getMinAge(), age.getMinAgeType(), age.getMaxAge(), age.getMaxAgeType(), age.getRequestType(), age.getNote(), injectionFAgeDTOS));
            }
        }
        return ageFSelectDTOList;
    }
}
