package com.kainos.ea;

import com.kainos.ea.resources.Band;
import com.kainos.ea.resources.BandLevel;
import io.swagger.annotations.Api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
}
