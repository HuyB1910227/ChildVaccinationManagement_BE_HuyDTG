package vn.huydtg.immunizationservice.service;

import vn.huydtg.immunizationservice.service.dto.HistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.huydtg.immunizationservice.service.dto.HistoryFCustomerDTO;

import java.util.List;
import java.util.Optional;


public interface HistoryService {

    HistoryDTO save(HistoryDTO historyDTO);


    HistoryDTO update(HistoryDTO historyDTO);


    Optional<HistoryDTO> partialUpdate(HistoryDTO historyDTO);


    Page<HistoryDTO> findAll(Pageable pageable);


    Optional<HistoryDTO> findOne(Long id);


    void delete(Long id);

    List<HistoryFCustomerDTO> getAllHistoriesByToken();
}
