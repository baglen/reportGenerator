package com.develop.reportGenerator.dao;

import java.util.List;
import java.util.Optional;

interface TemplateDao<T> {

    Optional<T> get(Long id);

    List<T> getAll();

    void save(T t);

    void update(T t);

    void delete(T t);
}
