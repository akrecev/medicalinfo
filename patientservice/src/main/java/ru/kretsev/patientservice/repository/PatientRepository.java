package ru.kretsev.patientservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kretsev.patientservice.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}
