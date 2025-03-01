package ru.kretsev.patientservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.kretsev.patientservice.exception.DataNotFoundException;
import ru.kretsev.patientservice.service.EntityService;
import ru.kretsev.patientservice.service.LoggingService;

@Service
@RequiredArgsConstructor
public class EntityServiceImpl implements EntityService {
    private final LoggingService loggingService;

    @Override
    @SuppressWarnings("java:S119")
    public <T, ID> T findEntityOrElseThrow(JpaRepository<T, ID> repository, ID id, String errorMessage) {
        return repository.findById(id).orElseThrow(() -> {
            loggingService.logError("{}: id={}", errorMessage, id);
            return new DataNotFoundException(errorMessage);
        });
    }
}
