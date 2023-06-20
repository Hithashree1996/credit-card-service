# Credit card service
## Running App in Local
- 1. JDK
   - Download jdk 17 and set the path to JAVA_HOME according to the environment.
   ```https://jdk.java.net/archive/```
   - This repo requires JDK 17. Make sure you are currently on JDK 17.
        ```
         java --version
       ```
    
- 2. Install docker 
  - Download and install docker from ```https://www.docker.com/products/docker-desktop/```
  - Use the below command to quickly start a postgres container(optional if you postgres db mention that in the properties file)
    ```docker run --name postgresql-container -p 5432:5432 -e POSTGRES_USER=user -e POSTGRES_DB=credit_card -e POSTGRES_PASSWORD=password -d postgres:15-alpine```
- 3. Run ```mvn clean install```
- 4. Start the application by running the ```CreditCardServiceApplication```
