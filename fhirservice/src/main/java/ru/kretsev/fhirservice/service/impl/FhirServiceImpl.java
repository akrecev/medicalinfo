package ru.kretsev.fhirservice.service.impl;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.hl7.fhir.r4.model.ContactPoint;
import org.hl7.fhir.r4.model.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.kretsev.fhirservice.dto.PatientDTO;
import ru.kretsev.fhirservice.exception.PatientJsonProcessingException;
import ru.kretsev.fhirservice.service.FhirService;
import ru.kretsev.fhirservice.service.LoggingService;

@Service
public class FhirServiceImpl implements FhirService {
    private final IGenericClient fhirClient;
    private final ObjectMapper objectMapper;
    private final LoggingService loggingService;

    @Autowired
    public FhirServiceImpl(
            FhirContext fhirContext,
            ObjectMapper objectMapper,
            LoggingService loggingService,
            @Value("${fhir.server.url}") String fhirServerUrl) {
        this.fhirClient = fhirContext.newRestfulGenericClient(fhirServerUrl);
        this.objectMapper = objectMapper;
        this.loggingService = loggingService;
    }

    @Override
    @Async("taskExecutor")
    public CompletableFuture<String> sendPatientToFhir(String patientJson) {
        try {
            PatientDTO patientDTO = objectMapper.readValue(patientJson, PatientDTO.class);

            org.hl7.fhir.r4.model.Patient fhirPatient = new org.hl7.fhir.r4.model.Patient();
            fhirPatient.addName().setFamily(patientDTO.lastName()).addGiven(patientDTO.firstName());
            fhirPatient
                    .addTelecom()
                    .setSystem(ContactPoint.ContactPointSystem.EMAIL)
                    .setValue(patientDTO.email());

            MethodOutcome outcome = fhirClient.create().resource(fhirPatient).execute();
            String fhirId = outcome.getId().getIdPart(); // Получаем FHIR ID

            loggingService.logInfo("Patient successfully sent to FHIR server with ID: {}", fhirId);
            return CompletableFuture.completedFuture(fhirId);
        } catch (JsonProcessingException e) {
            loggingService.logError("Error parsing patient JSON: {}", e.getMessage());
            throw new PatientJsonProcessingException("Error parsing patient JSON", e);
        }
    }

    @Override
    @Async("taskExecutor")
    public void updatePatientInFhir(String patientData) {
        try {
            loggingService.logInfo("update patient data to FHIR server: {}", patientData);

            PatientDTO patientDTO = objectMapper.readValue(patientData, PatientDTO.class);
            org.hl7.fhir.r4.model.Patient fhirPatient = fhirClient
                    .read()
                    .resource(org.hl7.fhir.r4.model.Patient.class)
                    .withId(patientDTO.id())
                    .execute();
            fhirPatient.addName().setFamily(patientDTO.lastName()).addGiven(patientDTO.firstName());
            fhirPatient.getNameFirstRep().setFamily(patientDTO.lastName());
            fhirPatient.getNameFirstRep().setGiven(List.of(new StringType(patientDTO.firstName())));
            fhirPatient.getTelecomFirstRep().setValue(patientDTO.email());

            MethodOutcome outcome = fhirClient.update().resource(fhirPatient).execute();

            loggingService.logInfo(
                    "Patient successfully updated in FHIR server with ID: {}",
                    outcome.getId().getIdPart());

        } catch (JsonProcessingException e) {
            loggingService.logError("Error parsing patient JSON: {}", e.getMessage());
            throw new PatientJsonProcessingException("Error parsing patient JSON", e);
        }
    }
}
