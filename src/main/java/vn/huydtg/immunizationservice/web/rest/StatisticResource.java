package vn.huydtg.immunizationservice.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.huydtg.immunizationservice.domain.Customer;
import vn.huydtg.immunizationservice.repository.AppointmentCardRepository;
import vn.huydtg.immunizationservice.repository.CustomerRepository;
import vn.huydtg.immunizationservice.service.AppointmentCardQueryService;
import vn.huydtg.immunizationservice.service.AppointmentCardService;
import vn.huydtg.immunizationservice.service.EmployeeService;
import vn.huydtg.immunizationservice.service.dto.AppointmentCardDTO;
import vn.huydtg.immunizationservice.service.dto.UserNormalDTO;
import vn.huydtg.immunizationservice.service.mapper.AppointmentCardMapper;
import vn.huydtg.immunizationservice.service.mapper.CustomerMapper;
import vn.huydtg.immunizationservice.web.rest.vm.StatisticAppointmentCardForCompareInWeekVM;
import vn.huydtg.immunizationservice.web.rest.vm.StatisticCountNewCustomerInMonthVM;

import java.time.Instant;

import java.util.ArrayList;
import java.util.List;


import java.time.DayOfWeek;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class StatisticResource {

    private static final String ENTITY_NAME = "REST_APPOINTMENT";

    @Value("${spring.application.name}")
    private String applicationName;

    private final AppointmentCardService appointmentCardService;

    private final AppointmentCardRepository appointmentCardRepository;

    private final AppointmentCardMapper appointmentCardMapper;

    private final AppointmentCardQueryService appointmentCardQueryService;

    private final EmployeeService employeeService;

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;



    Logger logger = LoggerFactory.getLogger(AppointmentCardResource.class);
    public StatisticResource(
            AppointmentCardService appointmentCardService,
            AppointmentCardRepository appointmentCardRepository,
            AppointmentCardQueryService appointmentCardQueryService,
            EmployeeService employeeService,
            AppointmentCardMapper appointmentCardMapper,
            CustomerRepository customerRepository,
            CustomerMapper customerMapper
    ) {
        this.appointmentCardService = appointmentCardService;
        this.appointmentCardRepository = appointmentCardRepository;
        this.appointmentCardQueryService = appointmentCardQueryService;
        this.employeeService = employeeService;
        this.appointmentCardMapper = appointmentCardMapper;
        this.customerRepository = customerRepository;
//        this.userRepository = userRepository;
//        this.userMapper = userMapper;
        this.customerMapper = customerMapper;
    }

    @GetMapping("statistics/appointment-cards/compare-last-two-weeks")
    public ResponseEntity<?> getAppointmentCardStatisticForCompareThisWeekToLastWeek() {
        LocalDate currentDate = LocalDate.now();
        LocalDate thisWeekFromDate = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        logger.info("this week from: " + thisWeekFromDate);
        LocalDate thisWeekToDate = currentDate.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        LocalDate lastWeekFromDate = thisWeekFromDate.minusWeeks(1);
        logger.info("last week from: " + lastWeekFromDate);
        LocalDate lastWeekToDate = thisWeekToDate.minusWeeks(1);

        thisWeekToDate = thisWeekToDate.plusDays(1);
        logger.info("this week end: " + thisWeekToDate);
        lastWeekToDate = lastWeekToDate.plusDays(1);
        logger.info("last week end: " + lastWeekToDate);
        Instant thisWeekStartDateInstant = thisWeekFromDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant thisWeekEndDateInstant = thisWeekToDate.atStartOfDay(ZoneId.systemDefault()).minusSeconds(1).toInstant();
        Instant lastWeekStartDateInstant = lastWeekFromDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant lastWeekEndDateInstant = lastWeekToDate.atStartOfDay(ZoneId.systemDefault()).minusSeconds(1).toInstant();

        List<AppointmentCardDTO> appointmentCardDTOListInThisWeek = appointmentCardRepository.findAppointmentCardWithDateRange(thisWeekStartDateInstant, thisWeekEndDateInstant).stream().map(appointmentCardMapper::toDto).toList();
        List<AppointmentCardDTO> appointmentCardDTOListInLastWeek = appointmentCardRepository.findAppointmentCardWithDateRange(lastWeekStartDateInstant, lastWeekEndDateInstant).stream().map(appointmentCardMapper::toDto).toList();
        List<StatisticAppointmentCardForCompareInWeekVM> statisticAppointmentCardForCompareInWeekVMListThisWeek = findListStatisticAppointmentCardForCompareInWeekVMByRange(thisWeekFromDate, thisWeekToDate, appointmentCardDTOListInThisWeek);
        List<StatisticAppointmentCardForCompareInWeekVM> statisticAppointmentCardForCompareInWeekVMListLastWeek = findListStatisticAppointmentCardForCompareInWeekVMByRange(lastWeekFromDate, lastWeekToDate, appointmentCardDTOListInLastWeek);
        List<List<StatisticAppointmentCardForCompareInWeekVM>> result = new ArrayList<>();
        result.add(statisticAppointmentCardForCompareInWeekVMListLastWeek);
        result.add(statisticAppointmentCardForCompareInWeekVMListThisWeek);
        return ResponseEntity.ok().body(result);
    }




    @GetMapping("statistics/customers/count-last-month/{number}")
    public ResponseEntity<?> getNewCustomerInMonthAgo(@PathVariable Long number) {
        LocalDate currentDate = LocalDate.now();
        LocalDate theLast6MonthDate = currentDate.minusMonths(number).with(TemporalAdjusters.firstDayOfMonth());
        LocalDate theNextDay = currentDate.plusDays(1);
        Instant currentDateInstant = theNextDay.atStartOfDay(ZoneId.systemDefault()).minusSeconds(1).toInstant();
        Instant theLast6MonthDateInstant = theLast6MonthDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        List<Customer> customerList = customerRepository.findCustomerByRange(theLast6MonthDateInstant, currentDateInstant);
        List<StatisticCountNewCustomerInMonthVM> results = new ArrayList<>();
        if (!customerList.isEmpty()) {
            List<UserNormalDTO> userNormalDTOList = customerList.stream().map(e -> {
                        UserNormalDTO userNormalDTO = new UserNormalDTO();
                        userNormalDTO.setId(e.getUser().getId());
                        userNormalDTO.setCreatedAt(e.getUser().getCreatedAt());
                        return userNormalDTO;
                    }
            ).toList();
            logger.info(String.valueOf(userNormalDTOList.size()));
            results = findListStatisticCountNewCustomerInMonthVM(theLast6MonthDate, currentDate, userNormalDTOList);
        } else {
            results = findListStatisticCountNewCustomerInMonthVM(theLast6MonthDate, currentDate, new ArrayList<>());
        }
        return ResponseEntity.ok().body(results);
    }




    List<StatisticAppointmentCardForCompareInWeekVM> findListStatisticAppointmentCardForCompareInWeekVMByRange(LocalDate fromDate, LocalDate toDate, List<AppointmentCardDTO> appointmentCardDTOList) {
        List<StatisticAppointmentCardForCompareInWeekVM> statisticAppointmentCardForCompareInWeekVMListThisWeek = new ArrayList<>();
        LocalDate runDate = fromDate;
        if (appointmentCardDTOList.isEmpty()) {
            while (!runDate.isAfter(toDate)) {
                StatisticAppointmentCardForCompareInWeekVM statisticVM = new StatisticAppointmentCardForCompareInWeekVM();
                statisticVM.setDate(runDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                statisticVM.setListAppointmentCardId(new ArrayList<>());
                statisticVM.setCount(0L);
                statisticAppointmentCardForCompareInWeekVMListThisWeek.add(statisticVM);
                runDate = runDate.plusDays(1);
            }
        } else {
            while (!runDate.isAfter(toDate)) {
                LocalDate finalRunDate = runDate;
                List<AppointmentCardDTO> appointmentsForCurrentDate = appointmentCardDTOList.stream()
                        .filter(appointmentCardDTO -> finalRunDate.equals(
                                appointmentCardDTO.getAppointmentDateConfirmed().atZone(ZoneId.systemDefault()).toLocalDate()))
                        .toList();
                StatisticAppointmentCardForCompareInWeekVM statisticVM = new StatisticAppointmentCardForCompareInWeekVM();
                statisticVM.setDate(runDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                statisticVM.setListAppointmentCardId(appointmentsForCurrentDate.stream()
                        .map(AppointmentCardDTO::getId)
                        .collect(Collectors.toList()));
                statisticVM.setCount((long) appointmentsForCurrentDate.size());
                statisticAppointmentCardForCompareInWeekVMListThisWeek.add(statisticVM);
                runDate = runDate.plusDays(1);
            }
        }

        return statisticAppointmentCardForCompareInWeekVMListThisWeek;
    }

    List<StatisticCountNewCustomerInMonthVM> findListStatisticCountNewCustomerInMonthVM(LocalDate fromDate, LocalDate toDate, List<UserNormalDTO> userNormalDTOList) {
        List<StatisticCountNewCustomerInMonthVM> statisticCountNewCustomerInMonthVMList = new ArrayList<>();
        LocalDate runDate = fromDate;
        if (userNormalDTOList.isEmpty()) {
            while (!runDate.isAfter(toDate)) {
                LocalDate endOfMonth = runDate.with(TemporalAdjusters.lastDayOfMonth());
                StatisticCountNewCustomerInMonthVM statisticCountNewCustomerInMonthVM = new StatisticCountNewCustomerInMonthVM(
                        endOfMonth.getYear() ,endOfMonth.getMonth(), 0L);
                statisticCountNewCustomerInMonthVMList.add(statisticCountNewCustomerInMonthVM);
                runDate = runDate.plusMonths(1);
            }
        }
        while (!runDate.isAfter(toDate)) {
            LocalDate finalRunDate = runDate;

            List<UserNormalDTO> resultListInMonth = userNormalDTOList.stream()
                    .filter(userNormalDTO -> finalRunDate.getYear() == userNormalDTO.getCreatedAt().atZone(ZoneId.systemDefault()).toLocalDate().getYear() &&
                            finalRunDate.getMonth() == userNormalDTO.getCreatedAt().atZone(ZoneId.systemDefault()).toLocalDate().getMonth())
                    .toList();
            LocalDate endOfMonth = runDate.with(TemporalAdjusters.lastDayOfMonth());
            StatisticCountNewCustomerInMonthVM statisticCountNewCustomerInMonthVM = new StatisticCountNewCustomerInMonthVM(
                    endOfMonth.getYear() ,endOfMonth.getMonth(), (long) resultListInMonth.size());
            statisticCountNewCustomerInMonthVMList.add(statisticCountNewCustomerInMonthVM);
            runDate = runDate.plusMonths(1);
        }

        return  statisticCountNewCustomerInMonthVMList;

    }

}
