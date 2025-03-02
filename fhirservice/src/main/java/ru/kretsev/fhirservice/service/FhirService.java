package ru.kretsev.fhirservice.service;

import java.util.concurrent.CompletableFuture;

public interface FhirService {
    CompletableFuture<String> sendPatientToFhir(String message);

    void updatePatientInFhir(String patientData);
}
