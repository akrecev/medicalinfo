package ru.kretsev.patientservice.service.impl;

import static java.lang.String.format;

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
import ru.kretsev.patientservice.exception.DuplicateEmailException;
import ru.kretsev.patientservice.mapper.PatientMapper;
import ru.kretsev.patientservice.model.Patient;
import ru.kretsev.patientservice.repository.PatientRepository;
import ru.kretsev.patientservice.service.EntityService;
import ru.kretsev.patientservice.service.KafkaService;
import ru.kretsev.patientservice.service.PatientService;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final KafkaService kafkaService;
    private final EntityService entityService;
    private final ObjectMapper objectMapper;
    private final PatientMapper patientMapper;
    private static final String PATIENT_CREATED_TOPIC = "patient-created";
    private static final String PATIENT_UPDATED_TOPIC = "patient-updated";

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

            kafkaService.sendMessage(PATIENT_CREATED_TOPIC, patientJson);

            return patientMapper.toResponseDTO(savedPatient);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEmailException("Email already exists: " + patientDTO.email());
        } catch (JsonProcessingException e) {
            log.error("Error mapping patients data to Json: {}", e.getMessage());
            throw new IllegalArgumentException("Failed to mapping patients data to Json", e);
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public PatientResponseDTO update(Long id, PatientDTO patientDTO) {
        try {
            log.atInfo()
                    .setMessage("Updating patient with id {}: {}")
                    .addArgument(() -> id)
                    .addArgument(() -> patientDTO)
                    .log();

            Patient patient = takePatient(id);

            if (patientDTO.firstName() != null) {
                patient.setFirstName(patientDTO.firstName());
            }
            if (patientDTO.lastName() != null) {
                patient.setLastName(patientDTO.lastName());
            }

            Patient updatedPatient = patientRepository.save(patient);
            String patientJson = objectMapper.writeValueAsString(updatedPatient);

            kafkaService.sendMessage(PATIENT_UPDATED_TOPIC, patientJson);

            return patientMapper.toResponseDTO(updatedPatient);
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

        Patient patient = takePatient(id);

        return patientMapper.toResponseDTO(patient);
    }

    private Patient takePatient(Long id) {
        return entityService.findEntityOrElseThrow(patientRepository, id, format("Patient with id:%d not found", id));
    }
}
