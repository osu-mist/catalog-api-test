package edu.oregonstate.mist.catalogapitest.resources

import edu.oregonstate.mist.catalogapitest.core.ErrorPOJO
import edu.oregonstate.mist.catalogapitest.core.Course
import edu.oregonstate.mist.catalogapitest.db.CourseDAO

import io.dropwizard.jersey.params.IntParam

import com.google.common.base.Optional
import javassist.NotFoundException
import org.eclipse.jetty.server.Response

import javax.validation.Valid
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
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo

@Path("/course")
@Produces(MediaType.APPLICATION_JSON)
class CourseResource {

    // Instantiate DAO -------------------------------------------------------------------------------------------------
    private final CourseDAO courseDAO

    @Context
    UriInfo uriInfo

    public CourseResource(CourseDAO courseDAO) {
        this.courseDAO = courseDAO
    }

    // POST to /course ------------------------------------------------------------------------------------------------
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postUser(@NotNull Course newCourse) {

        Response returnResponse;

        def createdURI

        try {
            courseDAO.postUser(newCourse.getUserLogin() , newUser.getDisplayName())
            //createdURI = URI.create("/"+userDAO)

            //201 CREATED
            //TODO add in the URI of newly created resource
            returnResponse = Response.created().build()

        } catch (org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException e) {

            def constraintError = e.cause.toString()
            def returnError;

            if(constraintError.contains("PR_USER_U_USER_LOGIN")){//USER LOGIN IS NOT UNIQUE

                returnError = new ErrorPOJO("User login is not unique", Response.Status.CONFLICT.getStatusCode())
            }else if(constraintError.contains("PR_USER_U_DISPLAY_NAME")){//DISPLAY NAME IS NOT UNIQUE

                returnError = new ErrorPOJO("Display name is not unique", Response.Status.CONFLICT.getStatusCode())

            }else{//Some other error, should be logged
                //System.out.println(e.localizedMessage)
                returnError = new ErrorPOJO("Unknown error.", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
            }

            return Response.status(Response.Status.CONFLICT).entity(returnError).build()

            //System.out.println(returnError.getErrorMessage())
        }

        return returnResponse;
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
    public Response getByCrn(@PathParam('crn') IntParam crn) {

        Course courses = courseDAO.getByCrn(crn.get())

        def returnResponse

        if (courses == null) {
            def returnError = new ErrorPOJO("Resource Not Found.", Response.Status.NOT_FOUND.getStatusCode())
            returnResponse = Response.status(Response.Status.NOT_FOUND).entity(returnError).build()
        } else {
            returnResponse = Response.ok(courses).build()
        }

        return returnResponse
    }

    @Path("{crn}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putByCrn(@PathParam("crn") Integer crn , Course newCourse) {

        def returnResponse

        Course checkForCourseCrn = courseDAO.getByCrn(crn)

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
