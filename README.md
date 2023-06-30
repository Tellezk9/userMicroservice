<br />
<div align="center">
  <h2>User Microservice</h2>
  <p align="center">
   The backend of this system handles the management of user operations, such as user creation, updating, and deletion, based on roles, while also providing authTokens for authentication. 
  </p>
</div>

### Built With

* ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
* ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
* ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
* ![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)


<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these steps.

### Prerequisites

* JDK 17 [https://jdk.java.net/java-se-ri/17](https://jdk.java.net/java-se-ri/17)
* Gradle [https://gradle.org/install/](https://gradle.org/install/)
* MySQL [https://dev.mysql.com/downloads/installer/](https://dev.mysql.com/downloads/installer/)

### Recommended Tools
* IntelliJ Community [https://www.jetbrains.com/idea/download/](https://www.jetbrains.com/idea/download/)
* Postman [https://www.postman.com/downloads/](https://www.postman.com/downloads/)

### Installation

1. Clone the repository
2. Change directory
   ```sh
   cd power-up-v4-user-v1
   ```
3. Create a new database in MySQL called session_1
4. Update the database connection settings
   ```yml
   # src/main/resources/application-dev.yml
   spring:
      datasource:
          url: jdbc:mysql://localhost:<your-MySQL-port>/user
          username: root
          password: <your-password>
   ```
5. add the restaurant microservice connection settings
   ```yml
   # src/main/resources/application-dev.yml
   app:
      urls:
          urlToRestaurantMicroService: "http://<your-restaurantMicroservice>:<your-port>/"
   ```
6. After the tables are created execute src/main/resources/data.sql content to populate the database
7. Open Swagger UI and search the /auth/login endpoint and login with userDni: 123, password: 1234

<!-- USAGE -->
## Usage

1. Right-click the class PowerUpApplication and choose Run
2. Open [http://localhost:8090/swagger-ui/index.html](http://localhost:8090/swagger-ui/index.html) in your web browser

<!-- ROADMAP -->
## Tests

- Right-click the test folder and choose Run tests with coverage
