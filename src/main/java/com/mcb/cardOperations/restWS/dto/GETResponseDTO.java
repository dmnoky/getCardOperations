package com.mcb.cardOperations.restWS.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mcb.soapWS.wsdl.ErrorInfoTypeRs;
import com.mcb.soapWS.wsdl.GETResponse;

public class GETResponseDTO extends CardOperationsDTO {

    public static String toJSON(GETResponse getResponse) throws JsonProcessingException {
        log.info("Response body: " + getResponse.toString());
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(getResponse);
    }

    public static String toJSON(String errorCode, String errorMessage) throws JsonProcessingException {
        GETResponse getResponse = new GETResponse();
        ErrorInfoTypeRs errorInfo = new ErrorInfoTypeRs();
        errorInfo.setErrorCode(objectFactory.createErrorInfoTypeRsErrorCode(errorCode));
        errorInfo.setErrorMsg(objectFactory.createErrorInfoTypeRsErrorMsg(errorMessage));
        getResponse.setErrorInfo(errorInfo);
        return toJSON(getResponse);
    }

}
