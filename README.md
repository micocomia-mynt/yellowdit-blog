# Yellowdit

Yellowdit is a blogging application that only contains great stuff that matters.

## API

Enables client application to manage users, contents, and reviews.

***
### User Management (/user)
CRUD for users. [Sample collections][postman] of API call that can be imported in postman for testing.

#### Register a user
Register a user. All fields are required.
```json
POST /
Header - Reference-Number: 123
{
    "email": "jdoe@apper.ph", //string|email
    "first_name": "John",
    "last_name": "Doe",
    "password": "$trong",
    "birth_date": "2000-12-13" //string|ISO-DATE-FORMAT
}
```
Response/s
```json
201 CREATED
{
    "verification_code": "uPHo9e"
}
```
```json
400 BAD_REQUEST
{
    "message": "email already registered"
}
```

#### Verify a user
Verify a registered user. All fields are required.
```json
POST /verify
Header - Reference-Number: 123
{
    "email": "jdoe@apper.ph",
    "verification_code": "uPHo9e"
}
```
Response/s
```json
200 OK
{
    "message": "verification success"
}
```
```json
400 BAD_REQUEST
{
    "message": "email already registered"
}
```
```json
400 BAD_REQUEST
{
    "message": "Invalid verification request"
}
```

#### Login
Authenticate an active user. All fields are required.
```json
POST /login
Header - Reference-Number: 123
{
    "email": "jdoe@apper.ph",
    "password": "$trong"
}
```
Response/s
```json
200 OK
{
    "id": "97e8e651-1363-41fe-8cb0-13720c144291",
    "email": "jdoe@apper.ph",
    "first_name": "John",
    "last_name": "Doe",
    "birth_date": "2000-12-13",
    "date_registered": "2021-02-14T14:21:35.387465",
    "date_verified": "2021-02-14T14:21:56.37356",
    "last_login": "2021-02-14T14:22:04.808352",
    "is_verified": true,
    "is_active": true
}
```
```json
400 BAD_REQUEST
{
    "message": "Invalid login credentials"
}
```
```json
400 BAD_REQUEST
{
    "message": "password: password is required"
}
```
#### Get all users
Retrieve all verified and active users
```json
GET /
```
Response/s
```json
200 OK (zero result)
[]
```
```json
200 OK
[
    {
        "id": "25f59db2-b2ff-4b33-be97-10c31217ae11",
        "email": "jdoe@apper.ph",
        "first_name": "John",
        "last_name": "Doe",
        "birth_date": "2000-12-13",
        "date_registered": "2021-02-14T15:44:44.175223",
        "date_verified": "2021-02-14T15:44:51.785636",
        "last_login": null,
        "is_verified": true,
        "is_active": true
    },
    {
        "id": "b6893356-6510-4067-a2f5-e4a50dd98e28",
        "email": "mreyes@apper.ph",
        "first_name": "Maria",
        "last_name": "Reyes",
        "birth_date": "2000-12-13",
        "date_registered": "2021-02-14T15:45:32.323657",
        "date_verified": "2021-02-14T15:45:45.240627",
        "last_login": null,
        "is_verified": true,
        "is_active": true
    }
]
```
#### Get a user
All fields are required.
```json
GET /{user_id}
```
Response/s
```json
200 OK
{
    "id": "25f59db2-b2ff-4b33-be97-10c31217ae11",
    "email": "jdoe@apper.ph",
    "first_name": "John",
    "last_name": "Doe",
    "birth_date": "2000-12-13",
    "date_registered": "2021-02-14T15:44:44.175223",
    "date_verified": "2021-02-14T15:44:51.785636",
    "last_login": "2021-02-14T15:51:05.584079",
    "is_verified": true,
    "is_active": true
}
```
```json
404 NOT FOUND (No body)
```

#### Delete a user
Soft deletes a user. Deactivates a user.
```json
DELETE /{user_id}
Header - Reference-Number: 123
```
Response/s
```json
200 OK
{
    "message": "delete user success"
}
```
```json
400 BAD_REQUEST
{
    "message": "email already registered"
}
```
```json
404 NOT FOUND (No body)
```

#### Update user details
Verify a registered user. All fields are required.
```json
PATCH /{user_id}
Header - Reference-Number: 123
{
    "first_name": "Eren",
    "last_name": "Yeager",
    "birth_date": "1999-12-12".
    "password": "$tronger",
    "is_active": false
}
```
Response/s
```json
200 OK
{
    "message": "update success"
}
```
```json
400 BAD_REQUEST
{
    "message": "age must be at least 18"
}
```

