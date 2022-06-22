package com.lschuetz;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * Document containing an id, filetype, filename, fileid and uploadedAt
 * 
 * @author Lukas Theodor Schütz
 */
public class Documents {
    
    private UUID id;
    private String filetype;
    private String filename;
    private String fileid;
    private Date uploadedAt;

    public Documents() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public Date getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Date uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Documents)) {
            return false;
        }

        Documents other = (Documents) obj;

        return other.fileid == this.fileid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.fileid);
    }

}
