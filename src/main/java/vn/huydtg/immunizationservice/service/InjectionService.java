package vn.huydtg.immunizationservice.service;

import vn.huydtg.immunizationservice.service.dto.InjectionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface InjectionService {

    InjectionDTO save(InjectionDTO injectionDTO);


    InjectionDTO update(InjectionDTO injectionDTO);


    Optional<InjectionDTO> partialUpdate(InjectionDTO injectionDTO);

    Page<InjectionDTO> findAll(Pageable pageable);


    Optional<InjectionDTO> findOne(Long id);

    void delete(Long id);


}
