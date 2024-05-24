Feature: Admin User Creation
As a Admin
I need to able to add a new user
So that I can access the user 


Scenario: Verify if 'USER' is being Successfully added using by 'AddNewUserAPI'
Given Admin needs to create a new user with data from Excel row 6
When Admin sends a post HTTP Request to "ADD_USER_API" 
Then The API call is successful with status code 201 
#Then The "POST "response matches with the schema

Scenario: Validate that Admin able to Retrieve User by valid First Name using 'GetAPI'
Given Admin needs to get user with valid API endpoint
When Admin sends a GET HTTP Request to "GET_USER_BY_FIRSTNAME"
Then The API call is successful with status code 200
#Then The "GET" response matches with the schema

#Scenario: Validate that the successful update of user details for only First Name using 'PutAPI'
#Given Admin needs to update user with data from Excel row 2 
#When Admin sends a put HTTP request to "UPDATE_USER_BY_ID"
#Then The API call is successful with status code 200
#Then The response matches with the schema
#
#Scenario: Validate that the successful update of user details for only Last Name using 'PutAPI'
#Given Admin needs to update user with data from Excel row 3 
#When Admin sends a put HTTP request to "UPDATE_USER_BY_ID"
#Then The API call is successful with status code 200
#Then The response matches with the schema
#
#Scenario: Validate that the successful update of user details for only unique Contact Number using 'PutAPI'
#Given Admin needs to update user with data from Excel row 4 
#When Admin sends a put HTTP request to "UPDATE_USER_BY_ID"
#Then The API call is successful with status code 200
#Then The response matches with the schema
#
#Scenario: Validate that the successful update of user details for only unique Email Id using 'PutAPI'
#Given Admin needs to update user with data from Excel row 5 
#When Admin sends a put HTTP request to "UPDATE_USER_BY_ID"
#Then The API call is successful with status code 200
#Then The response matches with the schema
#
#
#Scenario: Validate that the successful update of user details of all fields using 'PutAPI'
#Given Admin needs to update user with data from Excel row 6 
#When Admin sends a put HTTP request to "UPDATE_USER_BY_ID"
#Then The API call is successful with status code 200
#Then The response matches with the schema


Scenario: Validate that Admin able to Retrieve User by valid User ID using 'GetAPI'
Given Admin needs to get user with valid API endpoint
When Admin sends a GET HTTP Request to "GET_USER_BY_ID"
Then The API call is successful with status code 200
#Then The response matches with the schema




#Scenario: Validate that Admin able to Retrieve All Users by 'GetAPI'
#Given Admin needs to get user with valid API endpoint
#When Admin sends a GET HTTP Request to "GET_ALL_USERS"
#Then The API call is successful with status code 200
#Then The response matches with the schema

Scenario: Validate that Admin able to Delete User By valid User ID using 'DeleteAPI'
Given Admin needs to delete user with valid API endpoint
When Admin sends a DELETE HTTP Request to "DELETE_USER_BY_ID"
Then The API call is successful with status code 200 

#Scenario: Validate that Admin able to Delete User by valid First Name using 'DeleteAPI'
#Given Admin needs to delete user with valid API endpoint
#When Admin sends a DELETE HTTP Request to "DELETE_USER_BY_FIRSTNAME"
#Then The API call is successful with status code 200 




