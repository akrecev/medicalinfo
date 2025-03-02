package ru.kretsev.fhirservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.kafka.consumer")
@Getter
@Setter
public class KafkaTopicProperties {
    private Topics topics = new Topics();
    private String groupId;

    @Getter
    @Setter
    static class Topics {
        private String patientCreated;
        private String patientUpdated;
    }
}
