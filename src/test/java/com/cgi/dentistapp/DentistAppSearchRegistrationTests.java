package com.cgi.dentistapp;

import com.cgi.dentistapp.dto.DentistVisitDto;
import com.cgi.dentistapp.dto.DentistVisitSearchFormDTO;
import com.cgi.dentistapp.service.DentistVisitService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DentistAppSearchRegistrationTests {

    @Autowired
    private DentistVisitService visitService;

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
    public void testSearchByDentist() {
        long dentistId = 3L;
        DentistVisitSearchFormDTO searchFormDTO = new DentistVisitSearchFormDTO();
        searchFormDTO.setDentistId(dentistId);
        List<DentistVisitDto> visits = visitService.searchActiveVisits(searchFormDTO);
        visits.addAll(visitService.searchPreviousVisits(searchFormDTO));
        assert visits.size() == 3;
        assert visits.stream().allMatch(v -> v.getDentist().getId() == dentistId);
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
    public void testSearchByDateAndDentist() {
        long dentistId = 3L;
        DentistVisitSearchFormDTO searchFormDTO = new DentistVisitSearchFormDTO();
        searchFormDTO.setDentistId(dentistId);
        searchFormDTO.setStartDate(LocalDate.of(2021, 11, 15));
        searchFormDTO.setEndDate(LocalDate.of(2021, 11, 16));
        List<DentistVisitDto> visits = visitService.searchActiveVisits(searchFormDTO);
        visits.addAll(visitService.searchPreviousVisits(searchFormDTO));
        assert visits.size() == 2;
        assert visits.stream().allMatch(v -> v.getDentist().getId() == dentistId);
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
    public void testSearchByTime() {
        DentistVisitSearchFormDTO searchFormDTO = new DentistVisitSearchFormDTO();
        searchFormDTO.setStartTime(LocalTime.of(10, 0));
        searchFormDTO.setEndTime(LocalTime.of(15, 0));
        List<DentistVisitDto> visits = visitService.searchActiveVisits(searchFormDTO);
        visits.addAll(visitService.searchPreviousVisits(searchFormDTO));
        assert visits.size() == 3;
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
    public void testSearchByTimeAndDentist() {
        long dentistId = 3L;
        DentistVisitSearchFormDTO searchFormDTO = new DentistVisitSearchFormDTO();
        searchFormDTO.setDentistId(dentistId);
        searchFormDTO.setStartTime(LocalTime.of(10, 0));
        searchFormDTO.setEndTime(LocalTime.of(15, 0));
        List<DentistVisitDto> visits = visitService.searchActiveVisits(searchFormDTO);
        visits.addAll(visitService.searchPreviousVisits(searchFormDTO));
        assert visits.size() == 1;
        assert visits.get(0).getDentist().getId() == dentistId;
        assert visits.get(0).getVisitTime().getMinute() == 30;
        assert visits.get(0).getVisitTime().getHour() == 13;
    }
}
