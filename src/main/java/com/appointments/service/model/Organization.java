package com.appointments.service.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "code",
        "preferredLabel",
        "subOrganizationOf"
})

public class Organization {

    @JsonProperty("code")
    private String code;

    @JsonProperty("preferredLabel")
    private String preferredLabel;

    @JsonProperty("subOrganizationOf")
    private String subOrganizationOf;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Organization() {
    }

    public Organization(String code, String preferredLabel, String subOrganizationOf) {
        this.code = code;
        this.preferredLabel = preferredLabel;
        this.subOrganizationOf = subOrganizationOf;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("preferredLabel")
    public String getPreferredLabel() {
        return preferredLabel;
    }

    @JsonProperty("preferredLabel")
    public void setPreferredLabel(String preferredLabel) {
        this.preferredLabel = preferredLabel;
    }

    @JsonProperty("subOrganizationOf")
    public String getSubOrganizationOf() {
        return subOrganizationOf;
    }

    @JsonProperty("subOrganizationOf")
    public void setSubOrganizationOf(String subOrganizationOf) {
        this.subOrganizationOf = subOrganizationOf;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}