package com.cgi.dentistapp.service;

import com.cgi.dentistapp.dto.DentistDto;
import com.cgi.dentistapp.repository.DentistRepository;
import com.cgi.dentistapp.transformer.DentistTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DentistService {

    @Autowired
    private DentistRepository dentistRepository;

    @Autowired
    private DentistTransformer transformer;

    public List<DentistDto> getDentists() {
        return transformer.entitiesToDtos(dentistRepository.findAll());
    }

    public DentistDto getDentistById(Long id) {
        return transformer.entityToDto(dentistRepository.findOne(id));
    }
}
