package com.cgi.dentistapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DentistVisitDto {

    @NotNull
    Long visitId;

    DentistDto dentist;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate visitDate;

    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    LocalTime visitTime;

    Boolean isActive;

    List<String> errors;

}
