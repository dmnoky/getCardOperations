package com.mcb.cardOperations.restWS.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.namespace.QName;

@JsonIgnoreProperties(value = {"globalScope", "typeSubstituted", "nil", "scope", "declaredType"})
public abstract class JAXBElementMixIn<T> {
    @JsonCreator
    public JAXBElementMixIn(@JsonProperty("name") QName name,
                            @JsonProperty("value") T value) {
    }
}
