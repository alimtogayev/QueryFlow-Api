QueryFlow API

Spring Boot backend for storing and safely executing SQL queries (only SELECT allowed).
Includes example passenger data based on the Titanic dataset.

Main Endpoints

POST /queries — save a new SQL query

GET /queries — get all stored queries

GET /execute/{id} — execute a stored query

POST /upload - upload dataset

Tech Stack

Java 17 · Spring Boot 3 · Spring Data JPA · JDBC · PostgreSQL · JUnit 5 · Mockito

Architecture

Controller → Service → Repository → Database
(QueryController → QueryService → QueryRepo / PassengerRepo)



Notes

Only SELECT queries are allowed.
