package com.cgi.dentistapp;

import com.cgi.dentistapp.dto.DentistVisitDto;
import com.cgi.dentistapp.service.DentistVisitService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DentistAppRemoveAppointmentTests {

    @Autowired
    private DentistVisitService visitService;

    @Test
    public void testRemoveAppointment() {
        List<DentistVisitDto> currentVisits = visitService.getActiveVisits();
        int currentLength = currentVisits.size();
        DentistVisitDto visit = currentVisits.stream().filter(v -> v.getVisitId() == 3).findFirst().get();
        visitService.deleteVisitById(visit.getVisitId());
        List<DentistVisitDto> updatedVisits = visitService.getActiveVisits();
        int updatedLength = updatedVisits.size();
        assert updatedLength == currentLength - 1;
    }
}
