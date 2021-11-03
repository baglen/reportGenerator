
package com.develop.reportGenerator.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.wickedsource.docxstamper.replace.typeresolver.image.Image;

import java.util.List;

public class Content {

    @JsonProperty("text")
    private String text;
    @JsonProperty("image")
    private List<String> image = null;

    private List<Image> images;

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty("image")
    public List<String> getImage() {
        return image;
    }

    @JsonProperty("image")
    public void setImage(List<String> image) {
        this.image = image;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
