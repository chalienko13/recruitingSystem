package com.netcracker.solutions.kpi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    private static final String PROPERTIES_FILE ="app.properties";

    private static Logger log = LoggerFactory.getLogger(PropertiesReader.class.getName());

    private static class SingletonHolder {
        static final PropertiesReader HOLDER_INSTANCE = new PropertiesReader();
    }

    public static PropertiesReader getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    private static String prop;

    public String propertiesReader(String property) {

        Properties p = new Properties();

        try (InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            p.load(fileInputStream);
            prop = p.getProperty(property);
        } catch (IOException e) {
            log.error("File not found", e);
        }
        return prop;
    }

}
