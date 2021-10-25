package com.develop.reportGenerator.controllers;

import com.develop.reportGenerator.models.Template;
import com.develop.reportGenerator.services.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@RestController
public class TemplateController {

    TemplateService templateService = new TemplateService();

    @RequestMapping(value = "/uploadTemplate", method = RequestMethod.POST)
    public void uploadTemplate(@RequestParam("template") MultipartFile template) {
        if(template.getOriginalFilename() != "") {
            Template templateToUpload;
            try {
                for(Template templateFetch: templateService.findAllTemplates()){
                    if(templateFetch.getTitle().equals(template.getOriginalFilename())){
                        throw new ResponseStatusException(BAD_REQUEST, "Template already exists");
                    }
                }
                templateToUpload = new Template(template.getOriginalFilename(), new Date(), template.getBytes());
                templateService.saveTemplate(templateToUpload);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            throw new ResponseStatusException(NO_CONTENT, "Template is not set");
        }
    }

    @RequestMapping(value = "/deleteTemplate", method = RequestMethod.POST)
    public void deleteTemplate(@RequestParam("templateTitle") String templateTitle) {
        if(templateTitle != null){
            Template templateToDelete = null;
            for(Template template: templateService.findAllTemplates()){
                if(templateTitle.equals(template.getTitle())){
                    templateToDelete = template;
                }
            }
            if(templateToDelete != null) {
                templateService.deleteTemplate(templateToDelete);
            }
            else {
                throw new ResponseStatusException(NOT_FOUND, "Unable to find template");
            }
        }
        else{
            throw new ResponseStatusException(NO_CONTENT, "Template title was null");
        }
    }
}
