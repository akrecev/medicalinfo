package ru.kretsev.patientservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.kretsev.patientservice.exception.DataNotFoundException;
import ru.kretsev.patientservice.service.EntityService;

@Slf4j
@Service
public class EntityServiceImpl implements EntityService {
    @Override
    @SuppressWarnings("java:S119")
    public <T, ID> T findEntityOrElseThrow(JpaRepository<T, ID> repository, ID id, String errorMessage) {
        return repository.findById(id).orElseThrow(() -> {
            log.error("{}: id={}", errorMessage, id);
            return new DataNotFoundException(errorMessage);
        });
    }
}
