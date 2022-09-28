package com.kainos.ea;


import com.kainos.ea.resources.base;
import com.kainos.ea.resources.getBases;
import io.swagger.annotations.Api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api")
@Api("jobRoles")
public class WebService {
    @GET
    @Path("/print/{msg}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMsg(@PathParam("msg") String message) {
        return "Hello from a RESTful Web service: " + message;
    }

    @GET
    @Path("/viewBandLevel")
    @Produces(MediaType.APPLICATION_JSON)
    public List<base> getBases(){
        List<base> bases = getBases.getBases();
        return bases;
    }

}
