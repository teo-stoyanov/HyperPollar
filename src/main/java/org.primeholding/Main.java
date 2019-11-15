package org.primeholding;

import org.primeholding.models.Company;
import org.primeholding.service.XmlParser;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File xmlFile = new File("src/main/resources/text.xml");
        XmlParser xmlParser = new XmlParser();
        Company company =  xmlParser.Parse(xmlFile, Company.class);
        String debug = "";
    }
}
