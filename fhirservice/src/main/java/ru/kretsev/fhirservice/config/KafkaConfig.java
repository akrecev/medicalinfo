package ru.kretsev.fhirservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic patientCreatedTopic() {
        return TopicBuilder.name("patient-created").partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic patientUpdatedTopic() {
        return TopicBuilder.name("patient-updated").partitions(1).replicas(1).build();
    }
}
