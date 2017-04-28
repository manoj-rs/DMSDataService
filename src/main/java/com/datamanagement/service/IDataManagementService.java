package com.datamanagement.service;



import com.datamanagement.exception.DataManagementException;


public interface IDataManagementService {
	
	public String fetchSchema(String tablename) throws DataManagementException;
	public String createSchema(String jsonString) ;
	public String createDmsDb (String jsonString) ;
	public String insertData(String tablename,String jsonString) ;
	public String dropTable (String tablename) ;
	public String fetchFromDB (String tablename);
	
}
