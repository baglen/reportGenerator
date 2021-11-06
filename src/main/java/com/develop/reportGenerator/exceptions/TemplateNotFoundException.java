package com.develop.reportGenerator.exceptions;

public class TemplateNotFoundException extends RuntimeException {

    public TemplateNotFoundException(Long id) {
        super("Template with id: " + id + " not found");
    }

    public TemplateNotFoundException(String title) {
        super("Template with title: " + title + " not found");
    }
}
