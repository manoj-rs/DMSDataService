package com.datamanagement.service;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datamanagement.dao.IDataManagementServiceDao;
import com.datamanagement.exception.DataManagementException;
@Component
public class DataManagementServiceImpl implements IDataManagementService {
	@Autowired
	private IDataManagementServiceDao dataMangementServiceDao;
	
	@Override
	public String fetchSchema(String tablename) throws DataManagementException {
		// TODO Auto-generated method stub
		return dataMangementServiceDao.fetchSchema(tablename);
	}

	@Override
	public String createSchema(String jsonString) {
		// TODO Auto-generated method stub
		JSONParser parser=new JSONParser();
		JSONArray jarray=new JSONArray();
		try {
			jarray=(JSONArray) parser.parse(jsonString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return"parse failed";
		}
		return dataMangementServiceDao.createSchema(jarray);
	}

	@Override
	public String createDmsDb(String jsonString) {
		JSONParser parser=new JSONParser();
		JSONArray jarray=new JSONArray();
		try {
			jarray=(JSONArray) parser.parse(jsonString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataMangementServiceDao.createDmsDb(jarray);
	}

	@Override
	public String insertData(String tablename, String jsonString) {
		JSONParser parser=new JSONParser();
		JSONArray jarray=new JSONArray();
		try {
			jarray=(JSONArray) parser.parse(jsonString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataMangementServiceDao.insertData(tablename,jarray);
	}

	@Override
	public String dropTable(String tablename) {
		// TODO Auto-generated method stub
		return dataMangementServiceDao.dropTable(tablename);
	}

	@Override
	public String fetchFromDB(String tablename) {
		// TODO Auto-generated method stub
		return dataMangementServiceDao.fetchFromDb(tablename);
	}

}
