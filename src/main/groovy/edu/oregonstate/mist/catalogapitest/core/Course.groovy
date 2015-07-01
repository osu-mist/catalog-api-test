package edu.oregonstate.mist.catalogapitest.core

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Column

import org.hibernate.validator.constraints.NotEmpty

// Classes in the core folder files are java beans, or classes with a few attributes,
// a no-argument constructor, getters and setters for those attributes

// Define Hibernate object-relational mapping to Course Table
@Entity
@Table(name='courses')
public class Course {
    @Id // primary key
    @Column(name='cid')
    final int cid

    @Column(name='CRN')
    @NotEmpty // not null
    final int crn

    @Column(name='courseName')
    @NotEmpty
    String courseName

    @Column(name='instructor')
    @NotEmpty
    String instructor

    @Column(name='day')
    @NotEmpty
    String day

    @Column(name='time')
    @NotEmpty
    String time

    @Column(name='location')
    @NotEmpty
    String location

    // Override equals to implement Serializable
    @Override
    public boolean equals(Object obj) {
        return ((obj instanceof Course) && (((Course) obj).cid == cid))
    }

    // Override hashCode to implement Serializable
    @Override
    public int hashCode() {
        return cid
    }
}