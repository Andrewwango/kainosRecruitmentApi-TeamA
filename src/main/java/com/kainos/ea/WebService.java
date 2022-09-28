package com.kainos.ea;

import com.kainos.ea.resources.Band;
import com.kainos.ea.resources.BandLevel;
import dao.SpecificationLevel;
import io.swagger.annotations.Api;
import models.JobRole;

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
}
