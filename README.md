## How to run

Make sure to install HashiCorp Consul, MySQL Server, Java 21

Change .env password to your MySQL password for Question, Quiz and Result

Start by running Consul with:
consul agent -dev -node quiz

Then for each service (Gateway, Question, Quiz, Result, AI) navigate to their respective folder and run the two commands:
mvn clean install
mvn spring-boot:run

Lastly for frontend navigate to /app and run the commands:
npm install
npm run dev
o

If running through postman all calls mut be the ones available in Quiz controller. However the calls need to go through
the gateway at http://localhost:8000/quiz/

## Ports

- Frontend: 3000
- Gateway: 8000
- Consul: 8500
