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

import static at.ct.mock.demo.fixtures.FixtureType.*;

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
        if (fixtureTypes.contains(PRODUCT_TYPES)) {
            loadProductTypes();
        }
        if (fixtureTypes.contains(PRODUCTS)) {
            loadProducts();
        }
        if (fixtureTypes.contains(CATEGORIES)) {
            loadCategories();
        }
        if (fixtureTypes.contains(TAX_CATEGORIES)) {
            loadTaxCategories();
        }
        if (fixtureTypes.contains(CHANNELS)) {
            loadChannels();
        }
        if (fixtureTypes.contains(INVENTORY_ENTRIES)) {
            loadInventoryEntries();
        }
        if (fixtureTypes.contains(CUSTOM_TYPES)) {
            loadCustomTypes();
        }
        if (fixtureTypes.contains(CUSTOMERS)) {
            loadCustomers();
        }
        if (fixtureTypes.contains(CUSTOMER_GROUPS)) {
            loadCustomerGroups();
        }
        if (fixtureTypes.contains(CARTS)) {
            loadCarts();
        }
        if (fixtureTypes.contains(ORDERS)) {
            loadOrders();
        }
        if (fixtureTypes.contains(DISCOUNT_CODES)) {
            loadDiscountCodes();
        }
        if (fixtureTypes.contains(PAYMENTS)) {
            loadPayments();
        }
        if (fixtureTypes.contains(PRODUCT_DISCOUNTS)) {
            loadProductDiscounts();
        }
        if (fixtureTypes.contains(CART_DISCOUNTS)) {
            loadCartDiscounts();
        }
        if (fixtureTypes.contains(SHIPPING_METHODS)) {
            loadShippingMethods();
        }
        if (fixtureTypes.contains(ZONES)) {
            loadZones();
        }
        if (fixtureTypes.contains(STORES)) {
            loadStores();
        }
    }

    private void loadProductTypes() throws Exception {
        // implement product type creation logic
    }

    private void loadCategories() throws Exception {
        // implement category creation logic
    }

    private void loadTaxCategories() throws Exception {
        // implement tax category creation logic
    }

    private void loadChannels() throws Exception {
        // implement channel creation logic
    }

    private void loadInventoryEntries() throws Exception {
        // implement inventory entries creation logic
    }

    private void loadCustomTypes() throws Exception {
        // implement custom types creation logic
    }

    private void loadCustomerGroups() throws Exception {
        // implement customer groups creation logic
    }

    private void loadCarts() throws Exception {
        // implement cart creation logic
    }

    private void loadOrders() throws Exception {
        // implement orders creation logic
    }

    private void loadDiscountCodes() throws Exception {
        // implement discount codes creation logic
    }

    private void loadPayments() throws Exception {
        // implement payments creation logic
    }

    private void loadProductDiscounts() throws Exception {
        // implement product discount creation logic
    }

    private void loadCartDiscounts() throws Exception {
        // implement cart discount creation logic
    }

    private void loadShippingMethods() throws Exception {
        // implement shipping methods creation logic
    }

    private void loadZones() throws Exception {
        // implement shipping zones creation logic
    }

    private void loadStores() throws Exception {
        // implement store creation logic
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

