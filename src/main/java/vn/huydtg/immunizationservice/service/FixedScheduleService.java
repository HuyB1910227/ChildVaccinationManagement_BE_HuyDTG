package vn.huydtg.immunizationservice.service;


import vn.huydtg.immunizationservice.service.dto.FixedScheduleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface FixedScheduleService {

    FixedScheduleDTO save(FixedScheduleDTO fixedScheduleDTO);


    FixedScheduleDTO update(FixedScheduleDTO fixedScheduleDTO);


    Optional<FixedScheduleDTO> partialUpdate(FixedScheduleDTO fixedScheduleDTO);


    Page<FixedScheduleDTO> findAll(Pageable pageable);


    Optional<FixedScheduleDTO> findOne(Long id);

    void delete(Long id);



    List<FixedScheduleDTO> getAllFixedSchedulesWithWorkingWeek(FixedScheduleDTO fixedScheduleDTO);
}
