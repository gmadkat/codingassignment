#Architect Coding homework assignment

This repository contains the Spring based Java source code for the REST API for Categories as defined 
in the requirements from Neustar. This project was developed on a Mac OS El Capitan. 


##Build Instructions:

The implementation for Version 1.0 of the coding assignment is checked into github on:
https://github.com/spring-guides/gs-spring-boot.git

After checking out the project, build with either Gradle or Maven:
./gradlew build 
or 
mvn package

Execute and launch the server using:
java -jar  build/libs/category-service-0.1.0.jar

There is a REST API swagger/springfox based UI on:

http://localhost:8080/v2/api-docs?group=categories

The Swagger redistributable is on the dist directory in this source tree. Run dist/index.html from any browser and use the url above after launching the webservice. 

##Description of the REST endpoints:

###Title : List All Categories.

URL : /category/list

Method : GET 

URL Params : none

###Title : Add a new Category

URL : /category/add

Method : POST

URL Params : /add?name='nameofcategory'

Parameters

Parameter name:	 Category name
Type:	String

Response Messages:

HTTP Status Code: 
200	Success
Added Value from Model:
{
  "categoryName": "string",
  "count": 0,
  "id": 0
}

401	Unauthorized

403	Forbidden

404	Not Found

500	Failure


###Title : Delete a Category

URL : /category/delete

Method : POST

URL Params : /delete?name='nameofcategory'

Parameters

Parameter name:	 Category name
Type:	String

Response Messages:

true/false

HTTP Status Code: 

200	Success

401	Unauthorized

403	Forbidden

404	Not Found

500	Failure

###Title : Clean a list of categories and sub categories

URL : /category/clean

Method : POST

URL Params : /cleanclasslist

Body details: The list is a array of Categories, a class that has two String members, a Category and a subCategory. catslist example.. '[{"categoryName": "Animal","subCategoryName": "Dog"},{"categoryName": "PERSON","subCategoryName": "Alice"}]'

Success Response: Success returns a clean list with no invalid categories and noduplicates. eg. '[{"categoryName": "Animal","subCategoryName": "Dog"},{"categoryName": "PERSON","subCategoryName": "Alice"}]'

Response Messages:

HTTP Status Code: 200	
Success

List of cleaned Categories:

[{
  "categoryName": "string",
  “subCategoryName": "string"
}]

401	Unauthorized

403	Forbidden

404	Not Found

500	Failure


###Title : Clean a list of categories and sub categories and return a summary of count of subcategories by category in a list.

URL : /category/countclasslist

Method : POST

URL Params : /cleanandcount?catslist='categorylist'

Parameter details: The list is a array of Categories, a class that has two String members, a Category and a subCategory. catslist example.. '[{"categoryName": "Animal","subCategoryName": "Dog"},{"categoryName": "PERSON","subCategoryName": "Alice"}]'

Success Response: Success returns a list of Categories with an integer count of the number of sub categories per categories summed up

Response Messages:

HTTP Status Code: 200	
Success

List of counted Categories:
[{
  "categoryName": "string",
  “count”: “integer”
  “id: : “integer”
}]

401	Unauthorized

403	Forbidden

404	Not Found

500	Failure

###Title : Clean a list of categories and sub categories, alternative API with String input

URL : /category/clean

Method : POST

URL Params : /clean

Body details: The list is a array of Categories in String format, a String with colon delimited Category and a subCategory. catslist example.. ‘[{“ANIMAL:dog”, ”ANIMAL:dog”, “PERSON:Alice"}]'

Success Response: Success returns a clean list with no invalid categories and noduplicates. eg. '[{"categoryName": "Animal","subCategoryName": "Dog"},{"categoryName": "PERSON","subCategoryName": "Alice"}]'

Response Messages:

HTTP Status Code: 200	
Success

List of cleaned Categories:

[{
  "categoryName": "string",
  “subCategoryName": "string"
}]

401	Unauthorized

403	Forbidden

404	Not Found

500	Failure


###Title : Clean a list of categories and sub categories in colon delimited String format, and return a summary of count of subcategories by category in a list.

URL : /category/countclasslist

Method : POST

URL Params : /cleanandcount?catslist='categorylist'

Body details: The list is a array of Categories in String format, a String with colon delimited Category and a subCategory. catslist example.. ‘[{“ANIMAL:dog”, ”ANIMAL:dog”, “PERSON:Alice"}]'

Success Response: Success returns a list of Categories with an integer count of the number of sub categories per categories summed up

Response Messages:

HTTP Status Code: 200	
Success

List of counted Categories:

[{
  "categoryName": "string",
  “count”: “integer”
  “id: : “integer”
}]

401	Unauthorized

403	Forbidden

404	Not Found

500	Failure


##Documentation and API Contract:

There is a REST API documentation using swagger/springfox based UI on:

http://localhost:8080/v2/api-docs?group=categories

Some mock screenshots of the API are on the docs directory in this source tree

NOTE: Authentication has not been added for this version but planned for the next version. 


##QA Plan:

1. Unit Tests are implemented using JUnit.

2. QA Internal Testbed in mock production environment to be implemented
 
3. QA Cloud Environment in mock production environment to be implemented 


##Deployment Strategies after QA clears the build:

1. Spring Web services to Cloud Foundry

2. Ansible/Puppet to edge sites for QA and production.


##Monitoring and Logging:

1. Monitor the health of the deployments

2. Log all requests coming in to an instance of the service


##Load Balancing changes to be implemented:

1. Store no state in the service

2. Make all requests atomic, commit only at the end of the operations and rollback if there is any failure at any point. 

3. Replicate changes from one instance to all the others so all are synchronized near real time so any service can pick up a request and be current

4. Add a health check capability to the service for the load balancers

5. Implement Spring security and authentication

