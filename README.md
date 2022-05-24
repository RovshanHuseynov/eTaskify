# eTaskify
# ABB eTaskify Project 
# written by Rovshan Huseynov
# https://github.com/RovshanHuseynov/eTaskify on 24.05.2022

# General info
The project uses h2 in-memory database to be easy to run
server.port=8099 (http://localhost:8099)
h2 database url: http://localhost:8099/h2-console
h2 database username=sa
h2 database password=

# RUNNING SEQUENCE OF THE PROJECT
1. Run ETaskifyApplication class (main class)
2. execute this link on your browser: http://localhost:8099/h2-console
3. write url, username, password (check # General info section) and click Connect button
4. you can select tables here or insert into values (examples are in data-h2.sql file)
5. open postman app or cmd and start sending requests

# Tested Requests on Postman
1. http://localhost:8099/company (Get Request)

2. http://localhost:8099/company (Post Request)
   Request Body
   {
   "name": "test company",
   "phoneNumber": "0123123",
   "address": "Ataturk street",
   "email": "testcompany@gmail.com",
   "employees": []
   }
   
3. http://localhost:8099/company (Put Request)
   Request Body
   {
   "id": 4,
   "name": "newName",
   "phoneNumber": "newNumber",
   "address": "newStreet",
   "email": "newEmail@gmail.com",
   "username": "newUsername",
   "password": "newPassword",
   "employees": []
   }

4. http://localhost:8099/employee/1 (Post Request)
   Request Body
   {
   "id": 4,
   "name": "kamil",
   "surname": "kamilli",
   "email": "kamil@gmail.com",
   "tasks": []
   }

5. http://localhost:8099/employee  (Put Request)
   Request Body
   {
   "id": 4,
   "name": "changeKamil",
   "surname": "changeKamilli",
   "email": "changeKamil@gmail.com",
   "password": "changeUFNrZbou",
   "tasks": []
   }

6. http://localhost:8099/task   (Get Request)

7. http://localhost:8099/task (Post Request)
   Request Body
   {
   "title": "writing documentations",
   "description": "writing documentations for all the projects",
   "deadline": "2022-06-20",
   "status": "NEW"
   }

8. http://localhost:8099/tasksofEmp/1  (Get Request)