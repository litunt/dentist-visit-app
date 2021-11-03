package com.cgi.dentistapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DentistVisitsDisplayDTO {

    List<DentistVisitDto> activeVisits;

    List<DentistVisitDto> previousVisits;
}
