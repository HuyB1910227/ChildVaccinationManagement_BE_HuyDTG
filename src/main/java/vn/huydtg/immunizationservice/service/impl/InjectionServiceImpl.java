package vn.huydtg.immunizationservice.service.impl;

import vn.huydtg.immunizationservice.domain.Injection;
import vn.huydtg.immunizationservice.repository.InjectionRepository;
import vn.huydtg.immunizationservice.service.InjectionService;
import vn.huydtg.immunizationservice.service.dto.InjectionDTO;
import vn.huydtg.immunizationservice.service.mapper.InjectionMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class InjectionServiceImpl implements InjectionService {


    private final InjectionRepository injectionRepository;

    private final InjectionMapper injectionMapper;

    public InjectionServiceImpl(InjectionRepository injectionRepository, InjectionMapper injectionMapper) {
        this.injectionRepository = injectionRepository;
        this.injectionMapper = injectionMapper;
    }

    @Override
    public InjectionDTO save(InjectionDTO injectionDTO) {
        Injection injection = injectionMapper.toEntity(injectionDTO);
        injection = injectionRepository.save(injection);
        return injectionMapper.toDto(injection);
    }

    @Override
    public InjectionDTO update(InjectionDTO injectionDTO) {
        Injection injection = injectionMapper.toEntity(injectionDTO);
        injection = injectionRepository.save(injection);
        return injectionMapper.toDto(injection);
    }

    @Override
    public Optional<InjectionDTO> partialUpdate(InjectionDTO injectionDTO) {

        return injectionRepository
            .findById(injectionDTO.getId())
            .map(existingInjection -> {
                injectionMapper.partialUpdate(existingInjection, injectionDTO);

                return existingInjection;
            })
            .map(injectionRepository::save)
            .map(injectionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InjectionDTO> findAll(Pageable pageable) {
        return injectionRepository.findAll(pageable).map(injectionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InjectionDTO> findOne(Long id) {
        return injectionRepository.findById(id).map(injectionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        injectionRepository.deleteById(id);
    }
}
