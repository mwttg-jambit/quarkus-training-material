package at.javatraining.rest.mocking;

import at.javatraining.entities.Customer;
import at.javatraining.rest.CustomerResourceTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class QuarkusMockupTest {
    /*
           see log file to check that CustomerRepositoryMockup was called instead of
           CustomerRepository
     */

    @Test
    void getAllCustomers() throws JsonProcessingException {
        Response response = RestAssured.given()
                .when()
                .header("accept", "application/json")
                .get(CustomerResourceTest.BASE_URI);

        Customer[] customers = response.as(Customer[].class);
        Arrays.stream(customers).forEach(System.out::println);

        response.then()
                .header("Content-Type", "application/json")
                .and()
                .statusCode(200)
                .and()
                .body("size()", is(1));
    }
}
