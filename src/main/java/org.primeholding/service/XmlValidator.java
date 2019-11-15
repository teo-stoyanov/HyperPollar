package org.primeholding.service;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.InputStream;

public class XmlValidator {
    public static boolean validate(InputStream inputStreamSchema, InputStream inputStreamXML) {
        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(inputStreamSchema));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(inputStreamXML));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
