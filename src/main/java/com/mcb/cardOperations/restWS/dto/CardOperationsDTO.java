package com.mcb.cardOperations.restWS.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcb.cardOperations.restWS.utils.JAXBElementMixIn;
import com.mcb.soapWS.wsdl.ObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBElement;

abstract class CardOperationsDTO {
    protected static final Logger log = LoggerFactory.getLogger(CardOperationsDTO.class);
    protected static final ObjectFactory objectFactory = new ObjectFactory();
    public static final ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.addMixIn(JAXBElement.class, JAXBElementMixIn.class);
    }
}
