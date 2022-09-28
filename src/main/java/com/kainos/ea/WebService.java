package com.kainos.ea;

import com.kainos.ea.database.DataBaseConnection;
import com.kainos.ea.resources.Band;
import com.kainos.ea.resources.BandLevel;
import com.kainos.ea.resources.JobRole;
import com.kainos.ea.resources.JobRoleLevel;
import io.swagger.annotations.Api;
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
    @Path("/viewBandLevel")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Band> getBases(){
        List<Band> bases = BandLevel.getBand();
        return bases;
    }
    @GET
    @Path("/job-roles")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JobRole> getJobRoles() throws SQLException {
        List<JobRole> job_roles = JobRoleLevel.getJobRoles();
        return job_roles;
    }
}
