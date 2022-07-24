# log-file-parser-service
This is a dockerized Spring Boot microservice with a GET api to get the log file analytics.

## assumptions
1. 

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
   
4. Remote JVM debug port expose to `8000`
5. Unit tests post-fixed with `*Test.java`. Integration tests post-fixed with `*IT.java` 
6. I have load tests under `SimulationApiLoadTest.java` testing whether the above assumptions can be met without out of memory error.
7. Note that logging is disabled for performance for tests. You can spin up the docker container to test the logs.
