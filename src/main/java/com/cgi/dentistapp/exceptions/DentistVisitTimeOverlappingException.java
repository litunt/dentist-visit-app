package com.cgi.dentistapp.exceptions;

public class DentistVisitTimeOverlappingException extends Exception {

    public DentistVisitTimeOverlappingException(String errorMessage) {
        super(errorMessage);
    }
}
