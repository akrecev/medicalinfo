package ru.kretsev.patientservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.kretsev.patientservice.service.KafkaService;

@Service
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    @Async("taskExecutor")
    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}