[postman]: <https://www.getpostman.com/collections/4c165e4e7076eb63a132>

***
### Blog Management (/blog)
CRUD for blog.

#### Create a blog
Create a blog. All fields are required.
```json
POST /
Header - Reference-Number: 123
{
    "title": "Sample Blog",
    "content": "Sample Content",
    "user_id": "123",
}
```
Response/s
```json
201 CREATED
{
  "message": "Blog created successfully"
}
```
```json
400 BAD_REQUEST
{
    "message": "User is not verified and active"
}
```
#### Comment
All parameters are required
```json
POST /{blog_id}/comment
Header - Reference-Number: 123
{
    "name": "John Doe",
    "comment": "Sample comment"
}
```
Response/s
```json
200 OK
{
  "message": "Comment added successfully"
}
```
```json
400 BAD REQUEST
{
  "message": "User not found"
}
```
#### Get a blog
All fields are required.
```json
GET /{blog_id}
```
Response/s
```json
200 OK
{
  "id": "fe8c3a3c-174e-416e-bb18-a59c8a88103c",
  "title": "Updated Title",
  "content": "Update content",
  "comments": [
    {
      "id": 1,
      "name": "Mico",
      "comment": "Comment 1"
    },
    {
      "id": 2,
      "name": "John",
      "comment": "Comment 2"
    },
    {
      "id": 3,
      "name": "Doe",
      "comment": "Comment 3"
    }
  ],
  "user_id": "123",
  "date_publish": "2021-02-15T18:39:49.909797",
  "last_updated": "2021-02-15T18:48:33.307873",
  "is_visible": true
},
```
```json
400 BAD REQUEST
{
  "message": "Blog does not exist"
}
```
#### Update blog details
Update the title and content of a blog
```json
PATCH /{blog_id}
Header - Reference-Number: 123
{
    "title": "New Title",
    "content": "New Content",
}
```
Response/s
```json
200 OK
{
    "message": "update success"
}
```
```json
400 BAD REQUEST
{
    "message": "Blog does not exist"
}
```
#### Delete a blog
Soft deletes a blog.
```json
DELETE /{blog_id}
Header - Reference-Number: 123
```
Response/s
```json
200 OK
{
    "message": "delete blog success"
}
```
```json
400 BAD REQUEST
{
  "message": "Blog does not exist"
}
```
#### Get all blogs
With parameter "all"
```json
GET ?all=false
```
Response/s
```json
200 OK
{
  "id": "fe8c3a3c-174e-416e-bb18-a59c8a88103c",
  "title": "Updated Title",
  "content": "Update content",
  "comments": [
    {
      "id": 1,
      "name": "Mico",
      "comment": "Comment 1"
    },
    {
      "id": 2,
      "name": "John",
      "comment": "Comment 2"
    },
    {
      "id": 3,
      "name": "Doe",
      "comment": "Comment 3"
    }
  ],
  "user_id": "123",
  "date_publish": "2021-02-15T18:39:49.909797",
  "last_updated": "2021-02-15T18:48:33.307873",
  "is_visible": true
}
```
Request
```json
GET ?all=true
```
Response/s
```json
200 OK
{
  "id": "fe8c3a3c-174e-416e-bb18-a59c8a88103c",
  "title": "Updated Title",
  "content": "Update content",
  "comments": [
    {
      "id": 1,
      "name": "Mico",
      "comment": "Comment 1"
    },
    {
      "id": 2,
      "name": "John",
      "comment": "Comment 2"
    },
    {
      "id": 3,
      "name": "Doe",
      "comment": "Comment 3"
    }
  ],
  "user_id": "123",
  "date_publish": "2021-02-15T18:39:49.909797",
  "last_updated": "2021-02-15T18:48:33.307873",
  "is_visible": true
},
{
    "id": "ge8c3s3c-1743-416e-bb18-a59c8a88103d",
    "title": "Sample Blog2",
    "content": "Sample Content",
    "comments": [
        {
        "id": 4,
        "name": "Mico",
        "comment": "Comment 4"
        },
        {
        "id": 5,
        "name": "John",
        "comment": "Comment 5"
        },
    ],
    "user_id": "123",
    "date_publish": "2021-02-15T18:39:49.909797",
    "last_updated": null,
    "is_visible": false
}
```
