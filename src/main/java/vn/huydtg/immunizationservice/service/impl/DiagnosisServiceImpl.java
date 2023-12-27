package vn.huydtg.immunizationservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import vn.huydtg.immunizationservice.domain.enumeration.AgeType;
import vn.huydtg.immunizationservice.domain.enumeration.DistanceTimeType;
import vn.huydtg.immunizationservice.domain.enumeration.LogType;
import vn.huydtg.immunizationservice.domain.enumeration.RequestType;
import vn.huydtg.immunizationservice.repository.*;
import vn.huydtg.immunizationservice.service.AgeService;
import vn.huydtg.immunizationservice.service.DiagnosisService;
import vn.huydtg.immunizationservice.service.dto.*;
import vn.huydtg.immunizationservice.service.mapper.*;
import vn.huydtg.immunizationservice.web.rest.errors.BadRequestAlertException;
import vn.huydtg.immunizationservice.web.rest.vm.AgeOfPatientVM;
import vn.huydtg.immunizationservice.web.rest.vm.FindingInjectionAdviceVM;
import vn.huydtg.immunizationservice.web.rest.vm.LoggerVM;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DiagnosisServiceImpl implements DiagnosisService {

    Logger logger = LoggerFactory.getLogger(DiagnosisServiceImpl.class);
    private final AssignmentRepository assignmentRepository;

    private final HistoryRepository historyRepository;

    private final InjectionRepository injectionRepository;

    private final DiseaseRepository diseaseRepository;

    private final DiseaseMapper diseaseMapper;

    private final AppointmentCardMapper appointmentCardMapper;

    private final AppointmentCardRepository appointmentCardRepository;

    private final VaccineMapper vaccineMapper;

    private final AssignmentMapper assignmentMapper;

    private final AgeRepository ageRepository;

    private final AgeService ageService;

    private final InjectionMapper injectionMapper;

    private final VaccineLotRepository vaccineLotRepository;

    private final VaccineLotMapper vaccineLotMapper;

    public DiagnosisServiceImpl (
            AssignmentRepository assignmentRepository,
            AssignmentMapper assignmentMapper,
            HistoryRepository historyRepository,
            InjectionRepository injectionRepository,
            InjectionMapper injectionMapper,
            DiseaseRepository diseaseRepository,
            DiseaseMapper diseaseMapper,
            AppointmentCardRepository appointmentCardRepository,
            AppointmentCardMapper appointmentCardMapper,
            VaccineMapper vaccineMapper,
            AgeRepository ageRepository,
            VaccineLotRepository vaccineLotRepository,
            VaccineLotMapper vaccineLotMapper,
            AgeService ageService
    ) {
        this.assignmentRepository = assignmentRepository;
        this.assignmentMapper = assignmentMapper;
        this.historyRepository = historyRepository;
        this.injectionRepository = injectionRepository;
        this.injectionMapper = injectionMapper;
        this.diseaseRepository = diseaseRepository;
        this.diseaseMapper = diseaseMapper;
        this.appointmentCardRepository = appointmentCardRepository;
        this.appointmentCardMapper = appointmentCardMapper;
        this.vaccineMapper = vaccineMapper;
        this.ageRepository = ageRepository;
        this.vaccineLotRepository = vaccineLotRepository;
        this.vaccineLotMapper = vaccineLotMapper;
        this.ageService = ageService;
    }

    @Override
    public List<SuggestionForSelectDTO> generateSuggestionList(Long appointmentCardId) {

        List<Object[]> ObjDiseaseList = diseaseRepository.findAllDiseasesForSelect();
        List<DiseaseFSelectDTO> diseaseDTOList = new ArrayList<>();
        for (Object[] result : ObjDiseaseList) {
            if (result.length >= 2) {
                Long id = (Long) result[0];
                String name = (String) result[1];
                DiseaseFSelectDTO diseaseDTO = new DiseaseFSelectDTO();
                diseaseDTO.setId(id);
                diseaseDTO.setName(name);
                diseaseDTOList.add(diseaseDTO);
            }
        }

        Optional<AppointmentCardDTO> currentAppointmentCard = appointmentCardRepository.findById(appointmentCardId).map(appointmentCardMapper::toDto);
        if (currentAppointmentCard.isEmpty()) {
            return new ArrayList<>();
        }
        List<Object[]> ObjSelectedCurrentAssignment = assignmentRepository.getAssignmentForCurrentAppointmentCard(appointmentCardId);
        List<Long> selectedDiseaseIds = new ArrayList<>();
        for (Object[] result :
                ObjSelectedCurrentAssignment) {
            if (result.length >= 2) {
                Long diseaseId = (Long) result[1];
                selectedDiseaseIds.add(diseaseId);
            }
        }

        List<AssignmentInjectionForDiaryDTO> assignmentInjectionForDiaryDTOList = getAssignmentInjectionForDiaryDTOs(currentAppointmentCard.get().getPatient().getId());

        List<SuggestionForSelectDTO> suggestionForSelectDTOList = new ArrayList<>();
        for (DiseaseFSelectDTO disease : diseaseDTOList) {
            Long diseaseId = disease.getId();
            Set<Long> assigmentIds = new HashSet<>();
            for (AssignmentInjectionForDiaryDTO assigment: assignmentInjectionForDiaryDTOList) {
                if (diseaseId.equals(assigment.getDiseaseId())) {
                    assigmentIds.add(assigment.getAssignmentId());
                }
            }
            String possibilityOfInjectionStatus = "AVAILABLE";
            if (selectedDiseaseIds.contains(diseaseId)) {
                possibilityOfInjectionStatus = "SELECTED";
            }
            SuggestionForSelectDTO suggestionForSelectDTO = new SuggestionForSelectDTO(disease, assigmentIds, possibilityOfInjectionStatus);
            suggestionForSelectDTOList.add(suggestionForSelectDTO);
        }
        return suggestionForSelectDTOList;
    }


    @Override
    public SuggestionDetailDTO getSuggestionDetailDTO(Long appointmentCardId, Long diseaseId) {
        Optional<AppointmentCardDTO> currentAppointmentCard = appointmentCardRepository.findById(appointmentCardId).map(appointmentCardMapper::toDto);
        if (currentAppointmentCard.isEmpty()) {
            return null;
        }
        List<AssignmentInjectionForDiaryDTO> assignmentInjectionForDiaryDTOList = getAssignmentInjectionForDiaryDTOs(currentAppointmentCard.get().getPatient().getId());
        Set<Optional<AssignmentDTO>> setPreviousAssignment = new HashSet<>();
        Set<AssignmentDTO> setPreviousAssignmentDTO = new HashSet<>();
        for (AssignmentInjectionForDiaryDTO assignmentInjectionForDiaryDTO : assignmentInjectionForDiaryDTOList) {
            if (diseaseId.equals(assignmentInjectionForDiaryDTO.getDiseaseId())) {
                Optional<AssignmentDTO> thePreviousAssignment = assignmentRepository.findById(assignmentInjectionForDiaryDTO.getAssignmentId()).map(assignmentMapper::toDto);
                setPreviousAssignment.add(thePreviousAssignment);
            }
        }
        for (Optional<AssignmentDTO> optionalAssignment : setPreviousAssignment) {
            optionalAssignment.ifPresent(setPreviousAssignmentDTO::add);
        }
        List<AssignmentDTO> listPreviousAssignmentDTO = new ArrayList<>(setPreviousAssignmentDTO);
        listPreviousAssignmentDTO.sort(Comparator.comparing(AssignmentDTO::getInjectionCompletionTime));

        List<Object[]> ObjectCurrentAssignmentList = assignmentRepository.getAssignmentForCurrentAppointmentCardAndDisease(appointmentCardId, diseaseId);
        Optional<AssignmentDTO> currentAssignment;
        if (ObjectCurrentAssignmentList.isEmpty()) {
            currentAssignment = Optional.empty();
        } else {
            Object[] theFirstObject = ObjectCurrentAssignmentList.get(0);
            currentAssignment = assignmentRepository.findById((Long) theFirstObject[0]).map(assignmentMapper::toDto);
        }

        Optional<DiseaseWithVaccineRelationshipDTO> currentDisease = diseaseRepository.findOneWithEagerRelationships(diseaseId).map(
                disease -> {
                    DiseaseWithVaccineRelationshipDTO diseaseWithVaccineRelationshipDTO = new DiseaseWithVaccineRelationshipDTO();
                    diseaseWithVaccineRelationshipDTO.setId(disease.getId());
                    diseaseWithVaccineRelationshipDTO.setName(disease.getName());
                    diseaseWithVaccineRelationshipDTO.setDescription(disease.getDescription());
                    diseaseWithVaccineRelationshipDTO.setVaccines(disease.getVaccines().stream().map(vaccineMapper::toDto).collect(Collectors.toSet()));
                    return diseaseWithVaccineRelationshipDTO;
                });

        return currentDisease.map(diseaseWithVaccineRelationshipDTO -> new SuggestionDetailDTO(listPreviousAssignmentDTO, currentAssignment, diseaseWithVaccineRelationshipDTO)).orElse(null);
    }

    @Override
    public List<LoggerVM> createLoggerAuditVaccineLot(Long appointmentCardId, Long vaccineLotId) {
        Optional<VaccineLotDTO> currentVaccineLot = vaccineLotRepository.findById(vaccineLotId).map(vaccineLotMapper::toDto);
        if (currentVaccineLot.isEmpty()) {
            throw new BadRequestAlertException("Not found vaccine lot", "REST_VACCINE_LOT", "vaccineLotNotExist");
        }
        Optional<AppointmentCardDTO> currentAppointmentCard = appointmentCardRepository.findById(appointmentCardId).map(appointmentCardMapper::toDto);
        if (currentAppointmentCard.isEmpty()) {
            throw new BadRequestAlertException("Not found appointment card", "REST_APPOINTMENT_CARD", "appointmentCardNotExist");
        }
        List<Long> needToCheckDiseaseIds = currentVaccineLot.get().getVaccine().getDiseases().stream().map(DiseaseDTO::getId).toList();

        List<AssignmentDTO> selectedAssignmentDTOS = assignmentRepository.findAllByAppointmentCardId(currentAppointmentCard.get().getId()).stream().map(assignmentMapper::toDto).toList();
        if (!selectedAssignmentDTOS.isEmpty()) {
            //familiar flat
            boolean flatIsFamiliarDisease = false;
            for (AssignmentDTO selectedAssignmentDTO: selectedAssignmentDTOS) {
                Set<DiseaseDTO> tempDiseaseDTO = selectedAssignmentDTO.getVaccineLot().getVaccine().getDiseases();
                for (DiseaseDTO d : tempDiseaseDTO) {
                    if(needToCheckDiseaseIds.contains(d.getId())) {
                        flatIsFamiliarDisease = true;
                        break;
                    }
                }
            }
            if (flatIsFamiliarDisease) {
                return Collections.singletonList(new LoggerVM(LogType.DANGER, "DISEASE", "Vắc xin hiện tại đã phòng nhóm bệnh tương đương với 1 vắc xin đang được chỉ định"));
            }
        }

        List<AssignmentInjectionForDiaryDTO> theLastAssignmentAndDisease = new ArrayList<>();
        for (Long needToCheckDiseaseId: needToCheckDiseaseIds) {
            Object[] obA = assignmentRepository.getTheLastAssignmentOfDiseaseByPatientIdAndDiseaseId(currentAppointmentCard.get().getPatient().getId(), needToCheckDiseaseId);
            if (obA.length > 0) {
                Object[] subArray = (Object[]) obA[0];
                if (subArray.length >= 2) {
                    Long assignmentId = (Long) subArray[0];
                    Long diseaseId = (Long) subArray[1];
                    AssignmentInjectionForDiaryDTO a = new AssignmentInjectionForDiaryDTO(assignmentId, diseaseId);
                    theLastAssignmentAndDisease.add(a);
                }
            }
        }
        logger.info("theLastAssignmentAndDisease: " + theLastAssignmentAndDisease);
        Instant theEligibleDateForVaccination = Instant.MIN;
        if (!theLastAssignmentAndDisease.isEmpty()) {
            for (AssignmentInjectionForDiaryDTO theLastInject: theLastAssignmentAndDisease) {
                Optional<AssignmentDTO> a = assignmentRepository.findById(theLastInject.getAssignmentId()).map(assignmentMapper::toDto);
                if (a.isPresent()) {
                    Instant injectionCompletionDate = a.get().getInjectionCompletionTime();
                    Instant forecastDate;
                    if (a.get().getInjection() != null) {
                        try {
                            Integer days = convertDistanceTimeTypeToDays(a.get().getInjection().getDistanceFromPrevious(), a.get().getInjection().getDistanceFromPreviousType());
                            forecastDate = injectionCompletionDate.plus(days, ChronoUnit.DAYS);
                        } catch (Exception e) {
                            // can not get days
                            logger.debug("get catch error forecast date: " + e);
                            forecastDate = a.get().getNextAvailableInjectionDate();
                        }
                    } else {
                        forecastDate = a.get().getNextAvailableInjectionDate();
                    }
                    if (forecastDate.isAfter(theEligibleDateForVaccination)) {
                        //
                        theEligibleDateForVaccination = forecastDate;
                    }
                }
            }
        }
        if (theEligibleDateForVaccination.compareTo(Instant.now()) >= 0) {
            return Collections.singletonList(new LoggerVM(LogType.DANGER, "VACCINE_LOT", "Chưa đến thời gian tiêm! Vui lòng thử lại sau. Thời gian tiêm khả dụng kế tiếp đối với vắc xin đã chọn: " + theEligibleDateForVaccination));
        }
        List<LoggerVM> warningLoggerDifferenceVaccine = new ArrayList<>();
        if(!theLastAssignmentAndDisease.isEmpty()) {
            for (AssignmentInjectionForDiaryDTO theLastInject: theLastAssignmentAndDisease) {
                Optional<AssignmentDTO> a = assignmentRepository.findById(theLastInject.getAssignmentId()).map(assignmentMapper::toDto);
                if (a.isPresent() && !a.get().getVaccineLot().getVaccine().equals(currentVaccineLot.get().getVaccine())) {
                    warningLoggerDifferenceVaccine.add(new LoggerVM(LogType.WARNING, "DIFFERENCE_VACCINE", "Vắc xin vừa chọn không cùng loại với vắc xin đã tiêm trước đó! " + a.get().getVaccineLot().getVaccine().getName()));
                }
            }
        }

        if (!warningLoggerDifferenceVaccine.isEmpty()) {
            return warningLoggerDifferenceVaccine;
        }
        return Collections.singletonList(new LoggerVM(LogType.PRIMARY, "VACCINE_LOT", "Vắc xin hợp lệ!"));
    }


    @Override
    public Optional<FindingInjectionAdviceVM> findEligibleInjection(Long appointmentCardId, UUID vaccineId) {
        Optional<AppointmentCardDTO> currentAppointmentCard = appointmentCardRepository.findById(appointmentCardId).map(appointmentCardMapper::toDto);
        if (currentAppointmentCard.isEmpty()) {
            throw new BadRequestAlertException("Not found appointment card", "REST_APPOINTMENT_CARD", "appointmentCardNotExist");
        }
        PatientDTO patientDTO = currentAppointmentCard.get().getPatient();
        Double oldOfPatient = getAgeInYear(patientDTO.getDateOfBirth(), LocalDate.now());
        logger.info("year old: " + oldOfPatient);
        List<AgeFSelectDTO> ageFSelectDTOList = ageService.findAllAgeByVaccine(vaccineId);
        AgeFSelectDTO ageFSelectDTOEligible = null;
        AgeFSelectDTO ageFSelectDTOAvailable = null;
        if (ageFSelectDTOList.isEmpty()) {
            throw new BadRequestAlertException("Not found age for vaccination", "REST_AGE", "ageListNotExist");
        }

        double flatMax = Double.MAX_VALUE;
        for (AgeFSelectDTO ageFSelectDTO : ageFSelectDTOList) {
            Double minAge = convertToYearOld(ageFSelectDTO.getMinAge(), ageFSelectDTO.getMinAgeType());
            Double maxAge = convertToYearOld(ageFSelectDTO.getMaxAge(), ageFSelectDTO.getMaxAgeType());
            logger.info("loop: " + "max: " + minAge + "min: " + maxAge);
            if (oldOfPatient >= minAge && oldOfPatient <= maxAge) {
                ageFSelectDTOEligible = ageFSelectDTO;
                break;
            }
            if((oldOfPatient - maxAge) > 0 && (oldOfPatient - maxAge) < flatMax) {
                flatMax = (oldOfPatient - maxAge);
                ageFSelectDTOAvailable = ageFSelectDTO;
                logger.info("Đã có khoảng tạm");
            }
        }
        if(ageFSelectDTOEligible == null) {
            logger.info("Không có khoảng thích hợp, dời về khoảng nhỏ hơn");
            ageFSelectDTOEligible = ageFSelectDTOAvailable;
        }

        if (ageFSelectDTOEligible == null) {
            return Optional.empty();
        }

        List<Object[]> assignmentRelationWithVaccineList = assignmentRepository.getAllAssigmentByPatientAndVaccine(currentAppointmentCard.get().getPatient().getId(), vaccineId);
        List<LastInjectionFAdviseDTO> theLastInjectionList = new ArrayList<>();
        for (Object[] assignment : assignmentRelationWithVaccineList) {
            if (assignment.length >= 2) {
                Long assignmentId = (Long) assignment[0];
                Long injectionId = (Long) assignment[1];
                Instant injectionCompletionTime = (Instant) assignment[2];
                Integer injectionTime = (Integer) assignment[3];
                theLastInjectionList.add(new LastInjectionFAdviseDTO(assignmentId, injectionId, injectionCompletionTime, injectionTime));
            }
        }
        //DU ĐOÁN MŨI TIẾP THEO
        int injectionTimeAdvice = 1;
        if (!theLastInjectionList.isEmpty()) {
            int count = theLastInjectionList.size();
            injectionTimeAdvice = count + 1;
        }

        InjectionAdviseDTO injectionAdviseDTO;

        if (ageFSelectDTOEligible.getRequestType() == RequestType.TIEU_CHUAN || ageFSelectDTOEligible.getRequestType() == RequestType.KHUYEN_CAO) {
            long injectionIdAdvise = 0;
            int flatMaxInjectionTime = Integer.MAX_VALUE;
            long injectionIdAvailable = 0;
            for (InjectionFAgeDTO injection: ageFSelectDTOEligible.getInjections()) {
                if (injection.getInjectionTime() == injectionTimeAdvice) {
                    injectionIdAdvise = injection.getId();
                    break;
                }
                if ((injectionTimeAdvice - injection.getInjectionTime()) > 0 && (injectionTimeAdvice - injection.getInjectionTime()) < flatMaxInjectionTime) {
                    flatMaxInjectionTime = flatMaxInjectionTime - injection.getInjectionTime();
                    injectionIdAvailable = injection.getId();
                }
            }

            if (injectionIdAdvise == 0) {
                injectionIdAdvise = injectionIdAvailable;
            }
            injectionAdviseDTO = new InjectionAdviseDTO(injectionIdAdvise, injectionTimeAdvice, theLastInjectionList);
        } else {
            injectionAdviseDTO = new InjectionAdviseDTO(null, injectionTimeAdvice, theLastInjectionList);
        }
        AgeOfPatientVM age = convertToAge(oldOfPatient);
        return Optional.of(new FindingInjectionAdviceVM(age, ageFSelectDTOList, injectionAdviseDTO, ageFSelectDTOEligible));
    }

    List<AssignmentInjectionForDiaryDTO> getAssignmentInjectionForDiaryDTOs(UUID patientId) {
        List<Object[]> ObjectAssignmentForDiaryList = assignmentRepository.getAllAssigmentForInjectionDiary(patientId);
        List<AssignmentInjectionForDiaryDTO> assignmentInjectionForDiaryDTOList = new ArrayList<>();
        for (Object[] result : ObjectAssignmentForDiaryList) {
            if (result.length >= 2) {
                Long assignmentId = (Long) result[0];
                Long diseaseId = (Long) result[1];
                AssignmentInjectionForDiaryDTO assignmentInjectionForDiaryDTO = new AssignmentInjectionForDiaryDTO(assignmentId, diseaseId);
                assignmentInjectionForDiaryDTOList.add(assignmentInjectionForDiaryDTO);
            }
        }
        return assignmentInjectionForDiaryDTOList;
    }

    public Double convertToYearOld(Integer age, AgeType ageType) {
        double v = 0.0;
        if (ageType == AgeType.TUAN) {
            v = (double) age / 52;

        }
        if (ageType == AgeType.THANG) {
            v = (double) age / 12;

        }
        if (ageType == AgeType.TUOI) {
            v = age;
        }
        return v;
    }

    public Integer convertDistanceTimeTypeToDays (Integer i, DistanceTimeType distanceTimeType) {
        if (distanceTimeType == DistanceTimeType.NGAY) {
            return i;
        } else if (distanceTimeType == DistanceTimeType.TUAN) {
            return i * 7;
        } else if (distanceTimeType == DistanceTimeType.THANG) {
            return i * 30;
        } else {
            return i *365;
        }
    }

    public Double getAgeInYear(LocalDate dateOfBirth, LocalDate currentDate) {
        long daysBetween = currentDate.toEpochDay() - dateOfBirth.toEpochDay();
        double ageInYears = daysBetween / 365.0;
        return ageInYears;
    }

    public AgeOfPatientVM convertToAge(Double oldOfPatient) {
        if (oldOfPatient > 1) {
            if (oldOfPatient % 1 == 0) {
                return new AgeOfPatientVM(oldOfPatient.intValue(), AgeType.TUOI);
            } else if (oldOfPatient % 12 == 0) {
                int t = oldOfPatient.intValue() / 12;
                return new AgeOfPatientVM(t, AgeType.THANG);
            } else if (oldOfPatient % 52 == 0) {
                int t = oldOfPatient.intValue() / 52;
                return new AgeOfPatientVM(t, AgeType.TUAN);
            } else {
                int t = (int) Math.floor(oldOfPatient);
                return new AgeOfPatientVM(t, AgeType.TUOI);
            }
        } else {
            if (oldOfPatient % 12 == 0) {
                int t = oldOfPatient.intValue() / 12;
                return new AgeOfPatientVM(t, AgeType.THANG);
            } else if (oldOfPatient % 52 == 0) {
                int t = oldOfPatient.intValue() / 52;
                return new AgeOfPatientVM(t, AgeType.TUAN);
            } else {
                int t = (int) Math.floor(oldOfPatient * 12);
                if (t == 0) {
                    t = (int) Math.floor(oldOfPatient * 52);
                    return new AgeOfPatientVM(t, AgeType.TUAN);
                }
                return new AgeOfPatientVM(t, AgeType.THANG);
            }
        }

    }

}

