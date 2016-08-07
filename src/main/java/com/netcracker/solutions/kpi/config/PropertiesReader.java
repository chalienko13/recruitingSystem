package com.netcracker.solutions.kpi.config;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
public class PropertiesReader {

    private static final String PROPERTIES_FILE = "app.properties";

    private static String prop;

    public String propertiesReader(String property) {

        Properties p = new Properties();

        try (InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            p.load(fileInputStream);
            prop = p.getProperty(property);
        } catch (IOException e) {
        }
        return prop;
    }
}
