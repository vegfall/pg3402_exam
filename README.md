## ToDo

- [x] Add a simple frontend (React Hello World)
- [x] Add a simple backend (Spring boot API get string call)
- [x] Create simple question dto and print in frontend
- [x] Add a backend service to handle multiple questions
- [x] Implement method for frontend to loop through questions
- [x] Add actuator and consul to current services
- [x] Add gateway to backend
- [x] Add a way to create a new quiz session
- [ ] Add oauth to authenticate user
- [ ] Check backend received a unique username for user
- [ ] Store quiz session in a database
- [ ] Add a backend service to check and provide results
- [ ] Add AI service with asynchronous communication
- [ ] Setup API call message to openai to get desired data
- [ ] ...

## Important Implementations

- [x] Frontend
- [x] Gateway
- [x] Synchronous communication
- [ ] Asynchronous communication (RabbitMQ)
- [x] Healthcheck
  - [x] Actuator
  - [x] Consul
- [ ] Database (MySQL?)
- [ ] Docker
- [ ] Testing?
- [ ] Exception Handling

## Backend services to add

- [x] QuizService
- [x] QuestionService
- [ ] ResultService
- [ ] AIService
- [x] UserService

## Important

- [ ] Make sure solution is stateless

## Ports

- Frontend: 3000
- Gateway: 8000
- Consul: 8500

#### Temp

- QuizService: 8080
- QuestionService: 8081
- ResultService: 8082
- UserService: 8083
- AIService: 8084
