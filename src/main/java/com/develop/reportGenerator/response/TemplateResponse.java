package com.develop.reportGenerator.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

public class TemplateResponse {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("creationDate")
    private ZonedDateTime creationDate;

    public TemplateResponse(){}

    public TemplateResponse(Long id, String title, ZonedDateTime creationDate) {
        this.id = id;
        this.title = title;
        this.creationDate = creationDate;
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("creationDate")
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    @JsonProperty("creationDate")
    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
