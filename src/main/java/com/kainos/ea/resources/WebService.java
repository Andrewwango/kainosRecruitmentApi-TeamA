package com.kainos.ea.resources;

import com.kainos.ea.dao.BandLevel;
import com.kainos.ea.dao.SpecificationLevel;
import com.kainos.ea.models.Band;
import io.swagger.annotations.Api;
import com.kainos.ea.models.JobRole;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api")
@Api("jobRoles")
public class WebService {

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
    public List<Capability> getCapabilities(){
        List<Capability> capabilities = Capabilities.getCapabilities();
        return capabilities;
    }
}
