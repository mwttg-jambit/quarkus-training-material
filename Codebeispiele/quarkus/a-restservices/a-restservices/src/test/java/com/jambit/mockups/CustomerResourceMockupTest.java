package com.jambit.mockups;

import com.jambit.Customer;
import com.jambit.CustomerResourceTest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@QuarkusTest
@Disabled
public class CustomerResourceMockupTest {
    @Test
    void findAllCustomersUsingMockup(){
        Customer[] customers = RestAssured.given()
                .when()
                .header("accept", "application/json")
                .get("/api/customers")
                .as(Customer[].class);

        Assertions.assertEquals(1, customers.length);
        Assertions.assertEquals("From Muckup", customers[0].getName());

    }
}
