package edu.oregonstate.mist.dropwizardtest.db

import edu.oregonstate.mist.dropwizardtest.core.Employee

import io.dropwizard.hibernate.AbstractDAO
import com.google.common.base.Optional
import org.hibernate.SessionFactory

/* Hibernate data access object representing an employee */
class EmployeeDAO extends AbstractDAO<Employee> {

    public EmployeeDAO(SessionFactory factory) {
        super(factory)
    }

    /* read */
    public Optional<Employee> findById(Long id) {
        return Optional.fromNullable(get(id))
    }

    public List<Employee> findAll() {
        return list(namedQuery('edu.oregonstate.mist.core.Employee.findAll'))
    }

    /* create, update */
    public Employee set(Employee employee) {
        return persist(employee)
    }
}
