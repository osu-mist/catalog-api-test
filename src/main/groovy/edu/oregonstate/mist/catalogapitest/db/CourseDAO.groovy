package edu.oregonstate.mist.catalogapitest.db

import Course

import io.dropwizard.hibernate.AbstractDAO
import com.google.common.base.Optional
import org.hibernate.SessionFactory

// Hibernate data access object representing a course
class CourseDAO extends AbstractDAO<Course> {

    public CourseDAO(SessionFactory factory) {
        super(factory)
    }

    public Optional<Course> findById(Long id) {
        return Optional.fromNullable(get(id))
    }

    public Optional<Course> findByCRN(Long crn) {
        return Optional.fromNullable(get(crn))
    }

    public Optional<Course> findByName(String name) {
        return Optional.fromNullable(get(name))
    }

    public Optional<Course> findByLocation(String location) {
        return Optional.fromNullable(get(location))
    }

    public List<Course> findAll() {
        return list(namedQuery('edu.oregonstate.mist.core.Course.findAll'))
    }

    public List<Course> findAllLocations() {
        return list(namedQuery('edu.oregonstate.mist.core.Course.location.findAll'))
    }

    public List<Course> findAllInstructors() {
        return list(namedQuery('edu.oregonstate.mist.core.Course.instructor.findAll'))
    }

    /* create, update */
    public Course set(Course course) {
        return persist(course)
    }
}