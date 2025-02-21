package ru.kretsev.patientservice.exception;

import lombok.experimental.StandardException;

@StandardException
@SuppressWarnings("this-escape")
public class DuplicateEmailException extends RuntimeException {}
