package vn.huydtg.immunizationservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.huydtg.immunizationservice.domain.AppointmentCard;
import vn.huydtg.immunizationservice.repository.AppointmentCardRepository;
import vn.huydtg.immunizationservice.service.AppointmentCardService;
import vn.huydtg.immunizationservice.service.dto.AppointmentCardDTO;
import vn.huydtg.immunizationservice.service.dto.AppointmentCardFSelectDTO;
import vn.huydtg.immunizationservice.service.mapper.AppointmentCardMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
@Transactional
public class AppointmentCardServiceImpl implements AppointmentCardService {


    private final AppointmentCardRepository appointmentCardRepository;

    private final AppointmentCardMapper appointmentCardMapper;

    Logger logger = LoggerFactory.getLogger(AppointmentCardServiceImpl.class);

    public AppointmentCardServiceImpl(AppointmentCardRepository appointmentCardRepository, AppointmentCardMapper appointmentCardMapper) {
        this.appointmentCardRepository = appointmentCardRepository;
        this.appointmentCardMapper = appointmentCardMapper;
    }

    @Override
    public AppointmentCardDTO save(AppointmentCardDTO appointmentCardDTO) {
        AppointmentCard appointmentCard = appointmentCardMapper.toEntity(appointmentCardDTO);
        appointmentCard = appointmentCardRepository.save(appointmentCard);
        return appointmentCardMapper.toDto(appointmentCard);
    }

    @Override
    public AppointmentCardDTO update(AppointmentCardDTO appointmentCardDTO) {
        AppointmentCard appointmentCard = appointmentCardMapper.toEntity(appointmentCardDTO);
        appointmentCard = appointmentCardRepository.save(appointmentCard);
        return appointmentCardMapper.toDto(appointmentCard);
    }

    @Override
    public Optional<AppointmentCardDTO> partialUpdate(AppointmentCardDTO appointmentCardDTO) {
        return appointmentCardRepository
            .findById(appointmentCardDTO.getId())
            .map(existingAppointmentCard -> {
                    appointmentCardMapper.partialUpdate(existingAppointmentCard, appointmentCardDTO);
                    return existingAppointmentCard;


            })
            .map(appointmentCardRepository::save)
            .map(appointmentCardMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppointmentCardDTO> findAll(Pageable pageable) {
        return appointmentCardRepository.findAll(pageable).map(appointmentCardMapper::toDto);
    }



    @Transactional(readOnly = true)
    public List<AppointmentCardDTO> findAllWhereHistoryIsNull() {
        return StreamSupport
            .stream(appointmentCardRepository.findAll().spliterator(), false)
            .filter(appointmentCard -> appointmentCard.getHistory() == null)
            .map(appointmentCardMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppointmentCardDTO> findOne(Long id) {
        return appointmentCardRepository.findById(id).map(appointmentCardMapper::toDto);
    }




    @Override
    public void delete(Long id) {
        appointmentCardRepository.deleteById(id);
    }

    @Override
    public List<AppointmentCardFSelectDTO> getAppointmentCardFSelectDTO() {
        List<Object[]> resultList = appointmentCardRepository.findAllAppointmentCardsForSelect();
        List<AppointmentCardFSelectDTO> appointmentCardFSelectDTOList = new ArrayList<>();
        for (Object[] result : resultList) {
            if (result.length >= 2) {
                Long id = (Long) result[0];
                Instant appointmentDateConfirmed = (Instant) result[1];
                Integer status = (Integer) result[2];
                AppointmentCardFSelectDTO appointmentCardFSelectDTO = new AppointmentCardFSelectDTO();
                appointmentCardFSelectDTO.setId(id);
                appointmentCardFSelectDTO.setAppointmentDateConfirmed(appointmentDateConfirmed);
                appointmentCardFSelectDTO.setStatus(status);
                appointmentCardFSelectDTOList.add(appointmentCardFSelectDTO);
            }
        }
        return appointmentCardFSelectDTOList;
    }

    @Override
    public List<AppointmentCardDTO> findAppointmentCardWScheduleExpectedFCustomer(UUID customerId, Instant fromDate, Instant toDate) {
        List<AppointmentCardDTO> appointmentCardList = new ArrayList<>(appointmentCardRepository.findAppointmentCardConfirmedForCustomer(customerId, fromDate, toDate).stream().map(appointmentCardMapper::toDto).toList());
        appointmentCardList.sort(Comparator.comparing(AppointmentCardDTO::getAppointmentDateConfirmed));
        return appointmentCardList;
    }

    @Override
    public List<AppointmentCardDTO> findAppointmentCardWScheduleRegisteredFCustomer(UUID customerId, Instant fromDate) {
        List<AppointmentCardDTO> appointmentCardList = new ArrayList<>(appointmentCardRepository.findAppointmentCardRegisteredForCustomer(customerId, fromDate).stream().map(appointmentCardMapper::toDto).toList());
        appointmentCardList.sort(Comparator.comparing(AppointmentCardDTO::getAppointmentDate));
        return appointmentCardList;
    }
}
