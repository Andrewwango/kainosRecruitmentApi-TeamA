package com.kainos.ea.resource;

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
public class JobResource {

    private JobRoleLevel jobRoleLevel;
    private BandLevel bandLevel;


    public JobResource(JobRoleLevel jobRoleLevel, BandLevel bandLevel) {
        this.jobRoleLevel = jobRoleLevel;
        this.bandLevel = bandLevel;
    }

    @GET
    @Path("/job-roles")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JobRole> getJobRoles()
    throws SQLException {
        List<JobRole> jobRoles = jobRoleLevel.getJobRoles();
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
    public List<Band> getBases() {
        List<Band> bases = bandLevel.getBand();
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
