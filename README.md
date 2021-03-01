# Job-Candidates-App
Application for recording job candidates and their skills as the separate entities.

Technology stack: 
- Java 8
- MySQL

Subject:
- System based on knowledge

Description:
 - Project is created with Maven build tool and it is powered by the 
   Apache Tomcat web server. 
 - Communication between application and database has been realised with 
   object relational mapping supported by Hibernate framework.
 - Since this is a web application, also I used a Spring MVC to realise a
   Representational State Transfer (REST) Between client, web server (Apache),
   service layer (contains the buisness logic of application) and database.
 - Application contains JUnit tests of the service and controller layers.
 - Application supports Swagger for simpler way to testing RESTful APIs.
 - Application contains validation of all requests on the Fornt Controller
   and database layer.
 - Code has been writed using Lombok Plug-in to avoid writing constructors,
   getters, setters...
 - Application contains Resolver to allows exceptions handling

Application url: http://localhost:8080
Swagger url: http://localhost:8080/swagger-ui.html#/

Recommended use case:
  - add the company needs skills
  - add job candidates and conect them to skills
