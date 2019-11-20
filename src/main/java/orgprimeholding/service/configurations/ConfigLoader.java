package orgprimeholding.service.configurations;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigLoader {
    private static final Logger LOGGER = Logger.getLogger(ConfigLoader.class.getName());

    private ConfigLoader() {
    }

    public static Properties getProperties(InputStream configPath) {
        LOGGER.setLevel(Level.ALL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        LOGGER.addHandler(handler);

        try {
            Properties properties = new Properties();
            properties.load(configPath);
            return properties;
        } catch (IOException e) {
            LOGGER.log(Level.ALL, e.getMessage(), e);
        }

        return null;
    }
}
