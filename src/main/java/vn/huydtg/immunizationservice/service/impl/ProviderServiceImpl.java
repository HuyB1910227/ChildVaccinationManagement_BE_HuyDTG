package vn.huydtg.immunizationservice.service.impl;

import vn.huydtg.immunizationservice.domain.Provider;
import vn.huydtg.immunizationservice.repository.ProviderRepository;
import vn.huydtg.immunizationservice.service.ProviderService;
import vn.huydtg.immunizationservice.service.dto.ProviderDTO;
import vn.huydtg.immunizationservice.service.dto.ProviderFSelectDTO;
import vn.huydtg.immunizationservice.service.dto.VaccineFSelectDTO;
import vn.huydtg.immunizationservice.service.mapper.ProviderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@Transactional
public class ProviderServiceImpl implements ProviderService {


    private final ProviderRepository providerRepository;

    private final ProviderMapper providerMapper;

    public ProviderServiceImpl(ProviderRepository providerRepository, ProviderMapper providerMapper) {
        this.providerRepository = providerRepository;
        this.providerMapper = providerMapper;
    }

    @Override
    public ProviderDTO save(ProviderDTO providerDTO) {
        Provider provider = providerMapper.toEntity(providerDTO);
        provider = providerRepository.save(provider);
        return providerMapper.toDto(provider);
    }

    @Override
    public ProviderDTO update(ProviderDTO providerDTO) {
        Provider provider = providerMapper.toEntity(providerDTO);
        provider = providerRepository.save(provider);
        return providerMapper.toDto(provider);
    }

    @Override
    public Optional<ProviderDTO> partialUpdate(ProviderDTO providerDTO) {

        return providerRepository
            .findById(providerDTO.getId())
            .map(existingProvider -> {
                providerMapper.partialUpdate(existingProvider, providerDTO);

                return existingProvider;
            })
            .map(providerRepository::save)
            .map(providerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProviderDTO> findAll(Pageable pageable) {
        return providerRepository.findAll(pageable).map(providerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProviderDTO> findOne(Long id) {
        return providerRepository.findById(id).map(providerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        providerRepository.deleteById(id);
    }

    @Override
    public List<ProviderFSelectDTO> getProviderFSelectDTO() {
        List<Object[]> resultList = providerRepository.findAllProvidersForSelect();
        List<ProviderFSelectDTO> providerFSelectDTOList = new ArrayList<>();
        for (Object[] result : resultList) {
            if (result.length >= 2) {
                Long id = (Long) result[0];
                String name = (String) result[1];
                ProviderFSelectDTO providerFSelectDTO = new ProviderFSelectDTO();
                providerFSelectDTO.setId(id);
                providerFSelectDTO.setName(name);
                providerFSelectDTOList.add(providerFSelectDTO);
            }
        }
        return providerFSelectDTOList;
    }
}
