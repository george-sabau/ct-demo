package at.ct.mock.demo;

import at.ct.mock.demo.client.api.DefaultApi;
import at.ct.mock.demo.client.model.CustomerUpdate;
import at.ct.mock.demo.fixtures.CommercetoolsFixtureLoader;
import at.ct.mock.demo.server.CommercetoolsTest;
import com.commercetools.api.client.ProjectApiRoot;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import static at.ct.mock.demo.fixtures.FixtureType.CUSTOMERS;
import static at.ct.mock.demo.fixtures.FixtureType.PRODUCTS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@CommercetoolsTest
public class CustomerUpdateFlowIT {

    @LocalServerPort
    private int port;

    @Autowired
    private DefaultApi customerClient;

    @Autowired
    private CommercetoolsFixtureLoader fixtureLoader;

    @Autowired
    private ProjectApiRoot commercetoolsClient;

    @BeforeAll
    void beforeAll() throws Exception {
        customerClient.getApiClient().setBasePath("http://localhost:" + port + "/api");
        fixtureLoader.load(Set.of(CUSTOMERS, PRODUCTS));
    }

    @Test
    void runUpdateCustomerHappyPath() {
        var customerUpdate = new CustomerUpdate();
        customerUpdate.setKey("customer-1");
        customerUpdate.setFirstName("not the same as it was");
        customerUpdate.setLastName("still the same as it was");

        // call customerUpdate customer endpoint
        customerClient.updateCustomer("customer-1", customerUpdate);

        // wait and check the customerUpdate in commercetools
        Awaitility.await()
                .atMost(3, TimeUnit.SECONDS)
                .untilAsserted(
                        () -> {
                            var customer = commercetoolsClient.customers().withKey("customer-1").get().executeBlocking().getBody();
                            assertEquals("not the same as it was", customer.getFirstName());
                            assertEquals("still the same as it was", customer.getLastName());
                        });
    }

}
