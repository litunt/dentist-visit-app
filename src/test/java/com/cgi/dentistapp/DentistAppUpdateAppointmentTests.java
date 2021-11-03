package com.cgi.dentistapp;

import com.cgi.dentistapp.dto.DentistVisitDto;
import com.cgi.dentistapp.service.DentistVisitService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DentistAppUpdateAppointmentTests {

    @Autowired
    private DentistVisitService visitService;

    @Test
    public void testUpdateAppointment() {
        List<DentistVisitDto> currentVisits = visitService.getActiveVisits();
        DentistVisitDto visit = currentVisits.stream().filter(v -> v.getVisitId() == 1).findFirst().get();
        assert visit.getVisitTime().getHour() == 15 && visit.getVisitTime().getMinute() == 30;
        visit.setVisitTime(LocalTime.of(14, 20));
        visitService.updateVisit(visit);
        List<DentistVisitDto> visits = visitService.getActiveVisits();
        DentistVisitDto updatedVisit = visits.stream().filter(v -> v.getVisitId() == 1).findFirst().get();
        assert updatedVisit.getVisitTime().getHour() == 14 && updatedVisit.getVisitTime().getMinute() == 20;
    }
}
