
package com.develop.reportGenerator.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.wickedsource.docxstamper.replace.typeresolver.image.Image;

import java.util.List;

public class Content {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("image")
    @Expose
    private List<String> image = null;

    private List<Image> images;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getImage() {
        return image;
    }

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
