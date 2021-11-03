package com.develop.reportGenerator.controllers;

import com.develop.reportGenerator.models.Template;
import com.develop.reportGenerator.repositories.TemplateRepository;
import com.develop.reportGenerator.response.TemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
public class TemplateController {

    final TemplateRepository templateRepository;

    @Autowired
    public TemplateController(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ReportController.class);

    @RequestMapping(value = "/uploadTemplate", method = RequestMethod.POST)
    public Template uploadTemplate(@RequestParam("template") MultipartFile template) {
        if(!template.getOriginalFilename().isBlank()) {
            try {
                if(templateRepository.existsTemplateByTitle(template.getOriginalFilename())) {
                    throw new ResponseStatusException(BAD_REQUEST, "Template already exists");
                }
                else {
                    Template templateToUpload = new Template(template.getOriginalFilename(),
                            ZonedDateTime.now(), template.getBytes());
                    templateRepository.save(templateToUpload);
                    return templateToUpload;
                }
            } catch (IOException e) {
                log.error("Failed to read template file", e);
                throw new ResponseStatusException(INTERNAL_SERVER_ERROR);
            }
        }
        else {
            throw new ResponseStatusException(NO_CONTENT, "Template is not set");
        }
    }

    @Transactional
    @RequestMapping(value = "/deleteTemplate", method = RequestMethod.POST)
    public void deleteTemplate(@RequestParam("templateTitle") String templateTitle) {
        if(templateRepository.existsTemplateByTitle(templateTitle)) {
            templateRepository.deleteByTitle(templateTitle);
        }
        else {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find template");
        }
    }

    @GetMapping("/templates")
    public List<TemplateResponse> getTemplates(){
        List<TemplateResponse> templates = new ArrayList<>();
        for(Template template: templateRepository.findAll()) {
            TemplateResponse templateResponse = new TemplateResponse(template.getId(),
                    template.getTitle(), template.getCreationDate());
            templates.add(templateResponse);
        }
        return templates;
    }
}
