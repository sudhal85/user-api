package com.herokuapp.utiles;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class ReusableMethods {

	Properties props = null;
	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;

	public RequestSpecification requestSpecification() throws IOException {

		if (requestSpec == null) {
			PrintStream log = new PrintStream(new FileOutputStream("logtofile.txt"));
			requestSpec = new RequestSpecBuilder()
			.setBaseUri(getGlobalProperty("baseUrl"))
			.setAuth(io.restassured.RestAssured.basic(getGlobalProperty("username"), getGlobalProperty("password")))
			.setContentType("application/json")
			.addFilter(RequestLoggingFilter.logRequestTo(log))
			.addFilter(ResponseLoggingFilter.logResponseTo(log)).build();
//			RequestSpecification requestSpec = builder.build();
			return requestSpec;
		}
		return requestSpec;
	}
	
	public ResponseSpecification responseSpecification() {
		ResponseSpecification responseSpec = new ResponseSpecBuilder()
	    		   .expectContentType(ContentType.JSON).expectHeader("Server","Cowboy").build();
		return responseSpec;
	}
	
	public String getJsonPath(Response response, String key) {
		String responsebody = response.asString();
		JsonPath js = new JsonPath(responsebody);
		return js.get(key).toString();
		
	}
	
	 public void validateResponseSchema(Response response, String schemaFilePath) {
		 response.then().spec(responseSpecification()).assertThat().body(JsonSchemaValidator
				   .matchesJsonSchemaInClasspath("schema/PostSuccessSchema.json"));
	    }
	
	public String getGlobalProperty(String key) throws IOException {
		if (props == null) {
			props = new Properties();
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("global.properties");
			props.load(is);
		}
		return props.getProperty(key);

	}
	
	
}
