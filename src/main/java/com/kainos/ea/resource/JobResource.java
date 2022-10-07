package com.kainos.ea.resource;

import com.kainos.ea.dao.*;
import com.kainos.ea.models.*;
import io.swagger.annotations.Api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
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

    public JobResource(JobRoleLevel jobRoleLevel, BandLevel bandLevel, CapabilityLevel capabilityLevel, SpecificationLevel specificationLevel, CompetenciesLevel competenciesLevel, TrainingLevel trainingLevel) {
        this.jobRoleLevel = jobRoleLevel;
        this.bandLevel = bandLevel;
        this.capabilityLevel = capabilityLevel;
        this.specificationLevel = specificationLevel;
        this.trainingLevel = trainingLevel;
        this.competenciesLevel = competenciesLevel;
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
    public List<BandName> getBandNames() throws SQLException {
        List<BandName> bands = bandLevel.getBandNames();
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
}
