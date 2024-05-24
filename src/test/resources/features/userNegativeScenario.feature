Feature: Admin User Creation
As a Admin
I need to able to add a new user
So that I can access the user 

#Scenario: Negative - Validate First Name with Missing Value for 'AddNewUserAPI'
#Given Admin needs to create a new user with data from Excel row 2
#When Admin sends a post HTTP Request to "ADD_USER_API"
#Then The API call fails with status code 400 
#And Validate the "message" with value feild "user FirstName is mandatory and should contains alphabets only"
#
#Scenario: Negative - Validate First Name with Invalid Value for 'AddNewUserAPI'
#Given Admin needs to create a new user with data from Excel row 2
#When Admin sends a post HTTP Request to "ADD_USER_API"
#Then The API call fails with status code 400 
#And Validate the "message" with value feild "user FirstName is mandatory and should contains alphabets only"

Scenario: Negative - Validate Contact Number with Missing Value for 'AddNewUserAPI'
Given Admin needs to create a new user with data from Excel row 2
When Admin sends a post HTTP Request to "ADD_USER_API"
Then The API call fails with status code 400 
And Validate the "message" with value feild "Phone Number is required and should contains 10 numeric values only"

Scenario: Negative - Validate Contact Number with Invalid value for 'AddNewUserAPI'
Given Admin needs to create a new user with data from Excel row 2
When Admin sends a post HTTP Request to "ADD_USER_API"
Then The API call fails with status code 400 
And Validate the "message" with value feild "Phone Number is required and should contains 10 numeric values only"

Scenario: Negative - Validate Contact Number with Existing Value for 'AddNewUserAPI'
Given Admin needs to create a new user with data from Excel row 2
When Admin sends a post HTTP Request to "ADD_USER_API"
Then The API call fails with status code 400 
And Validate the "message" with value feild "Phone Number is required and should contains 10 numeric values only"

