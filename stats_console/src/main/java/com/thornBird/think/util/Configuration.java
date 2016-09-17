package com.thornBird.think.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 读取properties工具类
 * @author hyman
 */
public class Configuration {

	private static final Logger logger = LoggerFactory.getLogger(Configuration.class);
	
    private static Configuration config;
    private Properties properties;
    private String path;

    private Configuration() {
    }

    private Configuration(String path) {
    	this.path = path;
    	initProperties();
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

    private String getRealPath()  throws FileNotFoundException {
		String realPath = "";
		if (path.startsWith("/")) {
			if (Configuration.class.getResource(path) == null) {
				throw new FileNotFoundException("Can not find file: " + path);
			}
			realPath = Configuration.class.getResource(path).getPath();
		} else {
			if (Configuration.class.getClassLoader().getResource(path) == null) {
				throw new FileNotFoundException("Can not find file: " + path);
			}
			realPath = Configuration.class.getClassLoader().getResource(path).getPath();
		}
		return realPath;
	}

	@SuppressWarnings("unused")
	private InputStream getInputStream() {
		InputStream is = null;

		if (path.startsWith("/")) {
			is = Configuration.class.getResourceAsStream(path);
		} else {
			is = Configuration.class.getClassLoader().getResourceAsStream(path);
		}

		return is;
	}
	
	private void initProperties() {
		logger.debug("Load properties: {}", path);
		InputStream is = null;
        try {
            File file = new File(path);
            if (!file.isFile()) {
                file = new File(getRealPath());
            }
            is = new FileInputStream(file);
            
            properties = new Properties();
            properties.load(is);
        } catch (FileNotFoundException e) {
        	properties = null;
        	logger.error(String.format("%s%s", "Read file fail: ", path), e);
        } catch (IOException e) {
        	properties = null;
        	logger.error(String.format("%s%s", "Read file fail: ", path), e);
        } catch (Exception e) {
        	properties = null;
			logger.error(String.format("%s%s", "Read file fail: ", path), e);
		} finally {
            try {
                if (null != is) {
                    is.close();
                }
            } catch (IOException e) {
            	logger.error("Close input stream error.", e);
            }
        }
	}

    public String getValue(String key) {
    	if (properties == null) {
    		return null;
    	}
    	
        if (properties.containsKey(key)) {
            return properties.getProperty(key);
        } else {
            return "";
        }
    }

    @SuppressWarnings("rawtypes")
	public Map<String, String> iteratorAll() {
    	if (properties == null) {
    		return null;
    	}
    	
    	Map<String, String> map = new HashMap<String, String>();
		Enumeration enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            String value = properties.getProperty(key);
            map.put(key, value);
        }
        
        return map;
    }

    public void writeProperties(String key, String value) {
    	if (properties == null) {
    		return;
    	}
    	
        OutputStream os = null;
        try {
        	File file = new File(path);
            if (!file.isFile()) {
                file = new File(getRealPath());
            }
            os = new FileOutputStream(file);
            
            properties.setProperty(key, value);
            properties.store(os, null);
            os.flush();
        } catch (FileNotFoundException e) {
        	logger.error(String.format("%s%s", "Write file fail: ", path), e);
        } catch (IOException e) {
        	logger.error(String.format("%s%s", "Write file fail: ", path), e);
        } catch (Exception e) {
			logger.error(String.format("%s%s", "Write file fail: ", path), e);
		} finally {
            try {
                os.close();
            } catch (IOException e) {
            	logger.error("Close output stream error.", e);
            }
        }
    }
    
    public static void main(String[] args) {
    	Configuration config = Configuration.getConfiguration("D:\\opt\\local\\jetty\\config\\configuration.properties");
//    	Configuration config = Configuration.getConfiguration("/opt/local/jetty/config/configuration.properties");
//    	Configuration config = Configuration.getConfiguration("application.properties");
//    	Configuration config = Configuration.getConfiguration("/application.properties");
    	Map<String, String> map = config.iteratorAll();
    	System.out.println(map.toString());
	}
}
