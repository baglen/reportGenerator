package com.develop.reportGenerator.controllers;

import com.develop.reportGenerator.models.Template;
import com.develop.reportGenerator.repositories.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@Component
public class TemplateController {

    final TemplateRepository templateRepository;

    @Autowired
    public TemplateController(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    private final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("scopes.xml");

    @RequestMapping(value = "/uploadTemplate", method = RequestMethod.POST)
    public Template uploadTemplate(@RequestParam("template") MultipartFile template) {
        if(template.getOriginalFilename() != "") {
            Template templateToUpload = applicationContext.getBean("template", Template.class);
            try {
                if(templateRepository.existsTemplateByTitle(template.getOriginalFilename())) {
                    throw new ResponseStatusException(BAD_REQUEST, "Template already exists");
                }
                else {
                    templateToUpload.setTitle(template.getOriginalFilename());
                    templateToUpload.setFile(template.getBytes());
                    templateToUpload.setCreationDate(ZonedDateTime.now());
                    templateRepository.save(templateToUpload);
                    return templateToUpload;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            throw new ResponseStatusException(NO_CONTENT, "Template is not set");
        }
        return null;
    }

    @Transactional
    @RequestMapping(value = "/deleteTemplate", method = RequestMethod.POST)
    public void deleteTemplate(@RequestParam("templateTitle") String templateTitle) {
        if(templateTitle != null){
            if(templateRepository.existsTemplateByTitle(templateTitle)) {
                templateRepository.deleteByTitle(templateTitle);
            }
            else {
                throw new ResponseStatusException(NOT_FOUND, "Unable to find template");
            }
        }
        else{
            throw new ResponseStatusException(NO_CONTENT, "Template title was null");
        }
    }

    @GetMapping("/templates")
    public List<Template> getTemplates(){
        return templateRepository.findAll();
    }
}
