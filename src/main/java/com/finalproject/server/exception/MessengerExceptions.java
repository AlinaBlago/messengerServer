package com.finalproject.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

public class MessengerExceptions {
    private MessengerExceptions() {
    }

    public static ResponseStatusException authorityNotFound(String value) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "User authority " + value + " not defined");
    }

    public static ResponseStatusException stateNotFound(String value) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "User state " + value + " not defined");
    }

    public static ResponseStatusException userNotFound(String email) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "User with email " + email + " not found");
    }

    public static ResponseStatusException userNotFound(long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " not found");
    }


    public static ResponseStatusException duplicateEmail(String email) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email " + email + " already taken");
    }

    public static ResponseStatusException duplicateNickname(String nickname) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nickname " + nickname + " already taken");
    }

    public static ResponseStatusException fileNotFound(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "File with id " + id + " not found");
    }

    public static ResponseStatusException emptyFile() {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is empty");
    }

    public static ResponseStatusException storageOperationFailed(IOException cause) {
        return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File storage operation failed", cause);
    }

//    public static ResponseStatusException noAccessRightsToFile(String subjectEmail, Long fileId) {
//        return new ResponseStatusException(HttpStatus.FORBIDDEN,
//                "User " + subjectEmail + " has no access rights to file " + fileId);
//    }
//
//    public static ResponseStatusException noEditingRightsToFile(String subjectEmail, Long fileId) {
//        return new ResponseStatusException(HttpStatus.FORBIDDEN,
//                "User " + subjectEmail + " has no editing rights to file " + fileId);
//    }


}
