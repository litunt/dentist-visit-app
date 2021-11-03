package com.cgi.dentistapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DentistVisitSearchFormDTO {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate endDate;

    @DateTimeFormat(pattern = "HH:mm")
    LocalTime startTime;

    @DateTimeFormat(pattern = "HH:mm")
    LocalTime endTime;

    Long dentistId;

    String dentistName;

    List<DentistDto> availableDentists;

    public void setAvailableDentists(List<DentistDto> dentists) {
        availableDentists = dentists;
        availableDentists.sort(Comparator.comparing(DentistDto::getName));
        availableDentists.add(0, null);
    }
}
