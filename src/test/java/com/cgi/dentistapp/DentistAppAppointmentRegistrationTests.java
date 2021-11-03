package com.cgi.dentistapp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cgi.dentistapp.dto.DentistVisitDto;
import com.cgi.dentistapp.dto.DentistVisitFormDTO;
import com.cgi.dentistapp.exceptions.DentistVisitTimeOverlappingException;
import com.cgi.dentistapp.service.DentistVisitService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DentistAppAppointmentRegistrationTests {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private DentistVisitService visitService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testRegisterVisit() throws Exception {
        DentistVisitFormDTO formDTO = new DentistVisitFormDTO();
        formDTO.setDentistId(7L);
        formDTO.setVisitTime(LocalTime.of(13, 30));
        formDTO.setVisitDate(LocalDate.of(2021, 12, 14));
        List<DentistVisitDto> currentVisits = visitService.getActiveVisits();
        int currentSize = currentVisits.size();
        visitService.addVisit(3L, LocalDate.of(2021, 12, 14), LocalTime.of(13, 30));
        this.mockMvc.perform(post("/", formDTO))
                .andExpect(status().isOk())
                .andExpect(view().name("form"));
        List<DentistVisitDto> updatedVisits = visitService.getActiveVisits();
        int updatedSize = updatedVisits.size();
        assert updatedSize == currentSize + 1;
    }

    @Test(expected = DentistVisitTimeOverlappingException.class)
    public void textRegisterVisitWithOverlappingTime() throws Exception {
        visitService.addVisit(3L, LocalDate.of(2021, 11, 16), LocalTime.of(13, 10));
    }
}
