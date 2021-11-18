package com.cgi.dentistapp.controller;

import com.cgi.dentistapp.dto.*;
import com.cgi.dentistapp.exceptions.DentistVisitTimeOverlappingException;
import com.cgi.dentistapp.service.DentistService;
import com.cgi.dentistapp.service.DentistVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@EnableAutoConfiguration
public class DentistAppController extends WebMvcConfigurerAdapter {

    @Autowired
    private DentistVisitService dentistVisitService;

    @Autowired
    private DentistService dentistService;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
        registry.addViewController("/details").setViewName("details");
        registry.addViewController("/appointments-search-result").setViewName("appointments-search-result");
    }

    @GetMapping("/")
    public String showRegisterForm(Model model) {
        DentistVisitFormDTO form = new DentistVisitFormDTO();
        form.setAvailableDentists(dentistService.getDentists());
        model.addAttribute("form", form);
        return "form";
    }

    @GetMapping("/appointments")
    public String showAppointments(Model model) {
        DentistVisitsDisplayDTO display = new DentistVisitsDisplayDTO();
        display.setActiveVisits(dentistVisitService.getActiveVisits());
        display.setPreviousVisits(dentistVisitService.getPreviousVisits());

        DentistVisitSearchFormDTO form = new DentistVisitSearchFormDTO();
        form.setAvailableDentists(dentistService.getDentists());

        model.addAttribute("appointments", display);
        model.addAttribute("searchForm", form);
        return "appointments";
    }

    @GetMapping("/visit/{visitId}")
    public String showVisitDetails(RedirectAttributes model, @PathVariable Long visitId) {
        DentistVisitDto dto = dentistVisitService.getVisitById(visitId);
        model.addFlashAttribute("visitDetails", dto);
        return "redirect:/details";
    }

    @PostMapping("/search")
    public String searchAppointments(@ModelAttribute("searchForm") DentistVisitSearchFormDTO searchForm,
                                     RedirectAttributes model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "appointments";
        }
        DentistVisitsDisplayDTO display = new DentistVisitsDisplayDTO();
        display.setActiveVisits(dentistVisitService.searchActiveVisits(searchForm));
        display.setPreviousVisits(dentistVisitService.searchPreviousVisits(searchForm));

        if (searchForm.getDentistId() != null) {
            searchForm.setDentistName(dentistService.getDentistById(searchForm.getDentistId()).getName());
        }

        model.addFlashAttribute("appointments", display);
        model.addFlashAttribute("searchFormParams", searchForm);
        return "redirect:/appointments-search-result";
    }

    @PostMapping("/")
    public String postRegisterForm(@Valid @ModelAttribute("form") DentistVisitFormDTO dentistVisitDTO,
                                   BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "form";
        }

        try {
            dentistVisitService.addVisit(dentistVisitDTO.getDentistId(), dentistVisitDTO.getVisitDate(), dentistVisitDTO.getVisitTime());
        } catch (DentistVisitTimeOverlappingException ex) {
            dentistVisitDTO.addError(ex.getMessage());
            dentistVisitDTO.setAvailableDentists(dentistService.getDentists());
            model.addAttribute("form", dentistVisitDTO);
            return "form";
        }
        return "redirect:/results";
    }

    @PostMapping("/visit/remove")
    public String deleteVisit(@ModelAttribute("visitDetails") DentistVisitDto visitDto,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/details";
        }

        dentistVisitService.deleteVisitById(visitDto.getVisitId());
        return "redirect:/appointments";
    }

    @PostMapping("/visit/update")
    public String updateVisit(@Valid @ModelAttribute("visitDetails") DentistVisitDto visitDto,
                              BindingResult bindingResult, RedirectAttributes model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/details";
        }

        DentistVisitDto dto = dentistVisitService.updateVisit(visitDto);
        model.addFlashAttribute("visitDetails", dto);
        return "redirect:/details";
    }
}
