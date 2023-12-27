package vn.huydtg.immunizationservice.service.impl;

import vn.huydtg.immunizationservice.domain.FixedSchedule;
import vn.huydtg.immunizationservice.repository.FixedScheduleRepository;
import vn.huydtg.immunizationservice.service.FixedScheduleService;
import vn.huydtg.immunizationservice.service.dto.FixedScheduleDTO;
import vn.huydtg.immunizationservice.service.mapper.FixedScheduleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Transactional
public class FixedScheduleServiceImpl implements FixedScheduleService {

    Logger logger = LoggerFactory.getLogger(FixedScheduleServiceImpl.class);
    private final FixedScheduleRepository fixedScheduleRepository;

    private final FixedScheduleMapper fixedScheduleMapper;

    public FixedScheduleServiceImpl(FixedScheduleRepository fixedScheduleRepository, FixedScheduleMapper fixedScheduleMapper) {
        this.fixedScheduleRepository = fixedScheduleRepository;
        this.fixedScheduleMapper = fixedScheduleMapper;
    }

    @Override
    public FixedScheduleDTO save(FixedScheduleDTO fixedScheduleDTO) {
        FixedSchedule fixedSchedule = fixedScheduleMapper.toEntity(fixedScheduleDTO);
        fixedSchedule = fixedScheduleRepository.save(fixedSchedule);
        return fixedScheduleMapper.toDto(fixedSchedule);
    }

    @Override
    public FixedScheduleDTO update(FixedScheduleDTO fixedScheduleDTO) {
        FixedSchedule fixedSchedule = fixedScheduleMapper.toEntity(fixedScheduleDTO);
        fixedSchedule = fixedScheduleRepository.save(fixedSchedule);
        return fixedScheduleMapper.toDto(fixedSchedule);
    }

    @Override
    public Optional<FixedScheduleDTO> partialUpdate(FixedScheduleDTO fixedScheduleDTO) {

        return fixedScheduleRepository
            .findById(fixedScheduleDTO.getId())
            .map(existingFixedSchedule -> {
                fixedScheduleMapper.partialUpdate(existingFixedSchedule, fixedScheduleDTO);

                return existingFixedSchedule;
            })
            .map(fixedScheduleRepository::save)
            .map(fixedScheduleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FixedScheduleDTO> findAll(Pageable pageable) {
        return fixedScheduleRepository.findAll(pageable).map(fixedScheduleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FixedScheduleDTO> findOne(Long id) {
        return fixedScheduleRepository.findById(id).map(fixedScheduleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        fixedScheduleRepository.deleteById(id);
    }



    @Override
    public List<FixedScheduleDTO> getAllFixedSchedulesWithWorkingWeek(FixedScheduleDTO fixedScheduleDTO) {
        List<FixedScheduleDTO> resultList = new ArrayList<>(fixedScheduleRepository.findAllFixedSchedulesWithWorkingWeek(
                fixedScheduleDTO.getWorkingDay(), fixedScheduleDTO.getWorkingWeek(), fixedScheduleDTO.getStartTime(),
                fixedScheduleDTO.getEndTime(), fixedScheduleDTO.getImmunizationUnit().getId(), fixedScheduleDTO.getVaccinationType()
        ).stream().map(fixedScheduleMapper::toDto).toList());
        Set<FixedScheduleDTO> existingSet = new HashSet<>(resultList);
        List<String> listWeekOptions = List.of("W1", "W2", "W3", "W4") ;
        if (Objects.equals(fixedScheduleDTO.getWorkingWeek(), "ALL")) {
            for (String item: listWeekOptions) {
                List<FixedScheduleDTO> plusData = fixedScheduleRepository.findAllFixedSchedulesWithWorkingWeek(
                        fixedScheduleDTO.getWorkingDay(), item, fixedScheduleDTO.getStartTime()
                        , fixedScheduleDTO.getEndTime(), fixedScheduleDTO.getImmunizationUnit().getId(), fixedScheduleDTO.getVaccinationType()
                ).stream().map(fixedScheduleMapper::toDto).toList();
                List<FixedScheduleDTO> newItems = plusData.stream()
                        .filter(i -> !existingSet.contains(i))
                        .toList();
                resultList.addAll(newItems);
                existingSet.addAll(newItems);
            }
        }
        return resultList;
    }
}
