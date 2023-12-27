package vn.huydtg.immunizationservice.service;

import vn.huydtg.immunizationservice.service.dto.SuggestionDetailDTO;
import vn.huydtg.immunizationservice.service.dto.SuggestionForSelectDTO;
import vn.huydtg.immunizationservice.web.rest.vm.FindingInjectionAdviceVM;
import vn.huydtg.immunizationservice.web.rest.vm.LoggerVM;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DiagnosisService {

    List<SuggestionForSelectDTO> generateSuggestionList(Long appointmentCardId);


    SuggestionDetailDTO getSuggestionDetailDTO(Long appointmentCardId, Long diseaseId);


    List<LoggerVM> createLoggerAuditVaccineLot(Long appointmentCardId, Long vaccineLotId);

    Optional<FindingInjectionAdviceVM> findEligibleInjection(Long appointmentCardId, UUID vaccineId);
}
