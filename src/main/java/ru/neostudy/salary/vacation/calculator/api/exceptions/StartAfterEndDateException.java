package ru.neostudy.salary.vacation.calculator.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StartAfterEndDateException extends BadRequestException {
    public StartAfterEndDateException(LocalDate startDate, LocalDate endDate) {
        super(String.format("\"start_date\" must be before or same day as \"end_date\", but given \"%s\" and \"%s\"",
                startDate,
                endDate
        ));
    }

}
