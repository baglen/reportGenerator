package com.develop.reportGenerator.exceptions;

public class TemplateAlreadyExistsException extends RuntimeException {
    public TemplateAlreadyExistsException(String title) {
        super("Template with title: " + title + " already exists");
    }
}
