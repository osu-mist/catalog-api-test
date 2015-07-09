package edu.oregonstate.mist.catalogapitest.resources

import edu.oregonstate.mist.catalogapitest.core.Course
import edu.oregonstate.mist.catalogapitest.db.CourseDAO

import io.dropwizard.jersey.params.IntParam

import com.google.common.base.Optional
import javassist.NotFoundException
import org.eclipse.jetty.server.Response

import javax.validation.constraints.NotNull
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.MediaType

@Path("/course")
@Produces(MediaType.APPLICATION_JSON)
class CourseResource {

    private final CourseDAO courseDAO

    public CourseResource(CourseDAO courseDAO) {
        this.courseDAO = courseDAO
    }

    @GET
    @Path('{crn}')
    public List<Course> getByCrn(@PathParam('crn') IntParam crn) {
        println(crn.get())

        final List<Course> courses = courseDAO.findByCrn(crn.get())

        if (courses.isEmpty()) {
            throw new WebApplicationException(404)
        }

        return courses
    }

    @GET
    @Path('/name/{courseName}')
    public List<Course> getByCourseName(@PathParam('courseName') String courseName) {
        println(courseName)

        final List<Course> courses = courseDAO.findByCourseName(courseName)

        if (courses.isEmpty()) {
            throw new WebApplicationException(404)
        }

        return courses
    }
}
