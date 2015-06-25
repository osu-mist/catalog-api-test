package edu.oregonstate.mist.catalogapitest.resources

import io.dropwizard.auth.Auth
import io.dropwizard.hibernate.UnitOfWork
import io.dropwizard.jersey.params.LongParam

import edu.oregonstate.mist.catalogapitest.*
import edu.oregonstate.mist.catalogapitest.auth.AuthenticatedUser
import edu.oregonstate.mist.catalogapitest.core.Course
import edu.oregonstate.mist.catalogapitest.db.CourseDAO

import javax.ws.rs.OPTIONS
import javax.ws.rs.GET
import javax.ws.rs.PUT
import javax.ws.rs.POST
import javax.ws.rs.DELETE
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.Consumes
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

import com.google.common.base.Optional
import javax.validation.Valid

// Course resource
@Path('courses')
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CourseResource {

    private final CourseDAO courseDAO

    public CourseResource(CourseDAO courseDAO) {
        this.courseDAO = courseDAO
    }

    //---------------------------------------------------------------------------

    // HTTP methods allowed on course url
    @OPTIONS
    public List courseOptions() {
        return ['OPTIONS', 'GET', 'POST']
    }

    // Gets all courses
    @GET
    @UnitOfWork
    public Course getAllCourses() {
        return courseDAO.findAll()
    }

    // Creates a course representation to be appended
    @POST
    @UnitOfWork
    public Course setCourse(@Valid Course course) {
        return courseDAO.set(course)
    }
    
    //---------------------------------------------------------------------------

    // HTTP methods allowed on courses/{crn} urls
    @OPTIONS
    @Path('/{crn: \\d+}')
    public List optionsByCRN() {
        return ['OPTIONS', 'GET', 'PUT', 'DELETE']
    }

    // Finds course by CRN
    @GET
    @Path('/{crn: \\d+}')
    @UnitOfWork
    public Course getByCRN(@PathParam('crn') LongParam crn) {
        final Optional<Course> course = courseDAO.findByCRN(crn.get())

        if (!course.isPresent()) {
            throw new WebApplicationException(Response.Status.NOT_FOUND)
        }

        return course.get()
    }

    // Puts a course resource to be updated or created.
    @PUT
    @Path('/{crn: \\d+}')
    @UnitOfWork
    public Course putByCRN(@PathParam('crn') LongParam crn, @Valid Course course) {
        final Optional<Course> course = courseDAO.findByCRN(crn.get())

        if (!course.isPresent()) {
            throw new WebApplicationException(Response.Status.NOT_FOUND)
        }

        return courseDAO.set(course)
    }

    // Deletes a course resource
    @DELETE
    @Path('/{crn: \\d+}')
    @UnitOfWork
    public Course deleteByCRN(@PathParam('crn') LongParam crn){
        final Optional<Course> course = courseDAO.findByCRN(crn.get())
            
            if (!course.isPresent()) {
                throw new WebApplicationException(Response.Status.NOT_FOUND)
            }

            // return method in DAO to delete the specific instance
    }

    //---------------------------------------------------------------------------

    // HTTP methods allowed on courses/name/{coursename}
    @OPTIONS
    @Path('/name/{coursename: \\d+}')
    public List optionsByName() {
        return ['OPTIONS', 'GET']
    }

    // Gets all courses by a course name
    @GET
    @Path('/name/{coursename: \\d+}')
    @UnitOfWork
    public String getByName(@PathParam('name') LongParam name) {
        final Optional<Course> course = courseDAO.findByName(name.get())

        if (!course.isPresent()) {
            throw new WebApplicationException(Response.Status.NOT_FOUND)
        }

        return course.get()
    }

    //---------------------------------------------------------------------------

    // HTTP methods allowed on courses/location
    @OPTIONS
    @Path('/location')
    public List locationOptions() {
        return ['OPTIONS', 'GET']
    }

    // Get all course locations
    @GET
    @Path('/location')
    @UnitOfWork
    public String getAllLocations() {
        return courseDAO.findAllLocations()
    }

    //---------------------------------------------------------------------------

    // HTTP methods allowed on courses/location/{location}
    @OPTIONS
    @Path('/location/{location: \\d+}')
    public List optionsByLocation() {
        return ['OPTIONS', 'GET']
    }

    // Get all courses from a specified location
    @GET
    @Path('/location/{location: \\d+}')
    @UnitOfWork
    public String getByLocation(@PathParam('location') LongParam location) {
        final Optional<Course> course = courseDAO.findByLocation(location.get())

        if (!course.isPresent()) {
            throw new WebApplicationException(Response.Status.NOT_FOUND)
        }

        return course.get()
    }

    //---------------------------------------------------------------------------

    // HTTP methods allowed on courses/findCourseByInstructor
    @OPTIONS
    @Path('/findCourseByInstructor')
    public List InstructorOptions() {
        return ['OPTIONS', 'GET']
    }

    // Gets all course Instructors
    @GET
    @Path('/findCourseByInstructor')
    @UnitOfWork
    public String getAllInstructors() {
        return courseDAO.findAllInstructors()
    }
    
    //---------------------------------------------------------------------------

}