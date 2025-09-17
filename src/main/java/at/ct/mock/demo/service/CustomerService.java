package at.ct.mock.demo.service;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.customer.CustomerSetFirstNameAction;
import com.commercetools.api.models.customer.CustomerSetLastNameAction;
import com.commercetools.api.models.customer.CustomerUpdate;
import com.commercetools.api.models.customer.CustomerUpdateAction;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CustomerService {

    private final ProjectApiRoot commercetoolsClient;

    public CustomerService(final ProjectApiRoot commercetoolsClient) {
        this.commercetoolsClient = commercetoolsClient;
    }

    /**
     * propagate update to commercetools
     */
    public void update(final String key, final String firstName, final String lastName) {
        CustomerUpdateAction setFirstName = CustomerSetFirstNameAction.builder()
                .firstName(firstName)
                .build();

        CustomerUpdateAction setLastName = CustomerSetLastNameAction.builder()
                .lastName(lastName)
                .build();

        CustomerUpdate update = CustomerUpdate.builder()
                .version(1L)
                .actions(Arrays.asList(setFirstName, setLastName))
                .build();

        commercetoolsClient.customers()
                .withKey(key)
                .post(update)
                .executeBlocking();
    }

    public Customer get(final String key) {
        return commercetoolsClient.customers().withKey(key).get().executeBlocking().getBody();
    }
}
