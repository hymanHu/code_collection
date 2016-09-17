package com.thornBird.think.zookeeper.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Configuration {

    private static Log log = LogFactory.getLog(Configuration.class);
    private static Configuration config;
    private Properties properties;

    private Configuration() {
    }

    private Configuration(String path) {
        properties = new Properties();
        InputStream is = null;
        try {
            File file = new File(path);
            if (!file.isFile()) {
                file = new File(getRealPath("/configuration.properties"));
            }
            is = new FileInputStream(file);
            properties.load(is);
        } catch (FileNotFoundException e) {
            log.info("File not exist!", e);
        } catch (IOException e) {
            log.info("Read file error!", e);
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
            } catch (IOException e) {
            	log.info("Configuration: ", e);
            }
        }
    }

    public static Configuration getConfiguration(String path) {
        if (config == null) {
            synchronized (Configuration.class) {
                if (config == null) {
                    config = new Configuration(path);
                }
            }
        }
        return config;
    }

    public static String getRealPath(String path) {
        String tempString = Configuration.class.getResource(path).getPath();
        return tempString;
    }

    public String getValue(String key) {
        if (properties.containsKey(key)) {
            return properties.getProperty(key);
        } else {
            return "";
        }
    }

    public void iteratorAll() {
        Enumeration enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            String value = properties.getProperty(key);
            if(log.isDebugEnabled()){
            	log.debug(key + "=" + value);
            }
        }
    }

    public void writeProperties(String path, String key, String value) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(getRealPath(path));
            properties.setProperty(key, value);
            properties.store(os, null);
            os.flush();
        } catch (FileNotFoundException e) {
        	log.info("writeProperties:", e);
        } catch (IOException e) {
        	log.info("writeProperties:", e);
        } finally {
            try {
                os.close();
            } catch (IOException e) {
            	log.info("writeProperties:", e);
            }
        }
    }
}
