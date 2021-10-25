package com.develop.reportGenerator.services;

import com.develop.reportGenerator.dao.TemplateDaoImpl;
import com.develop.reportGenerator.models.Template;

import java.util.List;
import java.util.Optional;

public class TemplateService {

    private TemplateDaoImpl templateDao = new TemplateDaoImpl();

    public TemplateService(){}

    public Optional<Template> findTemplate(Long id) {
        return templateDao.get(id);
    }

    public void saveTemplate(Template template) {
        templateDao.save(template);
    }

    public void deleteTemplate(Template template) {
        templateDao.delete(template);
    }

    public void updateTemplate(Template template) {
        templateDao.update(template);
    }

    public List<Template> findAllTemplates() {
        return templateDao.getAll();
    }
}
