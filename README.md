## **Description:**

Project "bank-system-api" was created to solve the problem of the hard system of transfer money.
Now users can simply make deposit, withdraw and transfer money with entered the email, the bank card and founds only.
They get understandable response how much money they invested, withdrew ,sent.

## **Instruction how to run a project:**
Pull the repository using git command: 
`git pull https://github.com/AndreyPolezhaiev/bank-system-api.git`

Set your database properties in `src/main/resources/application.properties`,
you need to change the properties only like:
```
    spring.datasource.url=jdbc:mysql://localhost:your_port/bank_system?createDatabaseIfNotExist=true
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
    
    spring.jpa.hibernate.ddl-auto=create-drop
    spring.jpa.show-sql=true
```
And then run the project!

## **Technologies:**

Spring Boot, Spring Data JPA, Spring Web, Hibernate, Java Core, OOP, Junit, Maven, Git and SQL.

## **Controllers:**

There are controllers, that allow to make deposit, withdraw, send money.
Also, there are others for getting all users, get their detailed info by phone number and register a user.

## **For people:**

People using GitHub can pull my project and make some corrections, improve the project, or use it for their goals.

## **Problems:**

Making a "Car-Sharing-App" I faced to make correct registration.
I spent more time to understand what is the problem, and this was that I tried
to change immutable object, I fixed it and all got worked.
Also, I knew how make local tests without Docker.

## **Postman queries:**

##### There are basic Postman queries that are explained below.

### **Basic Postman queries for users:**

#### GET:

`http://localhost:8080/api/users` - Get all users

`http://localhost:8080/api/users/` - Get user's detailed info by a phone number
```json
      {
          "phoneNumber": "+38050000458"
      }
```

#### POST:

`http://localhost:8080/api/users/register` - Register a user
```json
      {
          "firstName": "Bob",
          "lastName": "Alison",
          "phoneNumber": "+38050000449",
          "email": "alice@gmail.com",
          "password": "1234",
          "bankCard": "1111111111116789",
          "initialBalance": 1011
      }
```


### **Basic Postman queries for transactions:**

#### POST:

`http://localhost:8080/api/transactions/deposit` - Make deposit
```json
      {
          "email": "bob@gmail.com",
          "bankCard": "1111111111116781",
          "depositFounds": 150
      }
```

`http://localhost:8080/api/transactions/withdraw` - Withdraw founds
```json
      {
          "email": "bob@gmail.com",
          "bankCard": "1111111111116781",
          "withdrawFounds": 200
      } 
```

`http://localhost:8080/api/transactions/transfer` - Transfer founds
```json
      {
          "transferFounds": 100,
          "emailSend": "alice@gmail.com",
          "bankCardSend": "1111111111116789",
          "emailGet": "bob@gmail.com",
          "bankCardGet": "1111111111116781"
      }
```

