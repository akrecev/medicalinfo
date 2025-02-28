package ru.kretsev.patientservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import ru.kretsev.patientservice.OnCreate;
import ru.kretsev.patientservice.OnUpdate;

public record PatientDTO(
        @NotBlank(groups = OnCreate.class, message = "First name is required") String firstName,
        @NotBlank(groups = OnCreate.class, message = "Last name is required") String lastName,
        @NotBlank(groups = OnCreate.class, message = "Email is required")
                @Null(groups = OnUpdate.class, message = "Email cannot be updated")
                @Email(groups = OnCreate.class, message = "Invalid email format")
                String email) {}
