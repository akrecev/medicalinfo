package ru.kretsev.patientservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    public NewTopic patientTopic() {
        return TopicBuilder.name("patient-created").partitions(1).replicas(1).build();
    }
}
