# P1 - Angular Object Database for Pure Data

## Introduction

Pure Data is a visual dataflow programming environment for dsp, sound design, and electronic music. https://puredata.info/. This is an interface to a common database of pure data objects and libraries. The database will contain info about objects such as what library they're in, and users can search by object name or list the objects in a library.

## User Stories

- **As a user**,  I want to register an account so that I can have a personalized browsing experience.
- **As a user**, I want to log in to my account so that I can add objects and libraries to the database.
- **As a user**, I want to create/edit descriptions for libraries and objects only when logged in.
- **As a user**, I want to browse through objects and libraries.
- **As a user**, I want to search libraries and objects by name
- **As a user**, I want to leave comments on objects regarding their use and implementation
- **As a user**, I want to search objects by tag/category
- **As a user**, I want to add a profile picture that can be viewed by other users
- **As a user**, I want a list of libraries and objects recommended to me based on my previous view history.

## MVP (Minimum Viable Product)

- User registration and login
- Browsing libraries and objects
- Adding objects and libraries to the database
- ability to update library to new version from puredata.info text files/deken
- search by object or library name, or tag
- Modifying an object and/or library
- Per-object Comments
- allow users to query latest version from puredata.info/deken in order to update objects or create libraries.
- User has a profile with an optional picture and recommendations

## Stretch Goals

- Generate Dependency Tree for each object and/or library
- Import object/library data from .md file
- ability of server to download library and update names from object files. (potentially dealing w/ single-binary libraries and/or -meta.pd files)
- add objects from .txt file
- ability to add tags to search by
- search by author
- "following" activity in a library & receiving notifications
- link to library source code

## Tech Stacks

- **Java**: The main programming language used for building the application on the server.
- **PostgreSQL**: Used as the database to store user, object, and library data.
- **Maven**: Used for managing project dependencies.
- **Spring Boot**: Application Framework for creating web server backend and logging.
- **Angular and Typescript**: Used for displaying the site and client-side interaction.
- **Spring Data JPA**: Represents entity & data access layer.
- **BCrypt**: A Java library for hashing and checking passwords for security.
- **JUnit and Mockito/Spring-Boot-Test**: Used for unit and integration testing.
- **Log4j**: A logging utility for debugging purposes.
- **Git and GitHub**: Used for version control.
- **Amazon Web Services (AWS)**: Will be used for hosting the application and the database, and managing other services like Lambda, S3, etc.
- **Hibernate**: For object-relational mapping (ORM), allowing Java to interact with the database in an object-oriented manner.

Amazon S3 is used to store profile pictures

## Running
Running requires java (preferably java 17) https://www.java.com/en/download/ and maven https://maven.apache.org/download.cgi

to run the backend, use the maven wrapper: ```./mvnw spring-boot: run```

configure the appropriate variables in an 'application.properties' file in the 'src/main/resources' folder. Set configuration for the relational datasource (which uses jdbc) and hibernate.

set ```jwt.secret``` to a secret many-byte 64-bit encoded string

### Profile Picture Support
Profile pictures require an S3 bucket.
For S3 you have to set these in application.properties:
```amazonProperties.bucketName``` to your bucket name
```amazonProperties.accessKey``` to the user access key
```amazonProperties.secretKey``` to the user secret key
``amazonProperties.region``` to the s3 bucket's region