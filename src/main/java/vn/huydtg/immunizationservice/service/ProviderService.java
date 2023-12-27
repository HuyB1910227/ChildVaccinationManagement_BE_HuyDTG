package vn.huydtg.immunizationservice.service;

import vn.huydtg.immunizationservice.service.dto.ProviderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.huydtg.immunizationservice.service.dto.ProviderFSelectDTO;

import java.util.List;
import java.util.Optional;

public interface ProviderService {

    ProviderDTO save(ProviderDTO providerDTO);


    ProviderDTO update(ProviderDTO providerDTO);


    Optional<ProviderDTO> partialUpdate(ProviderDTO providerDTO);


    Page<ProviderDTO> findAll(Pageable pageable);


    Optional<ProviderDTO> findOne(Long id);


    void delete(Long id);

    List<ProviderFSelectDTO> getProviderFSelectDTO();
}
