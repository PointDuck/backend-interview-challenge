package com.lschuetz;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/cases")
/**
 * Expose the "/cases" endpoints
 * 
 * @author Lukas Theodor Schütz
 */
public class CasesResource {
    
    @Inject
    CasesService casesService;

    @GET
    @Path("/type/{type}")
    public List<Cases> listByType(@PathParam("type") String type) {
        return casesService.listByType(type);
    }

    @GET
    @Path("/documentId/{documentId}")
    public List<Cases> listByDocument(@PathParam("documentId") String documentId) {
        return casesService.listByContainingDocument(documentId);
    }
    
}
