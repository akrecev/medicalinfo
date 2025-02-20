package ru.kretsev.patientservice.service;

import org.springframework.http.ProblemDetail;
import ru.kretsev.patientservice.dto.PatientDTO;
import ru.kretsev.patientservice.dto.PatientResponseDTO;

import java.util.Optional;

public interface PatientService {
    PatientResponseDTO create(PatientDTO patientDTO);

    PatientResponseDTO getById(String id);
}
