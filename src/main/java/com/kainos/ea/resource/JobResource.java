package com.kainos.ea.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kainos.ea.dao.*;
import com.kainos.ea.exception.InvalidJobRoleException;
import com.kainos.ea.models.*;
import com.kainos.ea.service.AddJobRoleService;
import com.kainos.ea.validator.JobRoleValidator;
import org.eclipse.jetty.http.HttpStatus;
import com.kainos.ea.models.*;
import io.swagger.annotations.Api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Path("/api")
@Api("jobRoles")
public class JobResource {

    private JobRoleLevel jobRoleLevel;
    private SpecificationLevel specificationLevel;
    private BandLevel bandLevel;
    private CapabilityLevel capabilityLevel;
    private TrainingLevel trainingLevel;
    private CompetenciesLevel competenciesLevel;
    private GenderBiasLevel genderBiasLevel;
    private RoleFeaturesLevel roleFeaturesLevel;
    private AddJobRoleLevel addJobRoleLevel;
    private AddJobRoleService addJobRoleService;
    private JobRoleValidator jobRoleValidator;

    public JobResource(RoleFeaturesLevel roleFeaturesLevel, JobRoleLevel jobRoleLevel, BandLevel bandLevel, CapabilityLevel capabilityLevel, SpecificationLevel specificationLevel, CompetenciesLevel competenciesLevel, TrainingLevel trainingLevel, AddJobRoleLevel addJobRoleLevel, AddJobRoleService addJobRoleService, GenderBiasLevel genderBiasLevel) {
        this.jobRoleLevel = jobRoleLevel;
        this.bandLevel = bandLevel;
        this.capabilityLevel = capabilityLevel;
        this.specificationLevel = specificationLevel;
        this.trainingLevel = trainingLevel;
        this.competenciesLevel = competenciesLevel;
        this.genderBiasLevel = genderBiasLevel;
        this.addJobRoleLevel = addJobRoleLevel;
        this.addJobRoleService = addJobRoleService;
        this.roleFeaturesLevel = roleFeaturesLevel;
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
    @Path("/job-roles-by-capability/{capabilityId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JobRole> getJobRolesByCapability(@PathParam("capabilityId") int capabilityID)
            throws SQLException {
        List<JobRole> jobRoles = jobRoleLevel.getJobRolesByCapability(capabilityID);
        return jobRoles;
    }

    @GET
    @Path("/job-specification/{jobRoleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public JobRole getJobRole(@PathParam("jobRoleId") int jobRoleId) throws SQLException {
        JobRole jobRole = specificationLevel.getJobRole(jobRoleId);
        return jobRole;
    }

    @GET
    @Path("/viewBandLevel")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Band> getBases() throws SQLException {
        List<Band> bases = bandLevel.getBand();
        return bases;
    }

    @GET
    @Path("/viewBandLevelNames")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Band> getBandNames() throws SQLException {
        List<Band> bands = bandLevel.getBandNames();
        return bands;
    }

    @GET
    @Path("/viewCapabilities")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Capability> getCapabilities() throws SQLException {
        List<Capability> capabilities = capabilityLevel.getCapabilities();
        return capabilities;
    }

    @GET
    @Path("/viewCapabilitiesNames")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Capability> getCapabilitiesNames() throws SQLException {
        List<Capability> capabilities = capabilityLevel.getCapabilitiesNames();
        return capabilities;
    }

    @GET
    @Path("/view-band-training/{bandID}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Training> getTraining(@PathParam("bandID") int bandID) throws SQLException {
        List<Training> training = trainingLevel.getTraining(bandID);
        return training;
    }

    @GET
    @Path("/viewCompetencies/{bandID}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Competencies> getCompetencies(@PathParam("bandID") int bandID) throws SQLException {
        List<Competencies> competencies = competenciesLevel.getCompetencies(bandID);
        return competencies;
    }

    @DELETE
    @Path("/delete-job-roles")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteJobRoles(@JsonProperty("id") List<String> jobIDs) throws SQLException {
        Response response = jobRoleLevel.deleteJobRoles(jobIDs);
        return response;
    }
    
    @POST
    @Path("/gender-bias")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public BiasRequest postGenderBias(String request) throws IOException {
        BiasRequest genderBias = genderBiasLevel.getGenderBias(request);
        return genderBias;
    }

    @POST
    @Path("/add-job-roles")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addJobRole(AddJobRole jobRole) throws InvalidJobRoleException {
        try {
            return Response.ok(addJobRoleService.addJobRole(jobRole)).build();
        } catch (NullPointerException | InvalidJobRoleException e) {
            return Response.status(HttpStatus.BAD_REQUEST_400).entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @PUT
    @Path("/editJobRole/{jobRoleID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String putJobRoleChanges(@PathParam("jobRoleID") int jobID,JobRoleWithoutLink jobRole) throws SQLException {
        String response = roleFeaturesLevel.editJobRole(jobID,jobRole);
        return response;
    }
}
