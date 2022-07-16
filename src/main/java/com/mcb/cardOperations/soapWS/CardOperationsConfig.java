package com.mcb.cardOperations.soapWS;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

/**@see package-info.java в com.mcb.soapWS.wsdl - добавил elementFormDefault = XmlNsForm.QUALIFIED в аннотацию */
@Configuration
public class CardOperationsConfig {
    @Value("${soap.auth.username}")
    private String userName;
    @Value("${soap.auth.password}")
    private String userPassword;

    @Bean
    public Jaxb2Marshaller marshaller() throws Exception {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in pom.xml
        jaxb2Marshaller.setPackagesToScan("com.mcb.soapWS.wsdl");
        return jaxb2Marshaller;
    }

    @Bean
    public CardOperationsClient countryClient(Jaxb2Marshaller marshaller) throws Exception {
        CardOperationsClient client = new CardOperationsClient();
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        // set a HttpComponentsMessageSender which provides support for basic authentication
        client.setMessageSender(httpComponentsMessageSender());
        client.afterPropertiesSet();
        return client;
    }

    @Bean
    public HttpComponentsMessageSender httpComponentsMessageSender() {
        HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
        // set the basic authorization credentials
        httpComponentsMessageSender.setCredentials(new UsernamePasswordCredentials(userName, userPassword));
        return httpComponentsMessageSender;
    }

}
