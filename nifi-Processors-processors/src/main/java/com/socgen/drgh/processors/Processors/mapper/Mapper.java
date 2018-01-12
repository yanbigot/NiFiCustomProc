package com.socgen.drgh.processors.Processors.mapper;

import java.util.List;


public class Mapper {


    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ObjectAttribute getSource() {
        return source;
    }

    public void setSource(ObjectAttribute source) {
        this.source = source;
    }

    public ObjectAttribute getTarget() {
        return target;
    }

    public void setTarget(ObjectAttribute target) {
        this.target = target;
    }

    public List<MappingElement> getListMapping() {
        return listMapping;
    }

    public void setListMapping(List<MappingElement> listMapping) {
        this.listMapping = listMapping;
    }

    private ObjectAttribute source;
    private ObjectAttribute target;
    private List<MappingElement> listMapping;
}
