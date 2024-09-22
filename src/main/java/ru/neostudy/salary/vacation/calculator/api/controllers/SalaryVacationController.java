package ru.neostudy.salary.vacation.calculator.api.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.neostudy.salary.vacation.calculator.api.exceptions.BadRequestException;
import ru.neostudy.salary.vacation.calculator.api.exceptions.StartAfterEndDateException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@RestController
public class SalaryVacationController {
    public static final String CALC_VACATION_SALARY = "/api/calc_vacation_salary";


    @GetMapping(value = CALC_VACATION_SALARY, params = {"average_month_salary", "start_date", "end_date"})
    public Double calcVacationSalary(
            @RequestParam(value = "average_month_salary") double averageMonthSalary,
            @RequestParam(value = "start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1; // +1 includes start day

        if (days <= 0) throw new StartAfterEndDateException(startDate, endDate);

        return days * averageMonthSalary;
    }


    @GetMapping(value = CALC_VACATION_SALARY, params = {"average_month_salary", "day_count"})
    public Double calcVacationSalary(
            @RequestParam(value = "average_month_salary") double averageMonthSalary,
            @RequestParam(value = "day_count") Integer dayCount) {

        if (dayCount <= 0) throw new BadRequestException(
                String.format("day_count must be positive, but given \"%s\"",
                        dayCount
                ));

        return averageMonthSalary * dayCount;
    }
}
