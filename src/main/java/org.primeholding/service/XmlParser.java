package org.primeholding.service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;



public class XmlParser {

    public static <T> T Parse(File file,  Class<T> clazz){

        JAXBContext jaxbContext;
        {
            try {
                jaxbContext = JAXBContext.newInstance(clazz);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                return (T) jaxbUnmarshaller.unmarshal(file);

            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}
