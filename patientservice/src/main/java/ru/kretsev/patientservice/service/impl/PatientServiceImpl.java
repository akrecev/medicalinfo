package ru.kretsev.patientservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.kretsev.patientservice.dto.PatientDTO;
import ru.kretsev.patientservice.dto.PatientResponseDTO;
import ru.kretsev.patientservice.exception.DataNotFoundException;
import ru.kretsev.patientservice.exception.DuplicateEmailException;
import ru.kretsev.patientservice.mapper.PatientMapper;
import ru.kretsev.patientservice.model.Patient;
import ru.kretsev.patientservice.repository.PatientRepository;
import ru.kretsev.patientservice.service.KafkaService;
import ru.kretsev.patientservice.service.PatientService;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final KafkaService kafkaService;
    private final ObjectMapper objectMapper;
    private final PatientMapper patientMapper;
    private static final String PATIENT_TOPIC_NAME = "patient-created";

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public PatientResponseDTO create(PatientDTO patientDTO) {
        try {
            log.atInfo()
                    .setMessage("Creating patient: {}")
                    .addArgument(() -> patientDTO)
                    .log();

            Patient patient = patientMapper.toEntity(patientDTO);
            Patient savedPatient = patientRepository.save(patient);
            String patientJson = objectMapper.writeValueAsString(savedPatient);

            kafkaService.sendMessage(PATIENT_TOPIC_NAME, patientJson);

            return patientMapper.toResponseDTO(savedPatient);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEmailException("Email already exists: " + patientDTO.email());
        } catch (JsonProcessingException e) {
            log.error("Error mapping patients data to Json: {}", e.getMessage());
            throw new IllegalArgumentException("Failed to mapping patients data to Json", e);
        }
    }

    @Override
    public PatientResponseDTO getById(Long id) {
        log.atInfo()
                .setMessage("Fetching patient by id: {}")
                .addArgument(() -> id)
                .log();

        Patient patient = patientRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException("Patient not found with id: " + id));

        return patientMapper.toResponseDTO(patient);
    }
}
