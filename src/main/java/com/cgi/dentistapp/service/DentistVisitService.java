package com.cgi.dentistapp.service;

import com.cgi.dentistapp.dto.DentistDto;
import com.cgi.dentistapp.dto.DentistVisitDto;
import com.cgi.dentistapp.dto.DentistVisitSearchFormDTO;
import com.cgi.dentistapp.entity.DentistEntity;
import com.cgi.dentistapp.entity.DentistVisitEntity;
import com.cgi.dentistapp.exceptions.DentistVisitTimeOverlappingException;
import com.cgi.dentistapp.repository.DentistRepository;
import com.cgi.dentistapp.repository.DentistVisitRepository;
import com.cgi.dentistapp.transformer.DentistTransformer;
import com.cgi.dentistapp.transformer.DentistVisitTransformer;
import com.cgi.dentistapp.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DentistVisitService {

    @Autowired
    private DentistVisitTransformer dentistVisitTransformer;
    @Autowired
    private DentistTransformer dentistTransformer;

    @Autowired
    private DentistVisitRepository dentistVisitRepository;

    @Autowired
    private DentistRepository dentistRepository;

    public void addVisit(Long dentistId, LocalDate visitDate, LocalTime visitTime) throws DentistVisitTimeOverlappingException {
        DentistVisitEntity dentistVisit = new DentistVisitEntity();
        if (!hasOverlappingVisitTimes(dentistId, visitDate, visitTime)) {
            LocalDateTime visitDateTime = LocalDateTime.of(visitDate, visitTime);
            DentistDto dentistDto = dentistTransformer.entityToDto(dentistRepository.getOne(dentistId));
            DentistEntity entity = dentistTransformer.dtoToEntity(dentistDto);
            dentistVisit.setDentist(entity);
            dentistVisit.setVisitTime(visitDateTime);
            dentistVisitRepository.save(dentistVisit);
        } else {
            throw new DentistVisitTimeOverlappingException("Chosen visit time overlaps with dentist's other appointments!");
        }
    }

    public List<DentistVisitDto> getActiveVisits() {
        return dentistVisitTransformer.entitiesToDtos(dentistVisitRepository.findAllActive());
    }

    public List<DentistVisitDto> getPreviousVisits() {
        return dentistVisitTransformer.entitiesToDtos(dentistVisitRepository.findAllPrevious());
    }

    public DentistVisitDto getVisitById(Long visitId) {
        return dentistVisitTransformer.entityToDto(dentistVisitRepository.findOne(visitId));
    }

    public List<DentistVisitDto> searchActiveVisits(DentistVisitSearchFormDTO searchForm) {
        LocalDate startDate = searchForm.getStartDate();
        LocalTime startTime = searchForm.getStartTime();
        LocalDate endDate = searchForm.getEndDate();
        LocalTime endTime = searchForm.getEndTime();

        List<DentistVisitEntity> allVisits;

        if (searchForm.getDentistId() != null) {
            allVisits = dentistVisitRepository.findAllActiveByDentistId(searchForm.getDentistId());
        } else {
            allVisits = dentistVisitRepository.findAllActive();
        }

        List<DentistVisitEntity> visits = filterByDate(startDate, endDate, allVisits);
        visits = filterByTime(startTime, endTime, visits);
        return dentistVisitTransformer.entitiesToDtos(visits);
    }

    public List<DentistVisitDto> searchPreviousVisits(DentistVisitSearchFormDTO searchForm) {
        LocalDate startDate = searchForm.getStartDate();
        LocalTime startTime = searchForm.getStartTime();
        LocalDate endDate = searchForm.getEndDate();
        LocalTime endTime = searchForm.getEndTime();

        List<DentistVisitEntity> allVisits;

        if (searchForm.getDentistId() != null) {
            allVisits = dentistVisitRepository.findAllPreviousByDentistId(searchForm.getDentistId());
        } else {
            allVisits = dentistVisitRepository.findAllPrevious();
        }

        List<DentistVisitEntity> visits = filterByDate(startDate, endDate, allVisits);
        visits = filterByTime(startTime, endTime, visits);
        return dentistVisitTransformer.entitiesToDtos(visits);
    }

    public void deleteVisitById(Long visitId) {
        dentistVisitRepository.delete(visitId);
    }

    public DentistVisitDto updateVisit(DentistVisitDto visitDto) {
        DentistVisitEntity entity = dentistVisitRepository.findOne(visitDto.getVisitId());
        entity.setVisitTime(LocalDateTime.of(visitDto.getVisitDate(), visitDto.getVisitTime()));
        dentistVisitRepository.save(entity);
        return dentistVisitTransformer.entityToDto(dentistVisitRepository.findOne(visitDto.getVisitId()));
    }

    private boolean hasOverlappingVisitTimes(Long dentistId, LocalDate newDate, LocalTime newTime) {
        boolean hasOverlap = false;
        List<DentistVisitEntity> registeredVisits = dentistVisitRepository.findAllActiveByDentistId(dentistId);
        for (DentistVisitEntity dve : registeredVisits) {
            LocalDateTime dbDateTime = dve.getVisitTime();
            if (DateTimeUtils.compare(dbDateTime, newDate)) {
                hasOverlap = DateTimeUtils.compare(dbDateTime, newTime) || DateTimeUtils.timeOverlap(dbDateTime, newTime);
            }
        }
        return hasOverlap;
    }

    private List<DentistVisitEntity> filterByDate(LocalDate start, LocalDate end, List<DentistVisitEntity> visits) {
        return visits.stream()
                .filter(v -> DateTimeUtils.isBetween(v.getVisitTime().toLocalDate(), start, end))
                .collect(Collectors.toList());
    }

    private List<DentistVisitEntity> filterByTime(LocalTime start, LocalTime end, List<DentistVisitEntity> visits) {
        return visits.stream()
                .filter(v -> DateTimeUtils.isBetween(v.getVisitTime().toLocalTime(), start, end))
                .collect(Collectors.toList());
    }
}
