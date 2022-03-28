# CRUD Application using Slick and MySQL

- This template shows how to create a CRUD application in Scala using **Slick** and **MySQL**.
- **CRUD** refers to the four functions that are considered necessary to implement a persistent storage application: **Create**, **Read**, **Update** and **Delete**.
- This template will help you to understand these four major database operations and how to implement them in Scala.
- We have used ```Slick```, a modern database query and access library for Scala that allows you to work with stored data almost as if you were using Scala collections, and ```MySQL``` as a relational database. 


## Prerequisites

- MySQL, version 8.0.26
- Scala Build Tool(SBT), version 1.6.2
- Scala, version 2.13.8

## Steps for project execution

1. In [application.conf](src/main/resources/application.conf) file, add your mysql username and password.
2. Run the tests, using ```sbt test```