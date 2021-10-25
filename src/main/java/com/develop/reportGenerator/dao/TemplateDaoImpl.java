package com.develop.reportGenerator.dao;

import com.develop.reportGenerator.models.Template;
import com.develop.reportGenerator.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class TemplateDaoImpl implements TemplateDao<Template> {
    @Override
    public Optional<Template> get(Long id) {
        return Optional.ofNullable(HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Template.class, id));
    }

    @Override
    public List<Template> getAll() {
        List<Template> templates = (List<Template>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Template").list();
        return templates;
    }

    @Override
    public void save(Template template) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(template);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(Template template) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(template);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(Template template) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(template);
        tx1.commit();
        session.close();
    }
}
