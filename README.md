## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Test Environment](#test-environment)

## General info
This Project is for Technical Assessment in EDTS. <br />
Requirement for this application are: <br />
* User can find or search ticket concert based on availability
* User can book a ticket according selected concert based on availability
* Availability will be based on quota of concert and time

## Technologies
Project is created with:
* Spring boot version 3.3.1
* Mysql version 8
* Java version 17
* Liquibase for Database Migration
* API Documentation using OpenApi Swagger can open in `http://localhost:8080/api/docs`

## Setup
To run this project
1. Install JDK 17
2. Install maven
3. Install Mysql 8
4. Create Database with name `reservation`
5. clone project from github `https://github.com/hasaycha/ticket.git`
6. duplicate and rename `src/main/resource/application.yml.example` into `src/main/resource/application.yml`
7. Modify `application.yml` file for database connection
8. run application, example `./mvnw spring-boot:run`
9. check database it should automatically create tables
10. Access `http://localhost:8080/api/docs` for checking API documentation

## Test Environment
This Application has unit test and also already tested using Jmeter for handling mechanism for race booking <br />
or multiple booking at a time