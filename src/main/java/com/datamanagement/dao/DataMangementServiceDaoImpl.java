package com.datamanagement.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DataMangementServiceDaoImpl implements IDataManagementServiceDao {

	// @Autowired
	// CloudFoundryDatabaseConfig cloudFoundryDatabaseConfig;

	@Autowired
	DataSource dataSource;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	

	@Override
	public String fetchSchema(String tablename) {
		System.out.println("fetchSchema :" + tablename);
		// dataSource = cloudFoundryDatabaseConfig.dataSource();
		// setDataSource(dataSource);
		String key = null;
		String fetchresult = null;
		JSONObject json_obj = new JSONObject();
		List<Map<String, Object>> getschemaname = jdbcTemplate
				.queryForList("select distinct `schema_name` from " + tablename);
		System.out.println(getschemaname.toString());
		for (Map<String, Object> map : getschemaname) {
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				key = (String) entry.getValue();
				System.out.println("the key is...." + key);

				List<Map<String, Object>> result = jdbcTemplate.queryForList(
						"select `key`,`description` from " + tablename + " where `schema_name`='" + key + "'");
				fetchresult = convertListToJson(result);
			}
			json_obj.put(key, fetchresult);

		}
		System.out.println("fetchSchema :" + json_obj.toString());
		return json_obj.toString();
	}

	@SuppressWarnings("unchecked")
	private String convertListToJson(List<Map<String, Object>> mapList) {

		JSONArray json_arr = new JSONArray();
		for (Map<String, Object> map : mapList) {
			JSONObject json_obj = new JSONObject();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				json_obj.put(key, value);
			}
			json_arr.add(json_obj);
		}
		System.out.println("json_arr.toString() ------------>" + json_arr.toString());

		return json_arr.toString();
	}

	@Override
	public String createSchema(JSONArray jarray) {
		String key = null;
		String tableName = null;
		JSONObject jobj = new JSONObject();
		StringBuffer sb = new StringBuffer();
		String output = null;
		ArrayList<String> columnkeys = new ArrayList<>();
		try {
			// dataSource = cloudFoundryDatabaseConfig.dataSource();
			// setDataSource(dataSource);
			for (int i = 0; i < jarray.size(); i++) {
				jobj = (JSONObject) jarray.get(i);
				if (jobj.containsKey("tablename")) {
					tableName = (String) jobj.get("tablename");
				}
				if (jobj.containsKey("columnkeys")) {
					columnkeys = (ArrayList<String>) jobj.get("columnkeys");
				}
			}
			for (String column : columnkeys) {
				sb.append("`").append(column).append("`").append(" ").append("varchar(2000)").append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			String query = "create table if not exists " + tableName + " (" + sb.toString() + ")";
			jdbcTemplate.execute(query);
			output = "Table created successfully";
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			output = "Table creation failed";
		}
		return output;
	}

	@Override
	public String insertData(String tableName, JSONArray jarray) {
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		JSONObject jobj = new JSONObject();
		String key = null;
		String output = null;
		try {
			// dataSource = cloudFoundryDatabaseConfig.dataSource();
			// setDataSource(dataSource);
			for (int i = 0; i < jarray.size(); i++) {
				jobj = (JSONObject) jarray.get(i);
				for (Iterator iterator = jobj.keySet().iterator(); iterator.hasNext();) {
					key = (String) iterator.next();
					String description = jobj.get(key).toString();
					sb.append("`").append(key).append("`").append(",");
					sb1.append("'").append(description).append("'").append(",");
				}
				sb.deleteCharAt(sb.length() - 1);
				sb1.deleteCharAt(sb1.length() - 1);
				String query = "insert into " + tableName + "(" + sb.toString() + ")" + " values(" + sb1.toString()
						+ ")";
				jdbcTemplate.update(query);
				sb.setLength(0);
				sb1.setLength(0);
			}
			output = "Data inserted successfully";
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			output = "Data insertion failed";

		}
		return output;
	}

	@Override
	public String createDmsDb(JSONArray jarray) {
		String key = null;
		String tableName = null;
		JSONObject jobj = new JSONObject();
		StringBuffer sb = new StringBuffer();
		String output = null;
		ArrayList<String> columnkeys = new ArrayList<>();
		try {
			// dataSource = cloudFoundryDatabaseConfig.dataSource();
			// setDataSource(dataSource);
			for (int i = 0; i < jarray.size(); i++) {
				jobj = (JSONObject) jarray.get(i);
				if (jobj.containsKey("tablename")) {
					tableName = (String) jobj.get("tablename");
				}
				if (jobj.containsKey("columnkeys")) {
					columnkeys = (ArrayList<String>) jobj.get("columnkeys");
				}
			}
			for (String column : columnkeys) {
				sb.append("`").append(column).append("`").append(" ").append("text").append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			String query = "create table if not exists " + tableName + " (" + sb.toString() + ")";
			jdbcTemplate.execute(query);
			output = "Table created successfully";
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			output = "Table creation failed";
		}
		return output;
	}

	@Override
	public String dropTable(String tablename) {
		String output = null;
		try {
			// dataSource = cloudFoundryDatabaseConfig.dataSource();
			// setDataSource(dataSource);
			jdbcTemplate.execute("drop table " + tablename);

			output = "Table dropped successfully";
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return output;
	}

	@Override
	public String fetchFromDb(String tablename) {
		// dataSource = cloudFoundryDatabaseConfig.dataSource();
		// setDataSource(dataSource);
		List<Map<String, Object>> result = jdbcTemplate.queryForList("select * from " + tablename);
		String schemaData = convertListToJson(result);
		return schemaData;
	}

}
