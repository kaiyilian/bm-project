package com.bumu.arya.admin.devops.model.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;
import java.util.Map;

/**
 * @author Allen 2017-11-27
 **/
@Document(collection = "API_ACCESS_LOG")
public class ApiLogDocument {

    String message;
    Long millis;
    Date date;
    Map<String, Object> contextMap;
    @Id
    private String id;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getMillis() {
        return millis;
    }

    public void setMillis(Long millis) {
        this.millis = millis;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Map<String, Object> getContextMap() {
        return contextMap;
    }

    public void setContextMap(Map<String, Object> contextMap) {
        this.contextMap = contextMap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ApiLogDocument{" +
                "message='" + message + '\'' +
                ", millis=" + millis +
                ", date=" + date +
                ", contextMap=" + contextMap +
                ", id='" + id + '\'' +
                '}';
    }
}
