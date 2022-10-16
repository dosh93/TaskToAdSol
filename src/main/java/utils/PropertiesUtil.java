package utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

@Slf4j
public class PropertiesUtil {
    private Properties properties;
    private static final String NAME_FILE_PROPERTIES = "main.properties";

    public PropertiesUtil() {
        try {
            URL resource = getClass().getClassLoader().getResource(NAME_FILE_PROPERTIES);
            assert resource != null;
            File file = new File(resource.toURI());
            properties = new Properties();
            properties.load(new FileReader(file));
        } catch (IOException | URISyntaxException e) {
            log.error("Error read properties file: " + e.getMessage());
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
