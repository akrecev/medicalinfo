package ru.kretsev.patientservice.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {
    private final KafkaTopicProperties kafkaTopicProperties;

    @Bean
    public NewTopic patientCreatedTopic() {
        return TopicBuilder.name(kafkaTopicProperties.getPatientCreated())
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic patientUpdatedTopic() {
        return TopicBuilder.name(kafkaTopicProperties.getPatientUpdated())
                .partitions(1)
                .replicas(1)
                .build();
    }
}
