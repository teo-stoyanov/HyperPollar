package orgprimeholding.service.parsers;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class XmlParser {
    private static final Logger LOGGER = Logger.getLogger(XmlParser.class.getName());

    private XmlParser() {
    }

    public static <T> T parse(File file, Class<T> clazz) {
        LOGGER.setLevel(Level.ALL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        LOGGER.addHandler(handler);

        JAXBContext jaxbContext;

        try {
            jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (T) jaxbUnmarshaller.unmarshal(file);

        } catch (JAXBException e) {
            LOGGER.log(Level.ALL,e.getMessage(),e);
        }

        return null;
    }

}
