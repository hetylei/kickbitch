package com.winnie.pub.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.ResourceBundle;

public class PropertyUtil {
	private String propertyName = null;

	private Properties res = null;

	private void init() {
		res = new Properties();
        try {
            res.load(new InputStreamReader(PropertyUtil.class.getClassLoader().getResourceAsStream(propertyName+".properties"), "GBK"));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

	public PropertyUtil(String propertyName) {
		this.propertyName = propertyName;
		init();
	}

	public String getValue(String title) {
		return res.getProperty(title);
	}
}
