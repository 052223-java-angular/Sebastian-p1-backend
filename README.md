# P1 - Angular Object Database for Pure Data

## Introduction

Pure Data is a visual dataflow programming environment for dsp, sound design, and electronic music. https://puredata.info/. This is an interface to a common database of pure data objects and libraries. The database will contain info about objects such as what library they're in, and users can search by object name or list the objects in a library.

## User Stories

- **As a user**,  I want to register an account so that I can have a personalized browsing experience.
- **As a user**, I want to log in to my account so that I can add objects and libraries to the database.
- **As a user**, I want to create/edit descriptions for libraries and objects only when logged in.
- **As a user**, I want to browse through objects and libraries.
- **As a user**, I want to search for objects and libraries by name.

## MVP (Minimum Viable Product)

- User registration and login
- Browsing and searching for objects
- Adding objects and libraries to the database either manually or from .txt file
- ability to update library to new version from puredata.info text files/deken
- Modifying an object and/or library

## Stretch Goals

- Generate Dependency Tree for each object and/or library
- Import object/library data from .md file
- ability of server to download library and update names from object files. (potentially dealing w/ single-binary libraries and/or -meta.pd files)
- ability to add/search by library or object tag
- search by author

## Tech Stacks

- **Java**: The main programming language used for building the application on the server.
- **PostgreSQL**: Used as the database to store user, product, and order data.
- **Maven**: Used for managing project dependencies.
- **Spring Boot**: Used for serving http responses and creating routes/paths.
- **Angular and Typescript**: Used for displaying the site and client-side interaction.
- **JUnit**: A testing framework for Java applications, used to ensure our code works as expected.
- **Log4j**: A logging utility for debugging purposes.
- **JDBC (Java Database Connectivity)**: An API for connecting and executing queries on the database.
- **BCrypt**: A Java library for hashing and checking passwords for security.
- **JUnit and Mockito**: Used for unit and integration testing.
- **Git and GitHub**: Used for version control.

## Requirements

- **Clean Codebase**: All code should be clean and well-documented. The repository should not include any unnecessary files or folders such as the `target/`, `.DS_Store`, etc. All files and directories should be appropriately named and organized.

- **Database Design**: The database should be designed following the principles of the 3rd Normal Form (3NF) to ensure data integrity and efficiency. An Entity Relationship Diagram (ERD) should be included in the documentation.

- **Secure**: All sensitive user data such as passwords must be securely hashed before storing it in the database. The application should not display any sensitive information in error messages.

- **Error Handling**: The application should handle potential errors gracefully and provide clear and helpful error messages to the users.

- **Testing**: The application should have a high test coverage. Unit tests and integration tests should be implemented using JUnit, Mockito, and PowerMock.

- **Version Control**: The application should be developed using a version control system, preferably Git, with regular commits denoting progress.

- **Documentation**: The repository should include a README file with clear instructions on how to run the application. Code should be well-commented to allow for easy understanding and maintenance.

- **Scalable**: The design of the application should be scalable, allowing for easy addition of new features or modifications in the future.

