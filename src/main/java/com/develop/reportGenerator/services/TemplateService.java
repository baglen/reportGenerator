package com.develop.reportGenerator.services;

import com.develop.reportGenerator.models.Template;
import com.develop.reportGenerator.response.TemplateResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface TemplateService {
    Template uploadTemplate(MultipartFile template);
    void deleteTemplate(String templateTitle);
    List<TemplateResponse> getTemplates();
}
