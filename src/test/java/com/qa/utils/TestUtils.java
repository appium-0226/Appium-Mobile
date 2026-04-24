package com.qa.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.HashMap;

public class TestUtils {

    public static final long WAIT = 10;

    public HashMap<String, String> parseStringXML(InputStream file) throws Exception {
        HashMap<String, String> stringMap = new HashMap<String, String>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(file);

        document.getDocumentElement().normalize();

        NodeList nList = document.getElementsByTagName("string");

        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node node = nList.item(temp);

            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) node;

                stringMap.put(
                        eElement.getAttribute("name"),
                        eElement.getTextContent()
                );
            }
        }

        return stringMap;
    }



    public static Logger log(){
        return LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClassName());
    }

}
