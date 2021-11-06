package com.develop.reportGenerator.services;

import com.develop.reportGenerator.response.TemplateResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;


public interface TemplateService {
    TemplateResponse uploadTemplate(MultipartFile template) throws FileNotFoundException;

    void deleteTemplate(String templateTitle);

    List<TemplateResponse> getTemplates();
}
