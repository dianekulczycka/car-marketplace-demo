!!! Run Liquibase migrations before running the app: mvn liquibase:update
Liquibase may throw errors beacuse of this.
Quick fix - drop DATABASECHANGELOG and DATABASECHANGELOGLOCK tables and rerun the app

Postman collections are located in POSTMAN COLLECTIONS dir in root.

Prerequisites: Docker, Maven, Jvaa 17 installed

Running process: run DB, Kafka, Zookeeper using docker-compose file in root dir.
Then run every of services present with src.main.java.org.example.${servicename}.${servicename}Application.java file

Car Marketplace Demo allows users to:
  list cars for sale,
  manage roles (SELLER, MANAGER, ADMIN),
  view statistics,
  perfomr currency conversions.

The microservices communicate using Kafka and maintain separate responsibilities.
    UserAuth Service (port 8085) - Manages user authentication and JWT token issuance.
    Post Listing Service (port 8082) - Manages the listing of cars for sale.
    Role Management Service (port 8083) - Manages user roles and permissions.
    Statistics Service (port 8084) - Tracks and provides statistics on posts.
    Currency Conversion Service (port 8080) - Converts post prices between different currencies (USD, EUR, UAH).
    Notification Service (port 8081) - Sends email notifications based on role-based conditions.

Microservices
1. UserAuth Service
Responsible for user authentication, token issuance, and managing user roles.
Manages USERS table

2. Post Listing Service
Handles the listing of cars, including creating, viewing, and updating posts.
Manages POSTS table

3. Role Management Service
Manages user roles and communicates changes to the UserAuth service through Kafka.

4. Statistics Service
Tracks statistics such as how many times a post was viewed, and filters the data based on the user's role.
Manages POSTS_STATS table

5. Currency Conversion Service
Fetches real-time exchange rates from external sources and converts post prices to the desired currency (UAH).

6. Notification Service
Sends email notifications to users with a Manager role based on predefined conditions.
Manages MANAGERS table


