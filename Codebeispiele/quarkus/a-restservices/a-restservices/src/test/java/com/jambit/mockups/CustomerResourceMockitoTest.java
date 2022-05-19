package com.jambit.mockups;

import com.jambit.Customer;
import com.jambit.CustomerRepo;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.RestAssured;
import net.bytebuddy.asm.Advice;
import org.apache.commons.lang3.time.CalendarUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

@QuarkusTest
public class CustomerResourceMockitoTest {
    @InjectMock
    CustomerRepo customerRepo;

    @BeforeEach
    void beforeEach(){
        Customer theOneAndOnlyCustomer = new Customer(1L, "Mockito Mockup", LocalDate.now(), Customer.Status.ORDINARY);
        Mockito.when(customerRepo.findAll()).thenReturn(List.of(theOneAndOnlyCustomer));
    }

    @Test
    void findAllCustomersUsingMockup(){
        Customer[] customers = RestAssured.given()
                .when()
                .header("accept", "application/json")
                .get("/api/customers")
                .as(Customer[].class);

        Assertions.assertEquals(1, customers.length);
        Assertions.assertEquals("Mockito Mockup", customers[0].getName());

    }
}
