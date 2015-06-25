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

// Instructor resource
@Path('instructors')
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class InstructorResource {

    private final InstructorDAO instructorDAO

    public InstructorResource(InstructorDAO instructorDAO) {
        this.instructorDAO = instructorDAO
    }

    //---------------------------------------------------------------------------

    // HTTP methods allowed on instructor url
    @OPTIONS
    public List instuctorOptions() {
        return ['OPTIONS', 'GET', 'POST']
    }

    // Gets all instuctors
    @GET
    @UnitOfWork
    public Instructor getAllInstructors() {
        return instructorDAO.findAll()
    }

    // Creates a instructor representation to be appended
    @POST
    @UnitOfWork
    public Instructor setInstructor(@Valid Instructor instructor) {
        return instructorDAO.set(instructor)
    }

    //---------------------------------------------------------------------------

    // HTTP methods allowed on instructor/{instructor} urls
    @OPTIONS
    @Path('/{name: \\d+}')
    public List optionsByName() {
        return ['OPTIONS', 'GET', 'PUT', 'DELETE']
    }

    // Finds instructor by name
    @GET
    @Path('/{name: \\d+}')
    @UnitOfWork
    public Instructor getByName(@PathParam('name') LongParam name) {
        final Optional<Instructor> instructor = instructorDAO.findByName(name.get())

        if (!instructor.isPresent()) {
            throw new WebApplicationException(Response.Status.NOT_FOUND)
        }

        return instructor.get()
    }

    // Puts a instructor resource to be updated or created.
    @PUT
    @Path('/{name: \\d+}')
    @UnitOfWork
    public Instructor putByName(@PathParam('instructor') LongParam name, @Valid Instructor instuctor) {
        final Optional<Instructor> instuctor = instructorDAO.findByName(name.get())

        if (!instructor.isPresent()) {
            throw new WebApplicationException(Response.Status.NOT_FOUND)
        }

        return instructorDAO.set(instructor)
    }

    // Deletes a instructor resource
    @DELETE
    @Path('/{name: \\d+}')
    @UnitOfWork
    public Instructor deleteByName(@PathParam('name') LongParam name){
        final Optional<Instructor> instructor = instructorDAO.findByName(name.get())
            
            if (!instructor.isPresent()) {
                throw new WebApplicationException(Response.Status.NOT_FOUND)
            }

            // return method in DAO to delete the specific instance
    }

    //---------------------------------------------------------------------------

}
