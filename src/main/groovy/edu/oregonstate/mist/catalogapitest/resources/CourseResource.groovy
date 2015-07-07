package edu.oregonstate.mist.catalogapitest.resources

import edu.oregonstate.mist.catalogapitest.core.Course
import edu.oregonstate.mist.catalogapitest.db.CourseDAO

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

    @Path("/{crn}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Course findCourseByCRN(@PathParam("crn") int crn){
        Course returnCourse = CourseDAO.findByCRN(crn)

        if(returnCourse == null){
            throw new WebApplicationException(404)
        }
        return returnUser;
    }
}
