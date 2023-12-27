package vn.huydtg.immunizationservice.web.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.huydtg.immunizationservice.repository.*;
import vn.huydtg.immunizationservice.service.DiagnosisService;
import vn.huydtg.immunizationservice.service.dto.*;
import vn.huydtg.immunizationservice.web.rest.errors.BadRequestAlertException;
import vn.huydtg.immunizationservice.web.rest.vm.FindingInjectionAdviceVM;
import vn.huydtg.immunizationservice.web.rest.vm.LoggerVM;
import java.util.logging.Logger;


import java.util.*;

@RestController
@RequestMapping("/api")
public class DiagnosisResource {
    private static final String ENTITY_NAME = "REST_DIAGNOSIS";

    @Value("${spring.application.name}")
    private String applicationName;

    private final DiseaseRepository diseaseRepository;

    private final VaccineRepository vaccineRepository;

    private final AppointmentCardRepository appointmentCardRepository;

    private final VaccineLotRepository vaccineLotRepository;

    private final DiagnosisService diagnosisService;

    Logger logger = Logger.getLogger(DiagnosisResource.class.getName());

    public DiagnosisResource (
            DiseaseRepository diseaseRepository,
            VaccineRepository vaccineRepository,
            AppointmentCardRepository appointmentCardRepository,
            VaccineLotRepository vaccineLotRepository,
            DiagnosisService diagnosisService
    ) {

        this.diseaseRepository = diseaseRepository;
        this.vaccineRepository = vaccineRepository;
        this.appointmentCardRepository = appointmentCardRepository;
        this.vaccineLotRepository = vaccineLotRepository;
        this.diagnosisService = diagnosisService;
    }

    @GetMapping("/diagnosis/suggestion-select/by-appointment-card/{appointmentCardId}")
    public ResponseEntity<?> getSuggestionByGroupDisease(@PathVariable Long appointmentCardId) {
        if (!appointmentCardRepository.existsById(appointmentCardId)) {
            throw new BadRequestAlertException("Not found appointment card", "REST_DIAGNOSIS", "notfoundappointmentcard");
        }
        List<SuggestionForSelectDTO> result = diagnosisService.generateSuggestionList(appointmentCardId);
        return ResponseEntity.ok().body(result);
    }


    @GetMapping("/diagnosis/suggestion-details/by-appointment-card/{appointmentCardId}/require-disease/{diseaseId}")
    public ResponseEntity<?> getSuggestionForDiseaseDetail(@PathVariable Long appointmentCardId, @PathVariable Long diseaseId) {
        if (!appointmentCardRepository.existsById(appointmentCardId)) {
            throw new BadRequestAlertException("Not found appointment card", "REST_DIAGNOSIS", "notfoundappointmentcard");
        }
        if (!diseaseRepository.existsById(diseaseId)) {
            throw new BadRequestAlertException("Not found disease", "REST_DIAGNOSIS", "notfoundisease");
        }
        SuggestionDetailDTO result = diagnosisService.getSuggestionDetailDTO(appointmentCardId, diseaseId);
        return ResponseEntity.ok(result);

    }


    //Kiểm tra lô vắc xin có phù hợp tiêm cho trẻ không
    @GetMapping("/diagnosis/check-selected-vaccine-lot/{vaccineLotId}/in-appointment-card/{appointmentCardId}")
    public ResponseEntity<?> checkVaccineLot(@PathVariable Long appointmentCardId, @PathVariable Long vaccineLotId) {
        if (!appointmentCardRepository.existsById(appointmentCardId)) {
            throw new BadRequestAlertException("Not found appointment card", "REST_DIAGNOSIS", "notfoundappointmentcard");
        }
        if (!vaccineLotRepository.existsById(vaccineLotId)) {
            throw new BadRequestAlertException("Not found vaccine lot", "REST_DIAGNOSIS", "notfoundvaccinelot");
        }
        List<LoggerVM> loggerVMList = diagnosisService.createLoggerAuditVaccineLot(appointmentCardId, vaccineLotId);
        if (loggerVMList.size() == 1) {
            return ResponseEntity.ok(loggerVMList.get(0));
        }
        return ResponseEntity.ok(loggerVMList);
    }


    @GetMapping("/diagnosis/finding-injection/{vaccineId}/in-appointment-card/{appointmentCardId}")
    public ResponseEntity<?> findEligibleInjection(@PathVariable Long appointmentCardId, @PathVariable UUID vaccineId) {
        if (!appointmentCardRepository.existsById(appointmentCardId)) {
            throw new BadRequestAlertException("Not found appointment card", "REST_DIAGNOSIS", "notfoundappointmentcard");
        }
        if (!vaccineRepository.existsById(vaccineId)) {
            throw new BadRequestAlertException("Not found vaccine", "REST_DIAGNOSIS", "notfoundvaccine");
        }

        Optional<FindingInjectionAdviceVM> result = diagnosisService.findEligibleInjection(appointmentCardId, vaccineId);
        if (result.isEmpty()) {
            InjectionAdviseDTO injectionAdviseDTO = new InjectionAdviseDTO();
            return ResponseEntity.ok(injectionAdviseDTO);
        } else {
            return ResponseEntity.ok(result);
        }
    }


}
