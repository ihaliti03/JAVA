# JobFinderApi
#JobFinder Api Java A JobFinder RESTAPI with spring boot, JAVA framework.

1.This is a microservice for creating, biding,finding, updating and deleting , also manipulating with users nad creators that are responsible for mentioned functions.

2.Project is consisted about five entities: address,bid,user,work and worker, and they have a relationship between them; the user or the creator can manipulate with the works.

    Clone the project using this command: git clone https://github.com/Bani003/JobFinderApi.git

    Create a database for the microservice

    Import the sql file at your database.

    Configure the application.properties file with your mysql credentials:

spring.jpa.hibernate.ddl-auto=update

spring.datasource.url=jdbc:mysql://localhost:3306/JobFinderJava

spring.datasource.username=root

spring.datasource.driver-class-name =com.mysql.jdbc.Driver

You are all set and can run the JobFinder file and can use the application and see the result on http://localhost:8080/

Usage For better user experience, we prefer to use Postman, to better see the result and make changes. As we defined earlier, our project support crud services. We will define endpoints briefly.

User endpoints:

    URL: http://127.0.01:8080/user Create a user - METHOD = POST
    URL: http://127.0.01:8080/user/{id} Get a user - METHOD = GET
    URL: http://127.0.01:8080/users Get all users - METHOD = GET
    URL: http://127.0.01:8080/user/{id} Update a user - METHOD = PUT
    URL: http://127.0.01:8080/user/{id} Delete a user - METHOD = DELETE

Body example for user POST: { "username":"Test", "address":{ "city":"test", "country":"test" } }

Body example for user GET: { "id":1 "username":"Test", "address":{ "id":1 "city":"test", "country":"test" } "works":[] }

Worker endpoints:

    URL: http://127.0.01:8080/worker Create a Worker - METHOD = POST
    URL: http://127.0.01:8080/worker/{id} Get a Worker - METHOD = GET
    URL: http://127.0.01:8080/workers Get all Workers - METHOD = GET
    URL: http://127.0.01:8080/worker/{id} Update a Worker - METHOD = PUT
    URL: http://127.0.01:8080/worker/{id} Delete a Worker - METHOD = DELETE

Body example for worker POST: { "username":"Test", "address":{ "city":"test", "country":"test" } }

Body example for worker GET: { "id":1 "username":"Test", "address":{ "id":1 "city":"test", "country":"test" } "works":[] }

Work endpoints:

    URL: http://127.0.01:8080/work/{id} Create a Work - METHOD = POST
    URL: http://127.0.01:8080/work/user/{user_id} Get Work by UserId - METHOD = GET
    URL: http://127.0.01:8080/work/user/{work_id} Get Work by WorkId - METHOD = GET
    URL: http://127.0.01:8080/works Get all Works - METHOD = GET
    URL: http://127.0.01:8080/work/{work_id} Update a Work by WorkId - METHOD = PUT
    URL: http://127.0.01:8080/work/{work_id} Delete a Work by WrokId - METHOD = DELETE

Body example for work GET:

{ "id": 84, "title": "do dishes", "description": "shka me fol beweeeee", "is_available": true, "address": { "id": 1, "city": "test", "country": "test" }, "bid": [ { "id": 1, "price": 847.0, "created_at": null } { "id": 1, "price": 847.0, "created_at": null } ] },

Response - List of Work objects Bid endpoints:

    URL: http://127.0.01:8080/bid/{work_id}/{worker_id} Create a Bid by workId and workerId - METHOD = POST

