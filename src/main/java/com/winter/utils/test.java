package com.winter.utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class test {
	public String tableName;
	public Map<String, String> keys = new HashMap<String, String>();
	public Map<String, InputStream> blobs = new HashMap<String, InputStream>();

	public test(String tableName) {
		this.tableName = tableName;
	}
}