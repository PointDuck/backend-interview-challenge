package com.lschuetz;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A case can associate with many documents, or none
 * @author Lukas Theodor Schütz
 */
public class Cases {
    
    private UUID id;
    private String type;
    private Date createdAt;
    private List<Documents> documents;

    public Cases() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Documents> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Documents> documents) {
        this.documents = documents;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Cases)) {
            return false;
        }

        Cases other = (Cases) obj;

        return Objects.equals(other.id, this.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

}
