package com.kainos.ea.resources;

import com.kainos.ea.dao.BandLevel;
import com.kainos.ea.dao.CapabilityLevel;
import com.kainos.ea.dao.JobRoleLevel;
import com.kainos.ea.dao.SpecificationLevel;
import com.kainos.ea.models.Band;
import com.kainos.ea.models.Capability;
import io.swagger.annotations.Api;
import com.kainos.ea.models.JobRole;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;

@Path("/api")
@Api("jobRoles")
public class WebService {
    @GET
    @Path("/job-roles")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JobRole> getJobRoles() throws SQLException {
        List<JobRole> jobRoles = JobRoleLevel.getJobRoles();
        return jobRoles;
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
    public List<Band> getBases() throws SQLException {
        BandLevel band = new BandLevel();
        List<Band> bases = band.getBand();
        return bases;
    }

    @GET
    @Path("/viewCapabilities")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Capability> getCapabilities(){
        List<Capability> capabilities = CapabilityLevel.getCapabilities();
        return capabilities;
    }
}
