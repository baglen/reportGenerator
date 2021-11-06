package com.develop.reportGenerator.controllers;


import com.develop.reportGenerator.response.TemplateResponse;
import com.develop.reportGenerator.services.TemplateService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
public class TemplateController {

    final TemplateService templateService;

    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @RequestMapping(value = "/uploadTemplate", method = RequestMethod.POST)
    public TemplateResponse uploadTemplate(@RequestParam("template") MultipartFile template)
            throws FileNotFoundException {
        return templateService.uploadTemplate(template);
    }

    @GetMapping(value = "/deleteTemplate")
    public void deleteTemplate(@RequestParam("templateTitle") String templateTitle) {
        templateService.deleteTemplate(templateTitle);
    }

    @GetMapping("/templates")
    public List<TemplateResponse> getTemplates() {
        return templateService.getTemplates();
    }
}
