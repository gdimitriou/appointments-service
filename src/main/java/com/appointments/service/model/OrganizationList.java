package com.appointments.service.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "data"
})

public class OrganizationList {

    @JsonProperty("data")
    private List<Organization> data = null;

    @JsonProperty("data")
    public List<Organization> getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(List<Organization> data) {
        this.data = data;
    }

}
