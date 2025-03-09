package ru.kretsev.patientservice.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@Accessors(chain = true)
@Getter
@Setter
@Builder(toBuilder = true)
@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String fhirId;
}