package com.cgi.dentistapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DentistVisitFormDTO {

    @NotNull
    Long dentistId;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate visitDate;

    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    LocalTime visitTime;

    List<DentistDto> availableDentists;

    List<String> errors = new ArrayList<>();

    public void addError(String error) {
        errors.add(error);
    }
}
