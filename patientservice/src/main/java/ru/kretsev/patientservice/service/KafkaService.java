package ru.kretsev.patientservice.service;

public interface KafkaService {
    void sendMessage(String topic, String message);
}
