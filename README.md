# Account Example for Computer Security Class

## Purpose
The goal of this project was to show some secure practices for storing passwords in a database. To show some of these practices,
I made a basic Spring Boot application with a MYSQL database which stores user account information. The Web Application includes the
ability to create an account with a username and password and then sign into the account with a user specific welcome message.

## Password Storing Practices Shown
1. Generates Securely random salt that is unique for each user (protects against Rainbow Table attacks).​
2. Implements the slow hashing algorithm PBKDF2 (slows down Brute Force attacks).​​
3. Uses Entity Manager to interact with database (to prevent SQL injection attacks).

This project is from my Spring 2024 semester at CCSU.
