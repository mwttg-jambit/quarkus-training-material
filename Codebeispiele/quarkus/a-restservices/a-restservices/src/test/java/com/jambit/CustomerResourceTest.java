package com.jambit;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class CustomerResourceTest {

    public static final String BASE_URL = "/api/customers";

    @Test
    void allCustomers() {
        Response response = RestAssured.given()
                .when()
                .header("accept", "application/json")
                .get(BASE_URL);

        Customer[] customersResult = response.as(Customer[].class);
        Arrays.stream(customersResult).forEach(System.out::println);

        response.then()
                .header("Content-Type","application/json;charset=UTF-8")
                .and()
                .statusCode(200)
                .and()
                .body("size()", CoreMatchers.is(1));
    }

    @Test
    void findById() {
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }
}