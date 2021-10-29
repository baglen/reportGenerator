package com.develop.reportGenerator.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.time.ZonedDateTime;


@Entity
@Table(name = "template")
public class Template {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    @Column(name = "creation_date")
    private ZonedDateTime creationDate;
    @Column(name = "file")
    private byte[] fileBytes;

    public Template() {
    }

    @Bean
    @Scope("prototype")
    public Template template(){
        return new Template();
    }

    public Template(String title, ZonedDateTime creationDate, byte[] fileBytes) {
        this.title = title;
        this.creationDate = creationDate;
        this.fileBytes = fileBytes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public byte[] getFile() {
        return fileBytes;
    }

    public void setFile(byte[] file) {
        this.fileBytes = file;
    }
}
