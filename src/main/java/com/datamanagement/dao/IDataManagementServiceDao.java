package com.datamanagement.dao;

import org.json.simple.JSONArray;

public interface IDataManagementServiceDao {
	
	public String fetchSchema(String tablename);
	public String createSchema(JSONArray jsonarray);
	public String insertData(String tablename,JSONArray jsonarray);
	public String createDmsDb(JSONArray jsonarray);
	public String dropTable(String tablename);
	public String fetchFromDb(String tablename);
	
	
}
