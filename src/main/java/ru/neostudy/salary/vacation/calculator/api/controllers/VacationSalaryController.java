package ru.neostudy.salary.vacation.calculator.api.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.neostudy.salary.vacation.calculator.api.exceptions.BadRequestException;
import ru.neostudy.salary.vacation.calculator.api.exceptions.StartAfterEndDateException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@RestController
public class VacationSalaryController {
    public static final String GET_VACATION_SALARY = "/api/calc_vacation_salary";


    @GetMapping(value = GET_VACATION_SALARY, params = {"average_month_salary", "start_date", "end_date"})
    public Double getVacationSalary(
            @RequestParam(value = "average_month_salary") double averageMonthSalary,
            @RequestParam(value = "start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        long dayCount = ChronoUnit.DAYS.between(startDate, endDate) + 1; // +1 includes start day

        if (dayCount <= 0) throw new StartAfterEndDateException(startDate, endDate);

        return getVacationSalary(averageMonthSalary, dayCount);
    }


    @GetMapping(value = GET_VACATION_SALARY, params = {"average_month_salary", "day_count"})
    public Double getVacationSalary(
            @RequestParam(value = "average_month_salary") double averageMonthSalary,
            @RequestParam(value = "day_count") long dayCount) {

        return calcVacationSalary(averageMonthSalary, dayCount);
    }

    public static double calcVacationSalary(double averageMonthSalary, long dayCount){
        if (dayCount <= 0) throw new BadRequestException(
                String.format("day_count must be positive, but given \"%s\"",
                        dayCount
                ));

        if (averageMonthSalary <= 0d) throw new BadRequestException(
                String.format("average_month_salary must be positive, but given \"%s\"",
                        averageMonthSalary
                ));

        return averageMonthSalary * dayCount / 29.3d;
    }

}
