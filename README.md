# compass third challenge
This BLOG API is an API designed around a blog theme, which processes new post data from an external resource and tracks the Progress Status for the UOL Compass Challenge 3.

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

### ✅ Testing HTTP: Step by step ###
These are the commands you can try in your browser or testing software. We recommend using Postman and following the steps in order.

* ▶️ POST -> To process a new post, use an ID between 1 and 100 in place of {postId}:
* 
```bash
[http://localhost:8080/posts](http://localhost:8080/posts/{postId})
```

This will set the Progress Status to ENABLE if successful or FAILED if there's an issue.

* ▶️ GET ALL -> Retrieve all registered posts, associated comments, and their Progress Status History using:

```bash
http://localhost:8080/posts
```

* ▶️ PUT - > Reprocess a post using:
* 
```bash
http://localhost:8080/posts/{postId}
```

* ▶️ DELETE -> Delete a post using the same command as before. The post's status will be set to DISABLE.

  To disable a post, use the same logic as before; just copy and paste the ID of the one you want to disable:
```bash
http://localhost:8080/posts/{postId}
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
