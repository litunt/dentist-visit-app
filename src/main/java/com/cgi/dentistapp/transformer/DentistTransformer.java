package com.cgi.dentistapp.transformer;

import com.cgi.dentistapp.dto.DentistDto;
import com.cgi.dentistapp.entity.DentistEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DentistTransformer {

    public List<DentistDto> entitiesToDtos(List<DentistEntity> entities) {
        return entities.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public DentistDto entityToDto(DentistEntity entity) {
        DentistDto dto = new DentistDto();
        dto.setId(entity.getId());
        dto.setName(entity.getDentistName());
        return dto;
    }

    public DentistEntity dtoToEntity(DentistDto dto) {
        DentistEntity entity = new DentistEntity();
        entity.setId(dto.getId());
        entity.setDentistName(dto.getName());
        return entity;
    }
}
