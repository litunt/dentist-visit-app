package com.cgi.dentistapp.transformer;

import com.cgi.dentistapp.dto.DentistVisitDto;
import com.cgi.dentistapp.entity.DentistVisitEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DentistVisitTransformer {

    @Autowired
    private DentistTransformer dentistTransformer;

    public List<DentistVisitDto> entitiesToDtos(List<DentistVisitEntity> entities) {
        return entities.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public DentistVisitDto entityToDto(DentistVisitEntity entity) {
        DentistVisitDto dto = new DentistVisitDto();
        dto.setVisitId(entity.getId());
        dto.setDentist(dentistTransformer.entityToDto(entity.getDentist()));
        dto.setVisitTime(entity.getVisitTime().toLocalTime());
        dto.setVisitDate(entity.getVisitTime().toLocalDate());
        dto.setIsActive(entity.getVisitTime().isAfter(LocalDateTime.now()));
        return dto;
    }

    public DentistVisitEntity dtoToEntity(DentistVisitDto dto) {
        DentistVisitEntity entity = new DentistVisitEntity();
        entity.setId(dto.getVisitId());
        entity.setVisitTime(LocalDateTime.of(dto.getVisitDate(), dto.getVisitTime()));
        entity.setDentist(dentistTransformer.dtoToEntity(dto.getDentist()));
        return entity;
    }
}
