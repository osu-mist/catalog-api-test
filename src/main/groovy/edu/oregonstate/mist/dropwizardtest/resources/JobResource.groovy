package edu.oregonstate.mist.dropwizardtest.resources

import edu.oregonstate.mist.dropwizardtest.core.Job
import edu.oregonstate.mist.dropwizardtest.db.JobDAO

import io.dropwizard.jersey.params.LongParam

import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.OPTIONS
import javax.ws.rs.GET
import javax.ws.rs.PathParam
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Response

import com.codahale.metrics.annotation.Timed

/* job resources */
@Path('job')
@Produces(MediaType.APPLICATION_JSON)
class JobResource {
    private final JobDAO jobDAO

    public JobResource(JobDAO jobDAO) {
        this.jobDAO = jobDAO
    }

    /* HTTP methods allowed on job/{pidm} urls */
    @OPTIONS
    @Path('{pidm: \\d+}')
    public List optionsByPidm() {
        return ['OPTIONS', 'GET']
    }

    /* serialized list of jobs with given id */
    @GET
    @Timed
    @Path('{pidm: \\d+}')
    public List<Job> getByPidm(@PathParam('pidm') LongParam pidm) {
        final List<Job> jobs = jobDAO.findByPidm(pidm.get())

        if (jobs.isEmpty()) {
            throw new WebApplicationException(Response.Status.NOT_FOUND)
        }
git
        return jobs
    }
}
