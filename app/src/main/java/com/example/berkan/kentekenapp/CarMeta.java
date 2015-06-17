package com.example.berkan.kentekenapp;

import java.io.Serializable;

/**
 * Created by Haydar on 29-05-15.
 */
public class CarMeta implements Serializable {

    private String id;

    private String type;

    private String uri;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", type = " + type + ", uri = " + uri + "]";
    }
}
