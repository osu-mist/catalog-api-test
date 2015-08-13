package edu.oregonstate.mist.catalogapitest.resources

import edu.oregonstate.mist.catalogapitest.core.ErrorPOJO
import edu.oregonstate.mist.catalogapitest.core.Instructor
import edu.oregonstate.mist.catalogapitest.db.InstructorDAO
import io.dropwizard.jersey.params.IntParam
import com.google.common.base.Optional
import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo

@Path("/instructor")
@Produces(MediaType.APPLICATION_JSON)
class InstructorResource {

    // Instantiate DAO -------------------------------------------------------------------------------------------------
    private final InstructorDAO instructorDAO

    @Context
    UriInfo uriInfo

    public InstructorResource(InstructorDAO instructorDAO) {
        this.instructorDAO = instructorDAO
    }

    // POST to /instructor ---------------------------------------------------------------------------------------------
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postInstructor(@Valid Instructor newInstructor) {
        Response returnResponse
        URI createdURI

        try {
            instructorDAO.postInstructor(newInstructor.getCid(), newInstructor.getFirst_initial(),
                    newInstructor.getLast_name(), newInstructor.getNumber_of_courses())

            //TODO Add in the URI of newly created resource
            createdURI = URI.create(uriInfo.getPath() + "/" + instructorDAO.getLatestCidNumber())
            returnResponse = Response.created(createdURI).build()

        } catch (org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException e) {

            ErrorPOJO constraintError = e.cause.toString()
            ErrorPOJO returnError

            if (constraintError.contains("INSTRUCTORS_UK_CID")) {

                // CID number is not unique
                returnError = new ErrorPOJO("CID is not unique", Response.Status.CONFLICT.getStatusCode())

            } else {

                // Some other error, should be logged
                System.out.println(e.localizedMessage)
                returnError = new ErrorPOJO("Unknown Error", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
            }

            System.out.println(returnError.getErrorMessage())
        }

        return returnResponse
    }
}
