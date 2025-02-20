package ru.kretsev.patientservice.service.impl;

import org.springframework.stereotype.Service;
import ru.kretsev.patientservice.dto.PatientDTO;
import ru.kretsev.patientservice.dto.PatientResponseDTO;
import ru.kretsev.patientservice.service.PatientService;

@Service
public class PatientServiceImpl implements PatientService {
    @Override
    public PatientResponseDTO create(PatientDTO patientDTO) {
        return null;
    }

    @Override
    public PatientResponseDTO getById(String id) {
        return null;
    }
}
