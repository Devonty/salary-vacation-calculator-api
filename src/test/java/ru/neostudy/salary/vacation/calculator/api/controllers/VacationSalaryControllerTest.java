package ru.neostudy.salary.vacation.calculator.api.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.neostudy.salary.vacation.calculator.api.exceptions.BadRequestException;
import static ru.neostudy.salary.vacation.calculator.api.controllers.VacationSalaryController.calcVacationSalary;

class VacationSalaryControllerTest {

    public static final double averageMonthSalary = 30_000d;
    public static final int dayCount = 10;
    public static final double TOLERANCE = 1E-2; // accuracy down to kopecks

    @Test
    void getVacationSalaryDayCount_correct() {
        double expected = 1_023.89 * dayCount;  // calc on https://normativ.kontur.ru/calculators/vacation with salary 30_000
        Assertions.assertEquals(expected, calcVacationSalary(averageMonthSalary, dayCount), TOLERANCE);
    }

    @Test
    void getVacationSalaryDayCount_wrongAverageMonthSalary() {
        try{
            calcVacationSalary(-averageMonthSalary, dayCount);
            Assertions.fail();
        } catch (BadRequestException bre){
            Assertions.assertTrue(bre.getMessage().startsWith("average_month_salary must be positive, but given"));
        }
    }

    @Test
    void getVacationSalaryDayCount_wrongDayCount() {
        try{
            calcVacationSalary(averageMonthSalary, -dayCount);
            Assertions.fail();
        } catch (BadRequestException bre){
            Assertions.assertTrue(bre.getMessage().startsWith("day_count must be positive, but given"));
        }
    }
}