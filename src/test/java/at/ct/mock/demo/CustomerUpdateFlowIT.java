package at.ct.mock.demo;

import at.ct.mock.demo.client.api.DefaultApi;
import at.ct.mock.demo.client.model.CustomerUpdate;
import at.ct.mock.demo.fixtures.CommercetoolsFixtureLoader;
import at.ct.mock.demo.fixtures.FixtureScenario;
import com.commercetools.api.client.ProjectApiRoot;
import at.ct.mock.commontestutils.CommercetoolsTest;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@CommercetoolsTest
public class CustomerUpdateFlowIT {

    private final DefaultApi customerClient;
    private final CommercetoolsFixtureLoader fixtureLoader;
    private final ProjectApiRoot commercetoolsClient;

    public CustomerUpdateFlowIT(@Autowired final DefaultApi customerClient, @Autowired  final CommercetoolsFixtureLoader fixtureLoader, @Autowired  final ProjectApiRoot commercetoolsClient) {
        this.customerClient = customerClient;
        this.fixtureLoader = fixtureLoader;
        this.commercetoolsClient = commercetoolsClient;
    }

    @BeforeAll
    void beforeAll() throws Exception {
        fixtureLoader.load(FixtureScenario.CUSTOMERS);
    }

    @Test
    void runUpdateCustomerHappyPath() {
        var customerUpdate = new CustomerUpdate();
        customerUpdate.setKey("customer-1");
        customerUpdate.setFirstName("daniel");
        customerUpdate.setLastName("still the same as it was");

        // call customerUpdate customer endpoint
        customerClient.updateCustomer("customer-1", customerUpdate);

        // wait and check the customerUpdate in commercetools
        Awaitility.await()
                .atMost(3, TimeUnit.SECONDS)
                .untilAsserted(
                        () -> {
                            var customer = commercetoolsClient.customers().withKey("customer-1").get().executeBlocking().getBody();
                            assertEquals("daniel", customer.getFirstName());
                            assertEquals("still the same as it was", customer.getLastName());
                        });
    }

}
