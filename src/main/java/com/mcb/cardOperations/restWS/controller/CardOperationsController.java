package com.mcb.cardOperations.restWS.controller;

import com.mcb.cardOperations.restWS.dto.GETInputDTO;
import com.mcb.cardOperations.restWS.dto.GETResponseDTO;
import com.mcb.cardOperations.restWS.exception.BadRequestException;
import com.mcb.cardOperations.soapWS.CardOperationsClient;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping(value = "/api")
public class CardOperationsController {
    private static final Logger log = LoggerFactory.getLogger(CardOperationsController.class);
    public static final String DEFAULT_ERROR_CODE = "-1";
    @Autowired
    private CardOperationsClient quoteClient;

    @SneakyThrows
    @GetMapping(value = "/getcardoper")
    public String getCardOperations(
                  @RequestParam(name = "serNO") String serNO,
                  @RequestParam(name = "startDate", required = false) String startDate,
                  @RequestParam(name = "endDate", required = false) String endDate)
    { /* /api/getcardoper?serNO=12477817&startDate=13.03.2022&endDate=14.04.2022 */
        try {
            if (serNO.isEmpty()) throw new BadRequestException("Серно не может быть пустым");

            return GETResponseDTO.toJSON(quoteClient.addCardOperationsResponse(
                    new GETInputDTO(serNO, startDate, endDate).GETInputBuild()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return GETResponseDTO.toJSON(DEFAULT_ERROR_CODE, e.getMessage());
        }
    }
}
