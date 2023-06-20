package com.credit.card.creditcardservice.integration;

import com.credit.card.creditcardservice.controller.dto.CreditCardRequestPutDto;
import com.credit.card.creditcardservice.controller.dto.CustomerDto;
import com.credit.card.creditcardservice.domain.CreditCardRequest;
import com.credit.card.creditcardservice.enums.RecordStatus;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTests {
    @LocalServerPort
    private Integer port;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Test
    void fullLifeCycleTest() throws IOException {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(getCustomerDto())
                .when()
                .post("/customers/credit-cards")
                .then()
                .extract().response();
        assertEquals(HttpStatus.OK.value(), response.statusCode());
        InputStream responseStream = response.getBody().asInputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        CreditCardRequest creditCardRequest = objectMapper.readValue(responseStream, CreditCardRequest.class);
        assertEquals(creditCardRequest.getStatus(), RecordStatus.NEW);

        response = given()
                .contentType(ContentType.JSON)
                .param("customerId", creditCardRequest.getCustomerId())
                .when()
                .get("/customers/credit-cards")
                .then()
                .extract().response();
        responseStream = response.getBody().asInputStream();
        List<LinkedHashMap> list = objectMapper.readValue(responseStream, List.class);
        assertEquals(HttpStatus.OK.value(), response.statusCode());
        assertEquals(list.size(), 1);
        assertEquals(list.get(0).get("status"),RecordStatus.NEW.name());

        response = given()
                .contentType(ContentType.JSON)
                .body(getCreditCardRequestPutDto(UUID.fromString(list.get(0).get("applicationUuid").toString())))
                .when()
                .put("/customers/credit-cards")
                .then()
                .extract().response();
        String responseString = response.getBody().print();
        assertEquals(HttpStatus.OK.value(), response.statusCode());
        assertEquals(responseString, "Successfully updated");

        response = given()
                .contentType(ContentType.JSON)
                .param("customerId", creditCardRequest.getCustomerId())
                .when()
                .get("/customers/credit-cards")
                .then()
                .extract().response();
        responseStream = response.getBody().asInputStream();
        list = objectMapper.readValue(responseStream, List.class);
        assertEquals(HttpStatus.OK.value(), response.statusCode());
        assertEquals(list.get(0).get("status"),RecordStatus.APPROVED.name());
    }

    private CustomerDto getCustomerDto() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setAddress("Address");
        customerDto.setCustomerName("John Doe");
        customerDto.setMobileNumber("1234567890");
        customerDto.setCommunicationInfo("Call to Mobile number");
        return customerDto;
    }

    private CreditCardRequestPutDto getCreditCardRequestPutDto(UUID applicationUuid){
        CreditCardRequestPutDto creditCardRequestPutDto = new CreditCardRequestPutDto();
        creditCardRequestPutDto.setComment("Approved");
        creditCardRequestPutDto.setStatus(RecordStatus.APPROVED);
        creditCardRequestPutDto.setApplicationUuid(applicationUuid);
        return creditCardRequestPutDto;
    }

}
