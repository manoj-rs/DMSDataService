package com.datamanagement.controller;


import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.datamanagement.exception.DataManagementException;
import com.datamanagement.service.IDataManagementService;


/**
 * File : DataMangementController.java
 * Description :These APIs to deploy the streams into the spring cloud dataflow server.
 * 
 */


@Controller
public class DataMangementController  {
	
	@Autowired
	private IDataManagementService dataManagementService;

	/**
	 * this api to deploy the input request in scdf server 	
	 * @param input
	 * @return
	 */
	
	 @RequestMapping(value="/fetchdata/{tablename}", method = RequestMethod.GET)
	    @ResponseBody
	    public JSONArray fetchdata(@PathVariable("tablename")String tablename){
		 JSONArray jobj=new JSONArray();
		JSONParser parser=new JSONParser();
		String result=null;
		try {
			result=dataManagementService.fetchFromDB(tablename);
			jobj=(JSONArray) parser.parse(result);		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jobj;
	 }
    @RequestMapping(value="/createschema", method = RequestMethod.POST)
    @ResponseBody
   	public String createschema(@RequestBody String input) {
    	String response = null;
    	response=dataManagementService.createSchema(input);
    	return response;
    }
    
    @RequestMapping(value="/createdmstable", method = RequestMethod.POST)
    @ResponseBody
   	public String createDmsDb(@RequestBody String input) {
    	String response = null;
    	response=dataManagementService.createDmsDb(input);
    	return response;
    }
    
    @RequestMapping(value="/insertdata/{tablename}", method = RequestMethod.POST)
    @ResponseBody
   	public String insertdata(@PathVariable("tablename")String tablename,@RequestBody String input) throws DataManagementException {
    	String response = null;
    	response=dataManagementService.insertData(tablename,input);
    	return response;
    }
    
    @RequestMapping(value="/droptable/{tablename}", method = RequestMethod.POST)
    @ResponseBody
   	public String droptable(@PathVariable("tablename")String tablename)  {
    	String response = null;
    	response=dataManagementService.dropTable(tablename);
    	return response;
    }
    
    @RequestMapping(value="/fetchschema/{tablename}", method = RequestMethod.GET,produces={"application/json"})
    @ResponseBody
   	public String fetchSchema(@PathVariable("tablename")String tablename) throws DataManagementException {
    	String response = null;
    	response=dataManagementService.fetchSchema(tablename);
    	return response;
    }
    
   
}

