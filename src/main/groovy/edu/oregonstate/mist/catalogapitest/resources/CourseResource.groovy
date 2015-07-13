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
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.MediaType

@Path("/course")
@Produces(MediaType.APPLICATION_JSON)
class CourseResource {

    // Instantiate DAO -------------------------------------------------------------------------------------------------
    private final CourseDAO courseDAO

    public CourseResource(CourseDAO courseDAO) {
        this.courseDAO = courseDAO
    }

    // List all courses ------------------------------------------------------------------------------------------------
    @GET
    @Path('/all')
    @Produces(MediaType.APPLICATION_JSON)
    public List<Course> getByCrn() {
        return courseDAO.allCourses
    }

    // CRN specific requests -------------------------------------------------------------------------------------------
    @GET
    @Path('{crn}')
    @Produces(MediaType.APPLICATION_JSON)
    public List<Course> getByCrn(@PathParam('crn') IntParam crn) {
        final List<Course> courses = courseDAO.getByCrn(crn.get())

        if (courses.isEmpty()) {
            throw new WebApplicationException(404)
        }

        return courses
    }

    @Path("{crn}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putByCrn(@PathParam("crn") Integer crn , Course newCourse) {

        def returnResponse

        List<Course> checkForCourseCrn = courseDAO.getByCrn(crn)

        // If course does not already exist - POST it!
        if (checkForCourseCrn == null) {
            courseDAO.postByCrn(crn, newCourse.getCourseName(), newCourse.getInstructor(), newCourse.getDay(),
                    newCourse.getTime(), newCourse.getLocation())
            returnResponse = Response.created().build()

        } else {

            // Otherwise PUT it!
            Optional<String> newCourseName = Optional.of( newCourse.getCourseName() )
            Optional<String> newInstructor = Optional.of( newCourse.getInstructor() )
            Optional<String> newDay = Optional.of( newCourse.getDay() )
            Optional<String> newTime = Optional.of( newCourse.getTime() )
            Optional<String> newLocation = Optional.of( newCourse.getLocation() )

            courseDAO.putByCrn(crn, newCourseName.or(checkForCourseCrn.getCourseName()),
                    newInstructor.or(checkForCourseCrn.getInstructor()), newDay.or(checkForCourseCrn.getDay()),
                    newTime.or(checkForCourseCrn.getTime()), newLocation.or(checkForCourseCrn.getLocation())
            )
            returnResponse = Response.ok().build()
        }


        return returnResponse
    }


    // Name specific requests ------------------------------------------------------------------------------------------
    @GET
    @Path('/name/{courseName}')
    @Produces(MediaType.APPLICATION_JSON)
    public List<Course> getByCourseName(@PathParam('courseName') String courseName) {
        final List<Course> courses = courseDAO.getByName(courseName)

        if (courses.isEmpty()) {
            throw new WebApplicationException(404)
        }

        return courses
    }

    // Location specific requests --------------------------------------------------------------------------------------
    @GET
    @Path('/location/{location}')
    @Produces(MediaType.APPLICATION_JSON)
    public List<Course> getCourseByLocation(@PathParam('location') String location) {
        final List<Course> courses = courseDAO.getByLocation(location)

        if (courses.isEmpty()) {
            throw new WebApplicationException(404)
        }

        return courses
    }
}
