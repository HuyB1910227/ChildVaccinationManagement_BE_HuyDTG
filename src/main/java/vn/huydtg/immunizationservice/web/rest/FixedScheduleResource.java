package vn.huydtg.immunizationservice.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.UUIDFilter;
import vn.huydtg.immunizationservice.domain.enumeration.VaccinationType;
import vn.huydtg.immunizationservice.domain.enumeration.WorkingDayType;
import vn.huydtg.immunizationservice.domain.enumeration.WorkingWeekType;
import vn.huydtg.immunizationservice.domain.helper.DayTranslator;
import vn.huydtg.immunizationservice.repository.FixedScheduleRepository;
import vn.huydtg.immunizationservice.security.SecurityUtils;
import vn.huydtg.immunizationservice.service.FixedScheduleQueryService;
import vn.huydtg.immunizationservice.service.FixedScheduleService;
import vn.huydtg.immunizationservice.service.criteria.FixedScheduleCriteria;
import vn.huydtg.immunizationservice.service.dto.FixedScheduleDTO;
import vn.huydtg.immunizationservice.service.mapper.FixedScheduleMapper;
import vn.huydtg.immunizationservice.web.rest.errors.BadRequestAlertException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import vn.huydtg.immunizationservice.web.rest.utils.HeaderUtil;
import vn.huydtg.immunizationservice.web.rest.utils.PaginationUtil;
import vn.huydtg.immunizationservice.web.rest.utils.ResponseUtil;
import vn.huydtg.immunizationservice.web.rest.vm.FixedScheduleCalendarRequestVM;
import vn.huydtg.immunizationservice.web.rest.vm.WorkingScheduleResponseVM;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.*;
import java.time.temporal.WeekFields;
import java.util.*;

@RestController
@RequestMapping("/api")
public class FixedScheduleResource {

    Logger logger = LoggerFactory.getLogger(FixedScheduleResource.class);
    private static final String ENTITY_NAME = "REST_FIXED_SCHEDULE";

    @Value("${spring.application.name}")
    private String applicationName;

    private final FixedScheduleService fixedScheduleService;

    private final FixedScheduleRepository fixedScheduleRepository;

    private final FixedScheduleQueryService fixedScheduleQueryService;

    private final FixedScheduleMapper fixedScheduleMapper;



