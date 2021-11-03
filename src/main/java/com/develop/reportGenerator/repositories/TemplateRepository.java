package com.develop.reportGenerator.repositories;

import com.develop.reportGenerator.models.Template;
import com.develop.reportGenerator.response.TemplateResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {
    Optional<Template> findById(Long id);
    List<Template> findAll();
    Template save(Template template);
    void deleteByTitle(String templateTitle);
    boolean existsTemplateByTitle(String templateTitle);
}
