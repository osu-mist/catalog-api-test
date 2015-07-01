package edu.oregonstate.mist.catalogapitest.db

import Instructor

import io.dropwizard.hibernate.AbstractDAO
import com.google.common.base.Optional
import org.hibernate.SessionFactory

// Hibernate data access object representing a instructor
class InstructorDAO extends AbstractDAO<Instructor> {

    public InstructorDAO(SessionFactory factory) {
        super(factory)
    }

    /* read */
    public Optional<Instructor> findByName(Long name) {
        return Optional.fromNullable(get(name))
    }

    public List<Instructor> findAll() {
        return list(namedQuery('edu.oregonstate.mist.core.Instructor.findAll'))
    }

    /* create, update */
    public Instructor set(Instructor instructor) {
        return persist(instructor)
    }
}