    public FixedScheduleResource(
        FixedScheduleService fixedScheduleService,
        FixedScheduleRepository fixedScheduleRepository,
        FixedScheduleQueryService fixedScheduleQueryService,
        FixedScheduleMapper fixedScheduleMapper

    ) {
        this.fixedScheduleService = fixedScheduleService;
        this.fixedScheduleRepository = fixedScheduleRepository;
        this.fixedScheduleQueryService = fixedScheduleQueryService;
        this.fixedScheduleMapper = fixedScheduleMapper;

    }

    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('ADMINISTRATOR')")
    @PostMapping("/fixed-schedules")
    public ResponseEntity<FixedScheduleDTO> createFixedSchedule(@Valid @RequestBody FixedScheduleDTO fixedScheduleDTO)
        throws URISyntaxException {

        FixedScheduleDTO result = fixedScheduleService.save(fixedScheduleDTO);
        return ResponseEntity
            .created(new URI("/api/fixed-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('ADMINISTRATOR')")
    @PutMapping("/fixed-schedules/{id}")
    public ResponseEntity<FixedScheduleDTO> updateFixedSchedule(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FixedScheduleDTO fixedScheduleDTO
    ) throws URISyntaxException {
        if (fixedScheduleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fixedScheduleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fixedScheduleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FixedScheduleDTO result = fixedScheduleService.update(fixedScheduleDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fixedScheduleDTO.getId().toString()))
            .body(result);
    }

    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('ADMINISTRATOR')")
    @PatchMapping(value = "/fixed-schedules/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FixedScheduleDTO> partialUpdateFixedSchedule(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FixedScheduleDTO fixedScheduleDTO
    ) throws URISyntaxException {
        if (fixedScheduleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fixedScheduleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fixedScheduleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FixedScheduleDTO> result = fixedScheduleService.partialUpdate(fixedScheduleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fixedScheduleDTO.getId().toString())
        );
    }


    @GetMapping("/fixed-schedules")
    public ResponseEntity<List<FixedScheduleDTO>> getAllFixedSchedules(
        FixedScheduleCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        boolean isEmployee = SecurityUtils.hasCurrentUserAnyOfAuthorities("MANAGER", "PHYSICIAN", "STAFF");
        if(isEmployee) {
            UUID currentUserImmunizationUnitId = SecurityUtils.getImmunizationUnitId();
            Filter<UUID> immunizationUnitIdFilter = new UUIDFilter().setEquals(currentUserImmunizationUnitId);
            criteria.setImmunizationUnitId((UUIDFilter) immunizationUnitIdFilter);
        }
        Page<FixedScheduleDTO> page = fixedScheduleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/fixed-schedules/all")
    public ResponseEntity<List<FixedScheduleDTO>> getAllFixedSchedulesNoAuth(
            FixedScheduleCriteria criteria,
            @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        Page<FixedScheduleDTO> page = fixedScheduleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @GetMapping("/fixed-schedules/count")
    public ResponseEntity<Long> countFixedSchedules(FixedScheduleCriteria criteria) {
        return ResponseEntity.ok().body(fixedScheduleQueryService.countByCriteria(criteria));
    }


    @GetMapping("/fixed-schedules/{id}")
    public ResponseEntity<FixedScheduleDTO> getFixedSchedule(@PathVariable Long id) {
        Optional<FixedScheduleDTO> fixedScheduleDTO = fixedScheduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fixedScheduleDTO);
    }

    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('ADMINISTRATOR')")
    @DeleteMapping("/fixed-schedules/{id}")
    public ResponseEntity<Void> deleteFixedSchedule(@PathVariable Long id) {
        fixedScheduleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }


    @PostMapping("/fixed-schedules/duplicate-fixed-schedule")
    public ResponseEntity<List<FixedScheduleDTO>> getAllDuplicateFixedSchedules(@RequestBody FixedScheduleDTO fixedScheduleDTO) {
        List<FixedScheduleDTO> fixedScheduleDTOList = fixedScheduleService.getAllFixedSchedulesWithWorkingWeek(fixedScheduleDTO);
        return ResponseEntity.ok(fixedScheduleDTOList);
    }


    @GetMapping("/fixed-schedules/work-schedule/{vaccinationType}/{immunizationUnitId}/{month}/{year}")
    public ResponseEntity<?> getWorkSchedule(@PathVariable UUID immunizationUnitId,@PathVariable String vaccinationType,@PathVariable int month, @PathVariable int year) {
        List<FixedScheduleDTO> fixedScheduleDTOList;
        List<WorkingScheduleResponseVM> workingScheduleResponseVMList = new ArrayList<>();
        if (vaccinationType.equals("TCMR")) {
            fixedScheduleDTOList = fixedScheduleRepository.findAllByIsEnableAndImmunizationUnitIdAndVaccinationType(true, immunizationUnitId, VaccinationType.MO_RONG).stream().map(fixedScheduleMapper::toDto).toList();
        } else if (vaccinationType.equals("TCDV")) {
            fixedScheduleDTOList = fixedScheduleRepository.findAllByIsEnableAndImmunizationUnitIdAndVaccinationType(true, immunizationUnitId, VaccinationType.DICH_VU).stream().map(fixedScheduleMapper::toDto).toList();
        } else {
            fixedScheduleDTOList = fixedScheduleRepository.findAllByIsEnableAndImmunizationUnitId(true, immunizationUnitId).stream().map(fixedScheduleMapper::toDto).toList();
        }
        if (fixedScheduleDTOList.isEmpty()) {
            return ResponseEntity.ok(workingScheduleResponseVMList);
        }
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        YearMonth yearMonth = YearMonth.of(year, Month.of(month));
        logger.info(yearMonth.toString());
        logger.info("length:" + yearMonth.lengthOfMonth());
        List<LocalDate> allDatesInMonth = new ArrayList<>();
        while (!startDate.isAfter(endDate)) {
            allDatesInMonth.add(startDate);
            startDate = startDate.plusDays(1);
        }
        for (int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
            LocalDate date = LocalDate.of(year, month, day);
            logger.info("date:"  + date);
            String dayOfWeekName = date.getDayOfWeek().name();
            logger.info("day of week: " + dayOfWeekName);
            int weekOfMonth = date.get(WeekFields.of(DayOfWeek.MONDAY, 1).weekOfMonth());
            logger.info("week of month: " + weekOfMonth);
            for (FixedScheduleDTO fixedScheduleDTO : fixedScheduleDTOList) {
                if (isMatchingDay(fixedScheduleDTO, day, dayOfWeekName) && isMatchingWeek(fixedScheduleDTO, weekOfMonth)) {
                    WorkingScheduleResponseVM workingSchedule = new WorkingScheduleResponseVM();
                    workingSchedule.setDate(date);
                    workingSchedule.setTitle("LỊCH LÀM VIỆC - " + fixedScheduleDTO.getImmunizationUnit().getName());
                    workingSchedule.setStartTime(fixedScheduleDTO.getStartTime());
                    workingSchedule.setEndTime(fixedScheduleDTO.getEndTime());
                    workingSchedule.setNote(fixedScheduleDTO.getNote());
                    workingSchedule.setFixedScheduleId(fixedScheduleDTO.getId());
                    workingSchedule.setVaccinationType(fixedScheduleDTO.getVaccinationType());
                    workingScheduleResponseVMList.add(workingSchedule);
                }
            }
        }
        return ResponseEntity.ok(workingScheduleResponseVMList);
    }


    @PostMapping("/fixed-schedules/work-schedule/{vaccinationType}")
    public ResponseEntity<?> getWorkSchedule(@PathVariable String vaccinationType, @RequestBody FixedScheduleCalendarRequestVM fixedScheduleCalendarRequestVM) {

        List<FixedScheduleDTO> fixedScheduleDTOList;
        List<WorkingScheduleResponseVM> workingScheduleResponseVMList = new ArrayList<>();
        if (vaccinationType.equals("TCMR")) {
            fixedScheduleDTOList = fixedScheduleRepository.findAllByIsEnableAndImmunizationUnitIdAndVaccinationType(fixedScheduleCalendarRequestVM.getIsEnable(), fixedScheduleCalendarRequestVM.getImmunizationUnitId(), VaccinationType.MO_RONG).stream().map(fixedScheduleMapper::toDto).toList();
        } else if (vaccinationType.equals("TCDV")) {
            fixedScheduleDTOList = fixedScheduleRepository.findAllByIsEnableAndImmunizationUnitIdAndVaccinationType(fixedScheduleCalendarRequestVM.getIsEnable(), fixedScheduleCalendarRequestVM.getImmunizationUnitId(), VaccinationType.DICH_VU).stream().map(fixedScheduleMapper::toDto).toList();
        } else {
            fixedScheduleDTOList = fixedScheduleRepository.findAllByIsEnableAndImmunizationUnitId(fixedScheduleCalendarRequestVM.getIsEnable(), fixedScheduleCalendarRequestVM.getImmunizationUnitId()).stream().map(fixedScheduleMapper::toDto).toList();
        }
        if (fixedScheduleDTOList.isEmpty()) {
            return ResponseEntity.ok(workingScheduleResponseVMList);
        }

        Instant fromDate = fixedScheduleCalendarRequestVM.getFromDate();
        Instant toDate = fixedScheduleCalendarRequestVM.getToDate();

        LocalDate startLocalDate = fromDate.atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endLocalDate = toDate.atZone(ZoneId.systemDefault()).toLocalDate();

        while (!startLocalDate.isAfter(endLocalDate)) {
            for (FixedScheduleDTO fixedScheduleDTO : fixedScheduleDTOList) {
                String dayOfWeekName = startLocalDate.getDayOfWeek().name();
                int weekOfMonth = startLocalDate.get(WeekFields.of(DayOfWeek.MONDAY, 1).weekOfMonth());

                if (isMatchingDay(fixedScheduleDTO, startLocalDate.getDayOfMonth(), dayOfWeekName) && isMatchingWeek(fixedScheduleDTO, weekOfMonth)) {
                    WorkingScheduleResponseVM workingSchedule = new WorkingScheduleResponseVM();
                    workingSchedule.setDate(startLocalDate);
                    workingSchedule.setTitle("LỊCH LÀM VIỆC - " + fixedScheduleDTO.getImmunizationUnit().getName());
                    workingSchedule.setStartTime(fixedScheduleDTO.getStartTime());
                    workingSchedule.setEndTime(fixedScheduleDTO.getEndTime());
                    workingSchedule.setNote(fixedScheduleDTO.getNote());
                    workingSchedule.setFixedScheduleId(fixedScheduleDTO.getId());
                    workingSchedule.setVaccinationType(fixedScheduleDTO.getVaccinationType());
                    workingScheduleResponseVMList.add(workingSchedule);
                }
            }
            startLocalDate = startLocalDate.plusDays(1);
        }
        return ResponseEntity.ok(workingScheduleResponseVMList);
    }


    private boolean isMatchingDay(FixedScheduleDTO fixedScheduleDTO, int date, String dayOfWeekName) {
        if (fixedScheduleDTO.getWorkingDayType() == WorkingDayType.THU_TRONG_TUAN) {

            DayTranslator dayTranslator = new DayTranslator();
            String dayTranslated = dayTranslator.translate(fixedScheduleDTO.getWorkingDay());
            return dayOfWeekName.equals(dayTranslated);
        } else if (fixedScheduleDTO.getWorkingDayType() == WorkingDayType.NGAY_TRONG_THANG) {

            int dayNumber = translateIntTypeFromWorkingDayORWeek(fixedScheduleDTO.getWorkingDay());
            return dayNumber == date;
        }
        return false;
    }

    private Integer translateIntTypeFromWorkingDayORWeek (String workingString) {
        String numericPart = workingString.replaceAll("[^0-9]", "");
        int dayNumber = Integer.parseInt(numericPart);
        logger.info("translate int from working day or week: "+ dayNumber);
        return dayNumber;
    }

    private boolean isMatchingWeek(FixedScheduleDTO fixedScheduleDTO, int weekOfMonth) {

        if (fixedScheduleDTO.getWorkingWeekType() == WorkingWeekType.MAC_DINH) {
            return true;
        } else if (fixedScheduleDTO.getWorkingWeekType() == WorkingWeekType.TUY_CHON) {
            int weekNumber = translateIntTypeFromWorkingDayORWeek(fixedScheduleDTO.getWorkingWeek());
            return weekNumber == weekOfMonth;
        }
        return false;
    }



    @GetMapping("/fixed-schedules/work-schedule/today")
    public ResponseEntity<?> getAllScheduleCurrently() {
        DayTranslator dayTranslator = new DayTranslator();
        Instant now = Instant.now();
        LocalTime currentTime = now.atZone(ZoneId.systemDefault()).toLocalTime();
        logger.info(ZoneId.systemDefault().toString());
        logger.info(currentTime.toString());
        LocalDate date = LocalDate.of(2000, 1, 1);
        Instant beginTime = date.atTime(currentTime).atZone(ZoneId.systemDefault()).toInstant();
        logger.info(date.atTime(LocalTime.of(2, 0)).atZone(ZoneId.systemDefault()).toInstant().toString());
        logger.info(beginTime.toString());
        //today
        LocalDate currentDate = LocalDate.now();
        logger.info("today: " + currentDate);
        //D1, D2
        String dayOfMonth = "D" + currentDate.getDayOfMonth();
        logger.info("day of month: " + dayOfMonth);
        //Mon, ..
        String dayOfWeek = dayTranslator.reverseTranslate(currentDate.getDayOfWeek().name());
        logger.info("day of week: " + dayOfWeek);
        // W1, W2,....
        String weekOfMonth = "W" + date.get(WeekFields.of(DayOfWeek.MONDAY, 1).weekOfMonth());
        logger.info("week of month: " + weekOfMonth);
        List<FixedScheduleDTO> fixedScheduleDTOList = fixedScheduleRepository.findAllEligibleFixedSchedule(dayOfMonth, dayOfWeek, weekOfMonth, beginTime).stream().map(fixedScheduleMapper::toDto).toList();
        return ResponseEntity.ok().body(fixedScheduleDTOList);
    }




}
