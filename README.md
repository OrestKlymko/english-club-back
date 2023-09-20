
## Registration user
```http
  POST /api/v1/user/registration
```

| Request Parameter | Location     | Type     | Description                       |
| :-------- | :------- | :------- | :-------------------------------- |
| `username`      | `body` | String | **Required**. Username of customer |
| `password`      | `body` | String | **Required**. Password of customer. Should be min 8, max 20 symblos. Sholud contains big and small character and digits|
| `themes`      | `body` | String | **Required**. Only themes from list |
| `country`      | `body` | String | **Required**. Valid country |
| `levelOfEnglish`      | `body` | String | **Required**.  Valid only this values: Beginner, Intermediate, Upper-Intermediate, Advanced, Mastery |
| `email`      | `body` | String | **Required**. Should be on pattern example@mail.com|

Example of body request:
```http
{
    "email": "exam3ple@email.com",
    "username": "example_username1233",
    "passwords": "Password2000",
    "themes": "IT",
    "country": "Ukraine",
    "levelOfEnglish": "Beginner"
}

```


Example of success changed:
```http
{
    "status": 201,
    "body": "User registration"
}
```


## Login user
```http
  POST /api/v1/user/login-user
```

| Request Parameter | Location     | Type     | Description                       |
| :-------- | :------- | :------- | :-------------------------------- |
| `email`      | `body` | String | **Required**. Email of customer |
| `password`      | `body` | String | **Required**. Password of customer |

Example of body request:
```http
{
    "email":"user1@gmail.com",
    "password":"password1"
}
```


Example of success changed:
```http
{
    "status": 202,
    "username": "user1",
    "body": "Login successes",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiZXhwIjoxNjk1MTkxMjY2LCJpYXQiOjE2OTUxODk0NjYsInVzZXJuYW1lIjoidXNlcjEifQ.CYhFnf136ccTJgfDic4pNeCtiJTDZA8eOEaDaS8dzRk"
}
```

## Delete user
```http
  POST /api/v1/user/delete
```

| Request Parameter | Location     | Type     | Description                       |
| :-------- | :------- | :------- | :-------------------------------- |
| `Authorization`      | `Header` | String | **Required**. Token of user, what should be placed in token. Token you get, when user entry in system |



Example of success changed:
```http
{
    "status": 201,
    "body": "User deleted",
```

## Get all users

```http
  GET /api/v1/user/all
```

Example of output:
```http
[
    {
        "id": 1,
        "email": "user1@example.com",
        "username": "user1",
        "levelOfEnglish": "Intermediate",
        "country": "USA",
        "themesType": "IT",
        "existClubs": [],
        "creatingClubsByUser": []
    },
    {
        "id": 2,
        "email": "user2@example.com",
        "username": "user2",
        "levelOfEnglish": "Advanced",
        "country": "Canada",
        "themesType": "Marketing",
        "existClubs": [],
        "creatingClubsByUser": []
    },
```

## Get info about login user

```http
  GET /api/v1/user/get-info
```

| Request Parameter | Location     | Type     | Description                       |
| :-------- | :------- | :------- | :-------------------------------- |
| `Authorization`      | `Header` | String | **Required**. Token of user, what should be placed in token. Token you get, when user entry in system |


Example of output:
```http
  {
    "id": 1,
    "email": "user1@example.com",
    "username": "user1",
    "themes": "IT",
    "country": "USA",
    "levelOfEnglish": "Intermediate",
    "creationClubs": [],
    "existClubs": [],
    "role": "ROLE_ADMIN"
}
```

## Join user's to club
```http
  POST /api/v1/user/join/{club_id}
```

| Request Parameter | Location     | Type     | Description                       |
| :-------- | :------- | :------- | :-------------------------------- |
| `Authorization`      | `Header` | String | **Required**. Token of user, what should be placed in token. Token you get, when user entry in system |
| `club_id`      | `Parameter in path` | Integer | **Required**. Placed id of club, where user want to join|


Example of Success joined:
```http
  {
    "code": 202,
    "body": "success joined"
}
```

## Change level of English of user;
```http
  POST /api/v1/user/change/english-level/{newLevelLanguage}
```

| Request Parameter | Location     | Type     | Description                       |
| :-------- | :------- | :------- | :-------------------------------- |
| `Authorization`      | `Header` | String | **Required**. Token of user, what should be placed in token. Token you get, when user entry in system |
| `newLevelLanguage`      | `Parameter in path` | Integer | **Required**. Placed a new level of English. **Valid only this values:** Beginner, Intermediate, Upper-Intermediate, Advanced, Mastery|


Example of success changed:
```http
  {
    "code": 202,
    "body": "Language changed"
}
```


## Change user's password;
```http
  POST /api/v1/user/change/password
```

| Request Parameter | Location     | Type     | Description                       |
| :-------- | :------- | :------- | :-------------------------------- |
| `Authorization`      | `Header` | String | **Required**. Token of user, what should be placed in token. Token you get, when user entry in system |

Example of body request:
```http
  {
    "email": test@gmail.com,
    "oldPassword": "password13"
    "newPassword": "password35"
}
```


Example of success changed:
```http
  {
    "code": 202,
    "body": "Password changed"
}
```







