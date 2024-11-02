# Diksha Technical Assessment

## Overview
This project is a mobile application developed for the Diksha Technical Assessment. It features user authentication and a shopping cart system using the Fake Store API for product data and local storage with Room for cart management and stash (Sharedprefarance) for user login data.

## API
The application retrieves user and product data from the [Fake Store API](https://fakestoreapi.com/docs).

### User Login
- **Endpoint**: `https://fakestoreapi.com/users`
- **Method**: `GET`
- **Description**: This endpoint returns a list of users, including their personal details such as email, username, and password.

#### Sample User Data
The user data returned by the API contains the following fields:
```json
{
  "address": {
    "geolocation": {
      "lat": "-37.3159",
      "long": "81.1496"
    },
    "city": "kilcoole",
    "street": "new road",
    "number": 7682,
    "zipcode": "12926-3874"
  },
  "id": 1,
  "email": "john@gmail.com",
  "username": "johnd",
  "password": "m38rmF$",
  "name": {
    "firstname": "john",
    "lastname": "doe"
  },
  "phone": "1-570-236-7033",
  "__v": 0
}
