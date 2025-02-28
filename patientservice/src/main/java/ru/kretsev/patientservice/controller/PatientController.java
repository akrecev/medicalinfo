package ru.kretsev.patientservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kretsev.patientservice.OnCreate;
import ru.kretsev.patientservice.OnUpdate;
import ru.kretsev.patientservice.dto.PatientDTO;
import ru.kretsev.patientservice.dto.PatientResponseDTO;
import ru.kretsev.patientservice.service.PatientService;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
@Validated
public class PatientController {
    private final PatientService patientService;

    @Operation(
            summary = "Create a new patient",
            description = "Creates a new patient record and returns the created patient details")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "201", description = "Patient created successfully"),
                @ApiResponse(responseCode = "400", description = "Invalid input data")
            })
    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(
            @Validated(OnCreate.class) @RequestBody PatientDTO patientDTO) {
        return new ResponseEntity<>(patientService.create(patientDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Get patient details", description = "Retrieves details of a patient by ID")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Patient found"),
                @ApiResponse(responseCode = "404", description = "Patient not found")
            })
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> getPatient(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getById(id));
    }

    @Operation(
            summary = "Update a patient",
            description = "Updates a patient record and returns the updated patient details")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "201", description = "Patient created successfully"),
                @ApiResponse(responseCode = "400", description = "Invalid input data")
            })
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(
            @PathVariable Long id, @Validated(OnUpdate.class) PatientDTO patientDTO) {
        return ResponseEntity.ok(patientService.update(id, patientDTO));
    }
}
