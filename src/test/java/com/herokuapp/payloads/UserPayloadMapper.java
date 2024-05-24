package com.herokuapp.payloads;

import java.util.Map;

import com.herokuapp.pojo.UserPayload;
import com.herokuapp.utiles.ExcelOperator;

public class UserPayloadMapper {

	public String userId;
	public String userFirstName;
	ExcelOperator excelReader;
	String excelPath;
	String sheetName;
	

    public UserPayloadMapper(String excelPath, String sheetName) {
    	this.excelPath= excelPath;
    	this.sheetName = sheetName;
        this.excelReader = ExcelOperator.getInstance();
        
    }
    public UserPayload mapRowToUserPayload(int rowNumber) {
    	
        Map<String, Object> data = excelReader.getRowData(excelPath, sheetName,rowNumber);

    
    String zip = (String)(data.get("Zipcode"));
	UserPayload.Address address = new UserPayload.Address(
			(String)data.get("PlotNo"), (String)data.get("Street"), (String)data.get("State"),
			(String)data.get("Country"), zip);
	
	String contactno = (String)data.get("ContactNo");
	UserPayload userPayload = new UserPayload(
			(String)data.get("FirstName"), (String)data.get("LastName"), 
			contactno,(String) data.get("EmailId"), address); 	
	
		/*
		 * Map<String, String> data = excelReader.getRowData(rowNum);
		 * UserPayload.Address address = new UserPayload.Address(
		 * data.get("PlotNo"),data.get("Street"),data.get("State"),
		 * data.get("Country"),data.get("Zipcode")); UserPayload userPayload = new
		 * UserPayload( data.get("FirstName"), data.get("LastName"),
		 * data.get("ContactNo"),data.get("EmailId"), address);
		 */
	
	
	 return userPayload;
    }
   
    
    public void writeUserIdToExcel(int rowNum, String userId) {
        excelReader.writeData(excelPath,sheetName,rowNum, "UserId", userId);
        
    }
    public void writeUserFNameToExcel(int rowNum, String userFName) {
        excelReader.writeData(excelPath,sheetName,rowNum, "UserFName", userFName);
        
    }
    
    public void setConstantsFromExcel(int rowNum) {
        Map<String, Object> data = excelReader.getRowData(excelPath, sheetName,rowNum);

        userId = data.get("UserId").toString();
        userFirstName = data.get("UserFName").toString();
    }
}
