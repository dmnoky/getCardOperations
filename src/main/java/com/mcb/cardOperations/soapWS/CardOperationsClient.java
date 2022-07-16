package com.mcb.cardOperations.soapWS;

import com.mcb.soapWS.wsdl.GET;
import com.mcb.soapWS.wsdl.GETResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.SoapMessage;
import org.w3c.dom.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;

public class CardOperationsClient extends WebServiceGatewaySupport {
    private static final TransformerFactory TRANSFORMER_FACTORY = TransformerFactory.newInstance();

    @Value("${soap.auth.URI}")
    private String URI;

    public GETResponse addCardOperationsResponse(GET requestBody) throws Exception {
        //return (GETResponse) getWebServiceTemplate().marshalSendAndReceive(URI, requestBody, new SoapActionCallback("http://mkb.ru/SIEBEL_GetClientOperations/GET"));
        final Transformer transformer = TRANSFORMER_FACTORY.newTransformer();
        DOMSource sourceDOM = (DOMSource) getWebServiceTemplate().sendAndReceive(URI, message -> {
            try { //request
                ((SoapMessage) message).setSoapAction("http://mkb.ru/SIEBEL_GetClientOperations/GET");
                transformer.transform(new JAXBSource(JAXBContext.newInstance(GET.class), requestBody), message.getPayloadResult());
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
        }, WebServiceMessage::getPayloadSource); //response

        Node nodeGETResponse = sourceDOM.getNode();
        renameNamespaceRecursive(nodeGETResponse.getOwnerDocument(), nodeGETResponse, "xmlns", "http://mkb.ru/SIEBEL_GetClientOperations/");

        return (GETResponse) JAXBContext.newInstance(GETResponse.class).createUnmarshaller().unmarshal(nodeGETResponse);
    }

    private static void renameNamespaceRecursive(Document doc, Node node, String prefix, String namespace) {
        switch (node.getNodeName()) {
            case "ErrorCode":
            case "ErrorMsg": // удаляем namespace
                NamedNodeMap attributes = node.getAttributes();
                if (attributes != null && attributes.getNamedItem(prefix) != null) attributes.removeNamedItem(prefix);
                doc.renameNode(node, "", node.getNodeName());
                break;
            case "GETResponse":
            case "OperationList":
            case "OperationInfo":
            case "OperationMBKBonus":
            case "ErrorInfo": // добавляем namespace
                Attr attr = doc.createAttribute(prefix); //аттрибут всегда прототип
                attr.setNodeValue(namespace);
                node.getAttributes().setNamedItem(attr);
                doc.renameNode(node, namespace, node.getNodeName());
                break;
        }

        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); ++i) {
            renameNamespaceRecursive(doc, list.item(i), prefix, namespace);
        }
    }
}
