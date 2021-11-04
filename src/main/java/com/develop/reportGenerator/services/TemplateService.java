package com.develop.reportGenerator.services;

import com.develop.reportGenerator.response.TemplateResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface TemplateService {
    TemplateResponse uploadTemplate(MultipartFile template);

    void deleteTemplate(String templateTitle);

    List<TemplateResponse> getTemplates();
}
