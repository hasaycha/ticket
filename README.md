## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)

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

## Setup
To run this project
1. Install JDK 17
2. Install maven
3. Install Mysql 8
4. clone project from github `https://github.com/hasaycha/ticket.git`
5. duplicate and rename `src/main/resource/application.yml.example` into `src/main/resource/application.yml`
6. run application, example `./mvnw spring-boot:run`

## Test Environment
This Application has unit test and also already tested using Jmeter for handling mechanism for race booking <br />
or multiple booking at a time