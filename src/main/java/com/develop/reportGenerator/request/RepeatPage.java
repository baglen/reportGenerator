package com.develop.reportGenerator.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RepeatPage {

    @JsonProperty("type")
    private String type;
    @JsonProperty("content")
    private List<Content> content = null;

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("content")
    public List<Content> getContent() {
        return content;
    }

    @JsonProperty("content")
    public void setContent(List<Content> content) {
        this.content = content;
    }

}
