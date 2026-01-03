Flintzy Social Media Management Tool – Backend Project Overview

This Spring Boot backend application allows users to:

Authenticate via Google Login

Connect their facebook accounts

Link their Facebook Pages

Publish posts to linked Facebook Pages

Secure APIs using JWT authentication

Maintain browser session using HttpOnly cookies

Handle errors via global exception handling

The project follows clean architecture, interface-based services, and structured Git commits.

Tech Stack :-

Java 18

Spring Boot 3.5.x

Spring Security + JWT

Spring Data JPA

MySQL

Facebook Graph API

Maven

Postman (for testing)

Project Structure :-

com.socialmediamanagement.api
│
├── controller
├── dto
├── entity
├── exceptions
├── repository
├── security
├── service
├── serviceImpl
└── utility

Authentication & Cookie Flow:-

Step 1:- Google Login (Browser)

Endpoint

http://localhost:8080/auth/google/login

Flow :-

User logs in via Google OAuth

User details are saved in the database

Backend sets HttpOnly cookie userId

Backend generates and returns JWT token

Browser Response

{
  "status": "Google Login Successful",
  "jwt": "<JWT_TOKEN>"
}


Step 2 :- Facebook Login (Browser)

Endpoint

http://localhost:8080/auth/facebook/login


Flow :-

Can proceed only if userId cookie exists

User logs in via Facebook OAuth

Facebook User Access Token is fetched

User Access Token is stored in DB for that userId

Browser Response

{
  "status": "Facebook Login Successful"
}

Step 3:- Link Facebook Pages (Postman – JWT Required)

API is called from Postman

JWT token is mandatory

Backend fetches Facebook Pages using stored User Access Token from DB and links it to the user

Backend returns pageId and pageName

Endpoint: POST /facebook/link

Headers: Authorization: Bearer <JWT_TOKEN>

Response Example:  [
{
"pageId": "<PAGE_ID>",
"pageName": "Test Page"
}


Step 4:- Post to Facebook Pages (Postman – JWT Required)

API is called from Postman

JWT token is mandatory

Request body must include:

     pageId

     message

Post is published using Facebook Graph API

Backend returns SUCCESS response with Facebook Post ID

Endpoint: POST /posts/publish

Headers: Authorization: Bearer <JWT_TOKEN>

Request Body: {
"pageId": "<PAGE_ID>",
"message": "Hello from Flintzy Social Media App"
}

Response :    {
"status": "SUCCESS",
"facebookPostId": "<FACEBOOK_POST_ID>"
}


Cookie & JWT :-

Cookie

Stores userId

HttpOnly (Secure recommended)

Used only for browser-based OAuth flows

JWT Token:-

Used for API authorization

Mandatory for all Postman requests

Flow Summary:-
Google Login (Browser)
→ sets cookie + returns JWT

Facebook Login (Browser)
→ stores Facebook user access token

Postman (JWT)
→ link FB pages → returns pageId & pageName
→ publish post → returns SUCCESS + facebookPostId

API Flows :-
1. Google Login

Endpoint

http://localhost:8080/auth/google/login


Browser Response

{
  "status": "Google Login Successful",
  "jwt": "<JWT_TOKEN>"
}


Cookie Set

userId; HttpOnly

2.FacebookLogin

Endpoint

http://localhost:8080/auth/facebook/login

Browser Response

{
  "status": "Facebook Login Successful",
  
}

3. Link Facebook Pages

Endpoint

POST /facebook/link


Headers

Authorization: Bearer <JWT_TOKEN>


Response

[
  {
    "pageId": "<PAGE_ID>",
    "pageName": "Test Page"
  }
]


4. Publish Post to Facebook Page

Endpoint

POST /posts/publish


Headers

Authorization: Bearer <JWT_TOKEN>


Request Body

{
  "pageId": "<PAGE_ID>",
  "message": "Hello from Flintzy Social Media App"
}


Response

{
  "status": "SUCCESS",
  "facebookPostId": "<FACEBOOK_POST_ID>"
}


Backend Logic :-

JWT token is validated

User is identified

Page Access Token is fetched from database

Post is published using Facebook Graph API

Facebook Post ID is returned on success

Error Handling :-

Global exception handling using @ControllerAdvice

Custom Exceptions

UnauthorizedException

ResourceNotFoundException

InvalidJwtTokenException

FacebookPageNotLinkedException

HttpClientErrorException


Error Response Example
{
   "message": "JWT token is invalid or expired",
   "status": 401,
   "timestamp": "2026-01-03T11:15:45"
}

Database Setup :-
Create Database
CREATE DATABASE socialmedia_db;

Import Dump
mysql -u root -p socialmedia_db < db/socialmedia_db.sql

application.properties :-

spring.datasource.url=jdbc:mysql://localhost:3306/socialmedia_db
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

## JWT Configuration
jwt.secret=YOUR_SECRET_KEY
jwt.expiration=3600000

## Facebook Graph API
facebook.graph.api.base-url=https://graph.facebook.com

## Facebook OAuth Settings
facebook.app.id=<FACEBOOK_DEVELOPER_APP_ID>
facebook.app.secret=<FACEBOOK_DEVELOPER_APP_SECRET>
facebook.redirect.uri=http://localhost:8080/auth/facebook/callback


# Google OAuth Settings
google.client.id=<GOOGLE_CLIENT_ID>
google.client.secret=<GOOGLE_CLIENT_SECRET>
google.redirect.uri=http://localhost:8080/auth/google/callback
google.auth-uri=https://accounts.google.com/o/oauth2/v2/auth
google.token-uri=https://oauth2.googleapis.com/token
google.userinfo-uri=https://www.googleapis.com/oauth2/v2/userinfo

Running the Application
git clone <repo-url>
cd SocialmediaManagementTool
mvn clean install
mvn spring-boot:run


Application runs at:

http://localhost:8080

Notes :-

Google login is mandatory before Facebook login

Google login endpoint: http://localhost:8080/auth/google/login

Facebook login endpoint: http://localhost:8080/auth/facebook/login

Facebook OAuth must be browser-based

Linking Facebook Pages is done via Postman using JWT

Posting to Facebook Pages requires JWT + pageId + message

Cookie expiry enforces secure flow

Cookies are not required in Postman

JWT is mandatory for all secured APIs

facebookPostId confirms successful posting


Author:-
Shashank B K
Backend Developer – Java | Spring Boot | OAuth | JWT