package com.herokuapp.utiles;

public enum APIEndpoints {
	
	
	ADD_USER_API("/createusers"),
	UPDATE_USER_BY_ID("updateuser/{userId}"),
	GET_USER_BY_ID("user/{userId}"),
	GET_USER_BY_FIRSTNAME("users/username/{userFirstName}"),
	GET_ALL_USERS("/users"),
	DELETE_USER_BY_ID("/deleteuser/{userId}"),
	DELETE_USER_BY_FIRSTNAME("/deleteuser/username/{userFirstName}");
	
	private String endpoints;

	APIEndpoints(String endpoints) {
		this.endpoints = endpoints;
	} 
	
	public String getEndpoint() {
		
		return endpoints;
	}
}
