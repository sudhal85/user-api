package com.herokuapp.stepDefinition;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.Map;

import com.herokuapp.payloads.UserPayloadMapper;
import com.herokuapp.pojo.UserPayload;
import com.herokuapp.utiles.APIEndpoints;
import com.herokuapp.utiles.Constants;
import com.herokuapp.utiles.ExcelOperator;
import com.herokuapp.utiles.ReusableMethods;

import static org.junit.Assert.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.restassured.response.Response;


public class AddStepDefinition extends ReusableMethods{
	private static final String EXCEL_PATH = "C:\\Users\\sudha\\eclipse-workspace\\user-api\\src\\test\\resources\\testData\\userdata1.xlsx";
    private static final String SHEET_NAME_POST = "post";
    private static final String SHEET_NAME_PUT = "update";
	Response response;
	ResponseSpecification responseSpec;
	RequestSpecification request;
	private UserPayload userPayload;
	static UserPayloadMapper userPayloadMapper = new UserPayloadMapper(EXCEL_PATH,SHEET_NAME_POST);
	static int rowNo;
	static String  userId;
	static String userFirstName;
	
	

	 @Given("Admin needs to create a new user with data from Excel row {int}")
	 public void admin_needs_to_create_a_new_user_with_data_from_excel_row(Integer rowNumber) throws IOException {
		rowNo = rowNumber;
		
		userPayload = userPayloadMapper.mapRowToUserPayload(rowNumber);
		
        request =given()
        		.spec(requestSpecification())
        		.body(userPayload);
    
	}
    
	 @When("Admin sends a post HTTP Request to {string}")
	 public void admin_sends_a_post_http_request_to(String endpoint) {
		
		APIEndpoints endpointAPI = APIEndpoints.valueOf(endpoint);

		response = request
				   .when().post(endpointAPI.getEndpoint());
		   
		System.out.println(response.getBody().asString());
		if(response.statusCode() == 201 ) {
		userId = getJsonPath(response, "user_id");
//		Constants.userId = userId;
		userFirstName = getJsonPath(response, "user_first_name");

		userPayloadMapper.writeUserIdToExcel(rowNo,userId);
		userPayloadMapper.writeUserFNameToExcel(rowNo,userFirstName);
		
		}
	}
	 
	 @Then("The {string} response matches with the schema")
	 public void the_response_matches_with_the_schema(String request) {
		 if(request.equalsIgnoreCase("POST")) {
		 validateResponseSchema(response, "schema/postSuccessSchema.json");
		 }else if(request.equalsIgnoreCase("GET")) {
			 validateResponseSchema(response, "schema/getSuccessSchema.json");
			 }
	 }
	 
	 @Then("The API call is successful with status code {int}")
	 public void the_api_call_is_successful_with_status_code(int expectedStatuscode) {
		 assertEquals(response.getStatusCode(),expectedStatuscode);
			
	}
	 
	 
//	 @Then("Verify user_first_name created user matches with user_first_name from Excel")
//	 public void verify_user_first_name_created_user_matches_with_user_first_name_from_excel() throws IOException {
//	    String userFirstName = getJsonPath(response, "user_first_name");
//	    String url = APIEndpoints.GET_USER_FIRSTNAME.getEndpoint().replace("{userFirstName}", userFirstName);
//	    response = given().spec(requestSpecification()).when().get(url);
//	    
//	}
	 
	 @Given("Admin needs to update user with data from Excel row {int}")
	 public void admin_needs_to_update_user_with_valid_api_endpoint(int rowNumberPut ) throws IOException {
//		 userPayloadMapper.setConstantsFromExcel(rowNo);
			userPayload = userPayloadMapper.mapRowToUserPayload(rowNumberPut);
			
	        request =given()
	        		.spec(requestSpecification())
	        		.body(userPayload);
	 }

	 @When("Admin sends a put HTTP request to {string}")
	 public void admin_sends_a_put_http_request_to(String endpoint) {
		String url = APIEndpoints.UPDATE_USER_BY_ID.getEndpoint().replace("{userId}",userId);
		
		response = request
				   .when().put(url);
	 }

	 
	 @Given("Admin needs to get user with valid API endpoint")
	 public void admin_needs_to_get_user_with_valid_api_endpoint() throws IOException {
		 request =given()
	        		.spec(requestSpecification());
	 }

	 @When("Admin sends a GET HTTP Request to {string}")
	 public void admin_sends_a_get_http_request_to(String endPoint) {
		 userPayloadMapper.setConstantsFromExcel(rowNo);
		 System.out.println("UserId: " + userPayloadMapper.userId);
	        System.out.println("UserFirstName: " + userPayloadMapper.userFirstName);
		 if(endPoint.equals("GET_USER_BY_ID")) {
		 String url = APIEndpoints.GET_USER_BY_ID.getEndpoint().replace("{userId}",userPayloadMapper.userId);
		 response = request
				   .when().get(url);
		 }else if(endPoint.equals("GET_USER_BY_FIRSTNAME")) {
			 String url = APIEndpoints.GET_USER_BY_FIRSTNAME.getEndpoint().replace("{userFirstName}", userPayloadMapper.userFirstName);
			 response = request
					   .when().get(url);
		 }else if(endPoint.equals("GET_ALL_USERS")) {
			 APIEndpoints endpointAPI = APIEndpoints.valueOf(endPoint);
			 response = request
					   .when().get(endpointAPI.getEndpoint());
		 }
	 }
	 
	 @Given("Admin needs to delete user with valid API endpoint")
	 public void admin_needs_to_delete_user_with_valid_api_endpoint() throws IOException {
		 request =given()
	        		.spec(requestSpecification());
	 }

	 @When("Admin sends a DELETE HTTP Request to {string}")
	 public void admin_sends_a_delete_http_request_to(String endPoint) {
		 userPayloadMapper.setConstantsFromExcel(rowNo);
		 if(endPoint.equals("DELETE_USER_BY_ID")) {
			 String url = APIEndpoints.DELETE_USER_BY_ID.getEndpoint().replace("{userId}",userPayloadMapper.userId);
			 response = request
					   .when().delete(url);
			 }else if(endPoint.equals("DELETE_USER_BY_FIRSTNAME")) {
				 String resources = APIEndpoints.DELETE_USER_BY_FIRSTNAME.getEndpoint();
				 String url = resources.replace("{userFirstName}", userPayloadMapper.userFirstName);
				 System.out.println(url);
				 response = request
						   .when().delete(url);
			 }
	 }

    
	 @Then("The API call fails with status code {int}")
	 public void the_api_call_fails_with_status_code(int expectedStatuscode) {
	    
	     assertEquals(response.getStatusCode(),expectedStatuscode);
	     
	 }
	 @Then("Validate the {string} with value feild {string}")
	 public void validate_the_feild_with_value(String field, String expectedValue) {
		  response.then().assertThat().body(field,equalTo(expectedValue));
	 }


	
}
