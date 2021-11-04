package com.develop.reportGenerator.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.ZonedDateTime;

public class TemplateResponse {

    private Long id;
    private String title;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private ZonedDateTime creationDate;

    public TemplateResponse() {
    }

    public TemplateResponse(Long id, String title, ZonedDateTime creationDate) {
        this.id = id;
        this.title = title;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

}
