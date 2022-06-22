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
 * Contains methods for creating and receiving of Cases from the mongoDB database.
 * 
 * @author Lukas Theodor Schütz
 */
public class CasesService {
    
    @Inject
    MongoClient mongoClient;

    @Inject
    DocumentsService documentsService;

    /**
     * Lists all cases with a type
     * @param type  of the Case
     * @return      Cases with the type
     */
    public List<Cases> listByType(String type) {
        List<Cases> list = new ArrayList<>();
        MongoCursor<Document> cursor = getCollection().find(Filters.eq("type", type)).iterator();

        try {
            while(cursor.hasNext()) {
                Document document = cursor.next();
                List<Documents> documents = documentsService.listByCaseId((UUID) document.get("id"));
                Cases cases = new Cases();
                cases.setId((UUID) document.get("id"));
                cases.setType(document.getString("type"));
                cases.setCreatedAt((Date) document.get("createdAt"));
                cases.setDocuments(documents);
                list.add(cases);
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    /**
     * List all Cases that contain a Document
     * @param documentId    of the Document
     * @return              Cases containing the Document
     */
    public List<Cases> listByContainingDocument(String documentId) {
        List<Cases> list = new ArrayList<>();
        MongoCursor<Document> cursor = getCollection().find(Filters.eq("documentId", documentId)).iterator();

        try {
            while(cursor.hasNext()) {
                Document document = cursor.next();
                List<Documents> documents = documentsService.listByCaseId((UUID) document.get("id"));
                Cases cases = new Cases();
                cases.setId((UUID) document.get("id"));
                cases.setType(document.getString("type"));
                cases.setCreatedAt((Date) document.get("createdAt"));
                cases.setDocuments(documents);
                list.add(cases);
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    /**
     * Save a new Case and append the document it was created with.
     * @param cases         the Case to be saved
     * @param documentId    of the Document that was used to initiate the Case
     */
    public void add(Cases cases, UUID documentId) {
        // save the new Cases
        UUID caseId = UUID.randomUUID();
        Document document = new Document()
            .append("id", caseId)
            .append("type", cases.getType())
            .append("createdAt", LocalDateTime.now());
        getCollection().insertOne(document);

        // store the relation
        Document casesToDocumentDocument = new Document()
            .append("caseId", caseId)
            .append("documentId", documentId);
        getCasesToDocumentCollection().insertOne(casesToDocumentDocument);
    }

    /**
     * Fetch the MongoCollection that stores the relation between cases and documents
     * @return  MongoCollection
     */
    private MongoCollection getCasesToDocumentCollection() {
        return mongoClient.getDatabase("mongodb").getCollection("cases_to_documents");
    }

    /**
     * Fetch the MongoCollection that contains all the Cases
     * @return  MongoCollection
     */
    private MongoCollection getCollection() {
        return mongoClient.getDatabase("mongodb").getCollection("cases");
    }
}
