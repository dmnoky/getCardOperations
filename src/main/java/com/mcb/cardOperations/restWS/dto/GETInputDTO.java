package com.mcb.cardOperations.restWS.dto;

import com.mcb.soapWS.wsdl.GET;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GETInputDTO extends CardOperationsDTO {
    public static final String GETInput_REQUEST_DATE_PATTERN = "dd.MM.yyyy";
    public static final DateTimeFormatter GETInput_DATE_FORMATTER = DateTimeFormatter.ofPattern(GETInput_REQUEST_DATE_PATTERN);
    String serNo;
    String startDate;
    String endDate;

    public GETInputDTO(String serNo) {
        this.serNo = serNo;
        initDate();
    }

    public GETInputDTO(String serNo, String startDate, String endDate) {
        this.serNo = serNo;
        if (startDate == null || startDate.isEmpty() || endDate == null || endDate.isEmpty()) initDate();
        else {
            this.endDate = endDate;
            this.startDate = startDate;
        }
    }

    public void initDate() {
        final LocalDate now = LocalDate.now();
        this.startDate = now.minusMonths(3).format(GETInput_DATE_FORMATTER);
        this.endDate = GETInput_DATE_FORMATTER.format(now);
    }

    public GET GETInputBuild() {
        GET requestBody = new GET();
        requestBody.setSerNo(serNo);
        requestBody.setEndDate(objectFactory.createGETEndDate(endDate));
        requestBody.setStartDate(objectFactory.createGETStartDate(startDate));

        log.info("Requesting body: " + this.toString());
        return requestBody;
    }

}
