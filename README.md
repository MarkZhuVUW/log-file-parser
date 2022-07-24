# log-file-parser-service
This is a dockerized Spring Boot microservice with a GET api to get the log file analytics.

## assumptions
1. ip addresses and urls appear once per line in the log file.
2. each log entry in the log file is line separated.
3. ip addresses and urls can appear anywhere in the log file.
### unique ip addreses
1. use long to accommodate unique ip occurrences
### top 3 most active ip addresses
1. if the number of occurrences of ip addresses exceed `Long.MAX_VALUE`, order them by the lexicographical ordering of the ip address string.
### top 3 most visited urls
1. if the number of occurrences of urls exceed `Long.MAX_VALUE`, order them by the lexicographical ordering of the url string.
2. http method type in the url string is separated by a single space `GET /blabla`
### build requirements
1. JDK 17
2. Maven
3. Docker
4. docker-compose

## How to build and run the program
1. Run `./local-development/start.sh` to build the service according to `./api.json` and run it locally in a fairly simple docker container. 
   Dockerfile and docker-compose.xml can be found under `./local-development` folder
2. By running a test case within the `LogParsingControllerIT.java` we are running the simulation api with different input params.
3. To test the API and see API documentation, import the `api.json` under the root folder into postman.
   https://learning.postman.com/docs/designing-and-developing-your-api/importing-an-api/
   ![img.png](img.png)
4. Remote JVM debug port expose to `8000`
5. Unit tests post-fixed with `*Test.java`. Component tests post-fixed with `*IT.java` 
7. Note that logging is disabled for performance for tests.
8. Some test log files are stored under the `local-development/files` folder
