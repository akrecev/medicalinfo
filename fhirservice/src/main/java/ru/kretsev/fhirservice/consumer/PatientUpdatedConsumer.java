package ru.kretsev.fhirservice.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.kretsev.fhirservice.config.KafkaTopicProperties;
import ru.kretsev.fhirservice.service.FhirService;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientUpdatedConsumer {
    private final FhirService fhirService;
    private final KafkaTopicProperties kafkaTopicProperties;

    @KafkaListener(
            topics = "#{kafkaTopicProperties.topics.patientUpdated}",
            groupId = "#{kafkaTopicProperties.groupId}")
    public void listen(String message) {
        log.info("Received message: {}", message);
        fhirService.updatePatientInFhir(message);
    }
}
