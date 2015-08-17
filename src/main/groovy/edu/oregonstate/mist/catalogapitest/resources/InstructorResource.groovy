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

    // Lists all instructors -------------------------------------------------------------------------------------------
    @GET
    @Path('/all')
    @Produces(MediaType.APPLICATION_JSON)
    public List<Instructor> getByCid() {
        return instructorDAO.allInstructors
    }

    // CID specific requests -------------------------------------------------------------------------------------------
    @GET
    @Path('{cid}')
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByCid(@PathParam('cid') IntParam cid) {
        Instructor instructors = instructorDAO.getByCid(cid.get())

        Response returnResponse

        if (instructors == null) {
            ErrorPOJO returnError = new ErrorPOJO("Resource Not Found.", Response.Status.NOT_FOUND.getStatusCode())
            returnResponse = Response.status(Response.Status.NOT_FOUND).entity(returnError).build()
        } else {
            returnResponse = Response.ok(instructors).build()
        }

        return returnResponse
    }

    @PUT
    @Path('{cid}')
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putByCid(@PathParam("cid") Integer cid, Instructor newInstructor) {

        Response returnResponse

        Instructor checkForInstructorCid = instructorDAO.getByCid(cid)

        // If instructor does not already exist - POST it!
        if (checkForInstructorCid == null) {
            instructorDAO.postByCid(cid, newInstructor.getFirst_initial(), newInstructor.getLast_name(), newInstructor.getNumber_of_courses())
            returnResponse = Response.created().build()
        } else {

            // Otherwise PUT it!
            Optional<String> newFirstInitial = Optional.of( newInstructor.getFirst_initial() )
            Optional<String> newLastName = Optional.of( newInstructor.getLast_name() )
            Optional<Integer> newNumberOfCourses = Optional.of( newInstructor.getNumber_of_courses() )
        }

        return returnResponse
    }

    @DELETE
    @Path('{cid}')
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteByCid(@PathParam('cid') Integer cid) {

        //TODO add authentication for this method
        instructorDAO.deleteByCid(cid)
        Response returnResponse = Response.ok().build()

        return returnResponse
    }
}
