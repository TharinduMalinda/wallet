# WALLET MICROSERVICE

Wallet microservice is a spring boot application running on the JVM that manages credit/debit transactions of players.

## Scope

Wallet microservice expose set of rest endpoints that fulfill the requirements detailed below:

- Ability to create|update players|accounts.
- Ability to perform transactions (Credit|Debit) .
- Ability to check account balance.
- Ability to view list of transactions perform by players(support pagination)
  Current balance per player

Debit /withdraw per player: A debit transaction will only succeed if there are sufficient funds on the account (balance - debit amount >= 0). The caller will supply a transaction number that must be unique for all transactions. If the transaction number is not unique, the operation must fail.

Credit /deposit per player. The caller will supply a transaction number that must be unique for all transactions. If the transaction number is not unique, the operation must fail.

Transaction history per player sorted by creation date and support pagination.

## Build and Run
#### Prerequisite
1. install `Java 8` and `Maven 3` .
2. Change directory to the root folder of the application.
3. Run the below maven command to build the JAR file.

```bash
mvn clean package
```

4. Run the JAR file

```bash
java -jar target/wallet-0.0.1.jar
```

5. Swagger UI.

```bash
http://localhost:8080/swagger-ui/index.html#/
```

6.dev profile set as the default profile, which is JWT authorization disable profile.

```bash
java -jar target/wallet-0.0.1.jar
```
7.Run application with JWT authorization, change profile to `uat`.

```bash
java -jar -Dspring.profiles.active=uat  target/wallet-0.0.1.jar
```
**NOTE**

_if JWT enabled, first need to get jwt token by calling `/player/auth` . 
place that token inside the header for all other request `key - Authorization`
and `value - Bearer <token>`_
## Endpoints
1. Create player `  /player - POST`
2. Update player `  /player/{playerId} - PATCH`
3. Get account balance `/transaction/balance/{accountId} -GET`
4. Perform transactions `(/transaction/{accountId}) - POST`
5. Fetch transactions `(/transaction/{accountId}/{pageNo}/{pageSize}) - GET`


**Here you can find the sample requests and responses for all endpoints.**

**[Postman project](docs/postman)**


## Technologies

1. Java 8
2. Maven 3
3. Spring boot with JPA and Hibernate.
4. H2 database
5. Junit 4
6. Swagger UI
7. SonarLint

##Assumptions
** For completion of the project there are few `player` related endpoints. Those endpoints should separate from the wallet microservice**
1. There is only one account(wallet) for each player.
2. Only active players can perform transactions
3. Player status will directly effect to account status.Once player status getting update,account status automatically update(because there is one to one mapping)
4. Cash balance stored in the account table.
5. At the end of each and every successful transaction, cash balance will update
6. Each transaction has a unique transaction ID.
## Design
1. `Spring ControllerAdvice` is used for handle  exception  globally
2. `Spring AOP` is used for centralized logging operations
3. Transfer Object Pattern `(DTO)` is used to return data from endpoints.
4. Data Access Object `(DAO)` pattern is used to isolate the business layer from the persistence layer.
5. Optimistic Locking with JPA and Hibernate is used to solve the Concurrency issue while updating the player balance.
6. Endpoints are tested using `Swagger UI` and Postman also with `Junit` testing with `MockMvc`.
7. used `JWT` tokens to secure the API
8. transaction table doesn't include any mapping to other tables.But there is a account id field, which is validate before insert records

## Changes needed for the API be production-ready
1. player endpoint should decouple from the wallet microservice.
2. database should be persistence relational database with acid properties
3. Based on the transaction volume should use load balancer and scale database horizontally
4. Can use SSL and secure the connections via HTTPS

 