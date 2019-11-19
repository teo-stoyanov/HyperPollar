package orgprimeholding.service.configurations;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigLoader {
    private static final Logger LOGGER = Logger.getLogger(ConfigLoader.class.getName());

    private ConfigLoader() {
    }

    public static Properties getProperties(String configPath) {
        LOGGER.setLevel(Level.ALL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        LOGGER.addHandler(handler);

        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(configPath));
            return properties;
        } catch (IOException e) {
            LOGGER.log(Level.ALL, e.getMessage(), e);
        }

        return null;
    }
}
