package at.javatraining.rest.mocking;

import at.javatraining.entities.Customer;
import at.javatraining.repos.CustomerRepository;
import at.javatraining.rest.CustomerResourceTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.RestAssured;
import io.restassured.internal.ResponseSpecificationImpl;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.hamcrest.HamcrestArgumentMatcher;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
/**
 * IMPORTANT: When running this test comment the @Mock annotation in CuszoemrRepositoryMockup
 * if not doing so this Mockup is mocked up
 * Then uncomment the @Disabled Annotation of this test
 */
@Disabled
public class MokitoMockupTest {
    @InjectMock
    CustomerRepository customerRepositoryMockup;

    @BeforeEach
    void setUpMockup() {
        Customer c1 = new Customer(1L, "Mockito Customer", LocalDate.now(), Customer.Status.ORDINARY);
        Customer c2 = new Customer(1L, "Mockito Customer 2", LocalDate.now(), Customer.Status.ORDINARY);
        List<Customer> customers = List.of(c1, c2);
        Mockito.when(customerRepositoryMockup.findAll()).thenReturn(customers);
    }

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
                .body("size()", is(2));

        MatcherAssert.assertThat(customers[0].getName(), equalTo("Mockito Customer"));
    }
}
