package com.lschuetz;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/documents")
/**
 * Exposes all the "/documents" endpoints
 * 
 * @author Lukas Theodor Schütz
 */
public class DocumentsResource {
    
    @Inject
    DocumentsService documentsService;

    @GET
    public List<Documents> list() {
        return documentsService.list();
    }

    @GET
    @Path("/caseId/{caseId}")
    public List<Documents> listByCaseId(@PathParam("caseId") String caseId) {
        UUID caseUUIDId = UUID.fromString(caseId);
        return documentsService.listByCaseId(caseUUIDId);
    }

    @POST
    @Consumes("application/json")
    public List<Documents> addOne(Documents documents) {
        documentsService.add(documents);
        return list();
    }

    @POST
    @Path("/documentId/{documentId}/caseId/{caseId}")
    public List<Documents> appendToCase(@PathParam("documentId") String documentId, @PathParam("caseId") String caseId) {
        documentsService.addCaseToDocument(caseId, documentId);
        return list();
    }
}
