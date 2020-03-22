package ru.job4j.cars_storage.web;

public class BadRequestException extends Exception {
    public BadRequestException(String message) {
        super(message);
    }
}
