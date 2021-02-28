# Job-Candidates-App
Application for recording job candidates and their skills as the separate entities.

Technology stack: 
- Java 8
- MySQL

Subject:
- System based on knowledge

Description:
 - Project is created using Maven build tool and runs with Apache Tomcat
   web server. 
 - Communication between application and database has been realised with 
   object relational mapping supported by Hibernate framework.
 - Since this is a web application, also I used a Spring MVC to realise a
   Representational State Transfer (REST) Between client, web server (Apache),
   service layer (contains the buisness logic of application) and database.
 - Application contains Junit tests of the service and controller layers.
 - Application supports Swagger for simpliest way to testing RESTful APIs.
 - Application contains the validation of all requests on the Fornt Controller
   and database layer.
 - Code has been writed using Lombok Plug-in to avoid writing of constructors,
   getters, setters...
 - Application contains Resolver to allows exceptions handling

Application url: http://localhost:8080
Swagger url: http://localhost:8080/swagger-ui.html#/

Recommended use case:
  - add the company needs skills
  - add job candidates and conect them to skills
