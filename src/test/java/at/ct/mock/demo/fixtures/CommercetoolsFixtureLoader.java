package at.ct.mock.demo.fixtures;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.customer.CustomerDraft;
import com.commercetools.api.models.product.ProductDraft;
import com.commercetools.api.models.product_type.ProductTypeDraft;
import com.commercetools.api.models.product_type.ProductTypeResourceIdentifierBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.util.List;
import java.util.Set;

import static at.ct.mock.demo.fixtures.FixtureType.CUSTOMERS;
import static at.ct.mock.demo.fixtures.FixtureType.PRODUCTS;

@ActiveProfiles("test")
@Component
public class CommercetoolsFixtureLoader {

    private final ProjectApiRoot client;
    private final ObjectMapper mapper;

    // Internal fixture paths
    private final String PRODUCTS_PATH = "src/test/resources/fixtures/products.json";
    private final String CUSTOMERS_PATH = "src/test/resources/fixtures/customers.json";

    public CommercetoolsFixtureLoader(@Autowired ProjectApiRoot client) {
        this.client = client;
        this.mapper = new ObjectMapper();
    }

    public void load(Set<FixtureType> fixtureTypes) throws Exception {
        if (fixtureTypes.contains(PRODUCTS)) {
            loadProducts();
        }
        if (fixtureTypes.contains(CUSTOMERS)) {
            loadCustomers();
        }
    }

    private void loadProducts() throws Exception {
        // product type is mandatory in ct
        client.productTypes().post(ProductTypeDraft.builder().key("product-type-key").name("product-type-1").description("product-type-1").build()).executeBlocking();

        List<ProductDraft> products = mapper.readValue(
                new File(PRODUCTS_PATH),
                new TypeReference<>() {
                }
        );

        for (ProductDraft product : products) {
            product.setProductType(ProductTypeResourceIdentifierBuilder.of()
                    .id("product-type-1")
                    .key("product-type-key")
                    .build());
            client.products().post(product).executeBlocking();
        }
    }

    private void loadCustomers() throws Exception {
        List<CustomerDraft> customers = mapper.readValue(
                new File(CUSTOMERS_PATH),
                new TypeReference<>() {
                }
        );

        for (CustomerDraft customer : customers) {
            client.customers().post(customer).executeBlocking();
        }
    }
}

