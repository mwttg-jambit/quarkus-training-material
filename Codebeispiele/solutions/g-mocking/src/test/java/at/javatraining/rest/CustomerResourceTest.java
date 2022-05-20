package at.javatraining.rest;

import at.javatraining.entities.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class CustomerResourceTest {
    public static final String BASE_URI = "/v1/customers";

    Customer c1;
    Customer c2;

    @BeforeEach
    void beforeEach() {
        c1 = new Customer(1L, "C1 Customer", LocalDate.of(1980, 11, 1), Customer.Status.ORDINARY);
        c2 = new Customer(2L, "C2 Customer", LocalDate.of(1981, 12, 2), Customer.Status.KING);
    }

    @Test
    void getAllCustomers() throws JsonProcessingException {
        Response response = RestAssured.given()
                .when()
                .header("accept", "application/json")
                .get(BASE_URI);

        Customer[] customers = response.as(Customer[].class);
        Arrays.stream(customers).forEach(System.out::println);

        response.then()
                .header("Content-Type", "application/json")
                .and()
                .statusCode(200)
                .and()
                .body("size()", is(100));
    }

    @Test
    void findById() {
        RestAssured.given()
                .header("accept", "application/json")
                .get(BASE_URI + "/1")
                .then().statusCode(200)
                .header("Content-Type", "application/json")
                .body("id", is(1))
                .body("name", not(is(nullValue())))
                .body("dateOfBirth", not(is(nullValue())))
                .body("status", not(is(nullValue())));
    }

    @Test
    void update() {

        Customer[] customers = {c1, c2};
        Arrays.stream(customers).forEach(c -> {
            RestAssured.given()
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .body(c)
                    .put(BASE_URI + "/" + c.getId())
                    .then()
                    .statusCode(200)
                    .body("id", is(c.getId().intValue())) // error when not converting to an int value
                    .body("name", is(c.getName()))
                    .body("dateOfBirth", is(c.getDateOfBirth().toString()))
                    .body("status", is(c.getStatus().toString()));
        });
    }

    @Test
    void insert() {
        // student excercise
    }

    @Test
    void deleteCustomer() {
        // student exercise
    }
}