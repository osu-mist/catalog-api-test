package edu.oregonstate.mist.courseapitest.core

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Column

import org.hibernate.validator.constraints.NotEmpty
import org.hibernate.validator.constraints.Email

// Classes in the core folder files are java beans, or classes with a few attributes, 
// a no-argument constructor, getters and setters for those attributes

// Define Hibernate object-relational mapping to Instructor Table
@Entity
@Table(name='instructors')
public class Instructor {
    @Id // primary key
    @Column(name='id')
    final int id

    @Column(name='instructor')
    @NotEmpty
    String instructor

    @Column(name='rateMyProfessorLink')
    @NotEmpty
    String rateMyProfessorLink

    @Column(name='courses_taught')
    @NotEmpty
    String courses_taught

    // Override equals to implement Serializable
    @Override
    public boolean equals(Object obj) {
        return ((obj instanceof Instructor) && (((Instructor) obj).id == id))
    }

    // Override hashCode to implement Serializable
    @Override
    public int hashCode() {
        return id
    }
}