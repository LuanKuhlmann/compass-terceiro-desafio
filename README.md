# compass third challenge
CompassFlix is a movie rental themed RESTful API for the UOL Compass Challenge 2. Our API can handle data operations using HTTP verbs (GET, POST, PUT, DELETE). In the following steps, you will see how easy it is to register, consult, update, and delete a movie!

### 🛠️ Technologies
The following technologies were used in this project:

* [Java 17](https://www.oracle.com/br/java/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [h2database](https://www.h2database.com/html/main.html)
* [Git](https://git-scm.com/)
* [ActiveMQ Artemis](https://activemq.apache.org/components/artemis/)


## 🚀 Starting
To test this project on your computer, you should have the following tools installed:
* Git Bash, to clone this project
* An IDE to run the Spring Java code (like IntelliJ or Eclipse)
* A software testing tool (like Postman or Insomnia)

Next, you can clone this repository with this command:

```bash
git clone https://github.com/LuanKuhlmann/compass-terceiro-desafio.git
```

Now, you can open the directory in your IDE and navigate to the main class called 'CompassUolDesafio3Application' and press the 'Run' icon to execute the code! Next, you can open your preferred API testing tool and try the HTTP operations.

(Note: the main class is placed in "src/main/java/io/github/luankuhlmann/compassUoldesafio3/CompassUolDesafio3Application.java")

### ✅ Testing HTTP: Step by step ###
These are the commands you can try in your browser or testing software. We recommend using Postman and following the steps in order.

* ▶️ GET

  With this first command, you can see all the registered movies. Don't worry, we have already prepared the database with them for you.
```bash
http://localhost:8080/posts
```
(Note: if you can't see any post, you can proceed to the POST step to add a new one)

* ▶️ POST

  Now, you will register a new POST. You have to use the following URL:
```bash
http://localhost:8080/posts/{id} where {id} should be replaced by a number between 1 and 100
```

* ▶️ PUT

  You have to put the ID of the post you want to update:
```bash
http://localhost:8080/posts/{id}
```

* ▶️ DELETE

  To disable a post, use the same logic as before; just copy and paste the ID of the one you want to disable:
```bash
http://localhost:8080/posts/{id}
```

### 💡 Methodologies

In regards of organization:
* This project was planned with Agile Scrum Methodology
* The Trello website was used to monitor and simplify tasks to be done with the Kanban methodology


### 📄 Documentation
The documentation was automatically generated using Swagger. To know more about the POSTS API, you can access it by copying the following URL in your browser:
```bash
http://localhost:8080/swagger-ui.html
```

(Note: You can only access it when the project is running)

### ‎😃 Creators
You can see more about me in my profile:
* [Luan](https://github.com/LuanKuhlmann)
