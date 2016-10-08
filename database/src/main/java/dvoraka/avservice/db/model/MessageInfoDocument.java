package dvoraka.avservice.db.model;

import org.apache.solr.client.solrj.beans.Field;

import javax.persistence.Id;
import java.util.Date;

/**
 * Message info document.
 */
public class MessageInfoDocument {

    @Id
    private String id;

    @Field
    private String uuid;
    @Field
    private String source;
    @Field
    private String serviceId;
    @Field
    private Date created;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public Date getCreated() {
        return new Date(created.getTime());
    }

    public void setCreated(Date created) {
        this.created = new Date(created.getTime());
    }
}
