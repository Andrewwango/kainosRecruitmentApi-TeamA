package com.kainos.ea.resources;

import com.kainos.ea.dao.BandLevel;
import com.kainos.ea.dao.CapabilityLevel;
import com.kainos.ea.dao.JobRoleLevel;
import com.kainos.ea.dao.SpecificationLevel;
import com.kainos.ea.exception.DatabaseConnectionException;
import com.kainos.ea.models.Band;
import com.kainos.ea.models.Capability;
import io.swagger.annotations.Api;
import com.kainos.ea.models.JobRole;
import org.eclipse.jetty.http.HttpStatus;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Path("/api")
@Api("jobRoles")
public class WebService {
    @GET
    @Path("/job-roles")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JobRole> getJobRoles() throws SQLException {
        List<JobRole> job_roles = JobRoleLevel.getJobRoles();
        return job_roles;
    }
    @GET
    @Path("/job-specification/{jobRoleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public JobRole getJobRole(@PathParam("jobRoleId") int jobRoleId){
        JobRole jobRole = SpecificationLevel.getJobRole(jobRoleId);
        return jobRole;
    }

    @GET
    @Path("/viewBandLevel")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Band> getBases(){
        List<Band> bases = BandLevel.getBand();
        return bases;
    }

    @GET
    @Path("/viewCapabilities")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCapabilities() {
        try {
            CapabilityLevel capLevel = new CapabilityLevel();
            return Response.ok(capLevel.getCapabilities()).build();
        } catch (SQLException e) {
            System.out.println(e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }

    }
}
