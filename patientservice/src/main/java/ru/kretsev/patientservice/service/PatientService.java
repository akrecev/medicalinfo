package ru.kretsev.patientservice.service;

import ru.kretsev.patientservice.dto.PatientDTO;
import ru.kretsev.patientservice.dto.PatientResponseDTO;

public interface PatientService {
    PatientResponseDTO create(PatientDTO patientDTO);

    PatientResponseDTO getById(Long id);
}
