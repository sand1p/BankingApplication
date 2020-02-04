# BankingApplication

## Description: 
Banking System to process concurrent transaction requests.
1. Accounts creation screen
2. Concurrency test screen
3. Display screen
The app should handle concurrent transactions for a particular/multiple bank accounts.

## Features: 
    1. The app should be able to create dummy accounts with balances
    2. The app should be hit concurrent requests for an account and maintain the integrity of
       transactions.
    3. The app should be able to insert transactions into DB.
    4. The app should be able to show transactions and balance of accounts.
 
    
## How to run application
### Prerequisites (platform setup): 
    1. java 8 or latest
    2. sbt 
### Steps to run application in dev mode: 
    1. Install sbt in your local environment. 
    2. Clone the repository.
    3. Enter the project directory.
    4. Execute following commands in CLI.
    sbt : to enter into sbt console. Execute following commands inside sbt console.
      - clean : to clean previous executables. 
      - compile : to compile sbt application.
      - run -Dhttp.port=PORT_NUMBER -Dconfig.file=application.conf    : to deploy application locally PORT_NUMBER : e.g. 9111
    5. Now open any REST client. And execute following APIs
### API List
####  1. POST /v1/accounts      controller.AccountController.create
       Request: 
       ```xml
            <account>
               <balance>2134.23432</balance>
               <type>Savings</type>
            </account>
       ```
      Response: 
     1.  Success
      Ok
      ```xml 
         <response>
           <message>Success</message>
         </response>
      ```
     2. Failure
     Bad Request 
     ```xml
        <response>
        <message>Failure</message>
      </response>
     ```
####  2. GET /v1/accounts       controller.AccountController.getBalance
     Response: 
     1.  Success
      Ok
      ```xml 
        <response>
            <status>Success</status>
            <account>
               <id>{id}</id>
               <balance>{balance}</balance>
            </account>
        </response>
      ```
     2. Failure
     Bad Request 
     ```xml
        <response>
            <status>Failure</status>
            <message>Incomplete input.</message>
        </response>
     ```
####  3. POST /v1/users      controller.UserController.create
       Request: 
       ```xml
            <user>
              <emailId>priyasha@gslab.com</emailId>
              <mobileNumber>2938432493</mobileNumber>
              <password>priya123</password>
              <name>priya</name>
            </user>
        ```
        Response: 
          Success:
          ```xml
            <response>
                <status>Success</status> 
                <nessage>User Created Successfully</nessage>
            </response>
          ```
         Failure: 
          ```xml
            <response>
                <status>Failure</status> 
                <message>Complete details in request</message>
            </response>
          ```
####  4. POST /v1/transactions  controller.TransactionController.create
       Request: 
       ```xml 
        <transaction>
            <senderId>a1c46a80-4591-11ea-88e0-f530e33335cb </senderId>
            <recipientId>e9ddd290-458f-11ea-b28d-47921bf4e026 </recipientId>
            <amount>900</amount>
        </transaction>
        ```
        Reponse: 
        Success:
        200 Ok
        ```xml
            <response>
              <status>Success</status> 
              <message>Transaction Successful</message>
          </response>
        ``` 
        Failure: 
        400 Bad Request.
        ```xml
             <response>
                <message>Something went wrong</message> 
                <status>Failure</status>
             </response>
         ``` 
            
### 5. GET /v1/concurrency    controller.ConcurrencyController.generate(concurrency: Int)
      Response:
      Success
      200 Ok
        ```xml
         <response>
              <status>Success</status>
              <message>Request Generation has started</message>
         </response>
       ```
       

## How to build
      Steps: 
      1. Clone project in any directory 
      2. Enter directory 
      3. Execute Command: sbt dist 
          zip file will be created and the location will be printed in logs.
      4. Unzip file, you will get and jar to deploy
      5. deploy using command on any linux machine:  
        nohup bin/vcs -J-Xms512M -J-Xmx1G -Dpidfile.path=RUNNING_PID -Dlogger.file=prod/logback.xml -Dconfig.file=prod/prod.conf 
        -Dhttp.port=9000 &> vcs.out & tailf vcs.out
        -J-Xms512M :
        -J-Xmx1G  :
        -Dpidfile.path = RUNNING_PID : to store the java process ID.
        -Dlogger.file = logback.xml : Logging configurations 
        -Dconfig.file = prod/prod.conf : Application configuration file.
        -Dhttp.port = 9000   : port on which process will be running
        Logs will be written in vcs.out file.
           
##  Tech stack
### Scala 
### Akka
### Play Framework
### Cassandra
### XML
