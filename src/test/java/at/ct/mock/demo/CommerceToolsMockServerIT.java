package at.ct.mock.demo;

import at.ct.mock.demo.fixtures.CommercetoolsFixtureLoader;
import com.commercetools.api.client.ProjectApiRoot;
import at.ct.mock.commontestutils.CommercetoolsTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static at.ct.mock.demo.fixtures.FixtureType.CUSTOMERS;
import static at.ct.mock.demo.fixtures.FixtureType.PRODUCTS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@CommercetoolsTest
public class CommerceToolsMockServerIT {

    @Autowired
    private CommercetoolsFixtureLoader fixtureLoader;

    @Autowired
    private ProjectApiRoot commercetoolsClient;

    @BeforeAll
    void loadFixtures() throws Exception {
        fixtureLoader.load(Set.of(CUSTOMERS, PRODUCTS));
    }

    @Test
    void testFetchProducts() {
        var products = commercetoolsClient.products().get().executeBlocking().getBody().getResults();
        assertEquals(20, products.size());
    }

    @Test
    void testFetchCustomers() {
        var customers = commercetoolsClient.customers().get().executeBlocking().getBody().getResults();
        assertEquals(20, customers.size());
    }
}
