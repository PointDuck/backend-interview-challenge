package com.lschuetz;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

@ApplicationScoped
/**
 * Contains methods for creating and receiving of Documents from the mongoDB database.
 * 
 * @author Lukas Theodor Schütz
 */
public class DocumentsService {
    
    @Inject 
    MongoClient mongoClient;

    @Inject
    CasesService casesService;

    /**
     * List all Documents
     * @return      List of Documents
     */
    public List<Documents> list() {
        List<Documents> list = new ArrayList<>();
        MongoCursor<Document> cursor = getCollection().find().iterator();

        try {
            while(cursor.hasNext()) {
                Document document = cursor.next();
                Documents documents = new Documents();
                documents.setId((UUID) document.get("id"));
                documents.setFileid(document.getString("fileid"));
                documents.setFilename(document.getString("filename"));
                documents.setFiletype(document.getString("filetype"));
                documents.setUploadedAt((Date) document.get("uploadedAt"));
                list.add(documents);
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    /**
     * Save a new Document
     * @param documents     to be saved
     */
    public void add(Documents documents) {
        // store the document
        UUID documentId = UUID.randomUUID();
        Document document = new Document()
            .append("id", documentId)
            .append("filetype", documents.getFiletype())
            .append("filename", documents.getFilename())
            .append("fileid", documents.getFileid())
            .append("uploadedAt", LocalDateTime.now());
        getCollection().insertOne(document);

        // create new Cases
        Cases cases = new Cases();
        cases.setType("STRING_ENUM");
        casesService.add(cases, documentId);
    }

    /**
     * List all Documents that are associated with a caseId
     * @param caseId      of the Case  
     * @return            List of Documents
     */
    public List<Documents> listByCaseId(UUID caseId) {
        List<Documents> list = new ArrayList<>();
        MongoCursor<Document> cursor = getCasesToDocumentCollection().find(Filters.eq("caseId", caseId)).iterator();

        try {
            while(cursor.hasNext()) {
                // extract the UUID of the Document
                Document document = cursor.next();
                UUID internalId = (UUID) document.get("documentId");

                // find the one document
                MongoCursor<Document> internalCursor = getCollection().find(Filters.eq("id", internalId)).iterator();
                try {
                    while(internalCursor.hasNext()) {
                        Document internalDocument = internalCursor.next();
                        Documents documents = new Documents();
                        documents.setId((UUID) internalDocument.get("id"));
                        documents.setFileid(internalDocument.getString("fileid"));
                        documents.setFilename(internalDocument.getString("filename"));
                        documents.setFiletype(internalDocument.getString("filetype"));
                        documents.setUploadedAt((Date) internalDocument.get("uploadedAt"));
                        list.add(documents);
                        break; // we break here since there is max 1
                    }
                } finally {
                    internalCursor.close();
                }
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    /**
     * Append a Case to a Document
     * @param caseId        of the Case
     * @param documentId    of the Document
     */
    public void addCaseToDocument(String caseId, String documentId) {
        UUID caseUUID = UUID.fromString(caseId);
        UUID documentUUID = UUID.fromString(documentId);
        Document document = new Document()
            .append("caseId", caseUUID)
            .append("documentId", documentUUID);
        getCasesToDocumentCollection().insertOne(document);
    }

    /**
     * Fetch the MongoCollection that stores all the documents
     * @return  MongoCollection
     */
    private MongoCollection getCollection() {
        return mongoClient.getDatabase("mongodb").getCollection("documents");
    }

    /**
     * Fetch the MongoCollection that stores the relation between cases and documents
     * @return  MongoCollection
     */
    private MongoCollection getCasesToDocumentCollection() {
        return mongoClient.getDatabase("mongodb").getCollection("cases_to_documents");
    }
}
