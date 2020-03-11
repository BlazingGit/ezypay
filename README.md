# EzyPay Subscription Module
This project consists of Spring Boot API backend and Angular 8 frontend.

To run the project, please navigate to the project folder and run 'gradlew bootRun' at CMD. You can view the frontend at 'http://localhost:8049' after gradle run as I have already included the build file of Angular frontend in the source code. You should be able to run the backend even without gradle installed.

The Angular source code is located at 'src/main/webapp/frontend/' of the project. To start the frontend separately, run 'npm install' and then 'ng serve' for at the frontend folder. NodeJS and Angular is needed to be installed first.

I also added 2 JUnit test case at SubscriptionControllerTest.java at 'src/test/java/com/ezypay/controller/test' folder. The first case follows the example scenario of the questions.

For backend API test. You can make a POST request to this URL "http://localhost:8049/subscription/add" with body:
{
	"customerName": "Wei Yang",
	"type": "WEEKLY",
	"startDate": "02/03/2020",
	"endDate": "05/04/2020",
	"amount": 100.50
}
 
