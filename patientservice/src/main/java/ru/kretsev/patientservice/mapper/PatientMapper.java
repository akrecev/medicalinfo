package ru.kretsev.patientservice.mapper;

import org.mapstruct.Mapper;
import ru.kretsev.patientservice.dto.PatientDTO;
import ru.kretsev.patientservice.dto.PatientResponseDTO;
import ru.kretsev.patientservice.model.Patient;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    Patient toEntity(PatientDTO patientDTO);

    PatientResponseDTO toResponseDTO(Patient patient);
}
