package at.ct.mock.demo.fixtures;

import lombok.experimental.Delegate;

import java.util.EnumSet;
import java.util.Set;

public enum FixtureScenario implements Set<FixtureType> {

    // Scenario for setting up product catalog
    PRODUCT_CATALOG(EnumSet.of(
            FixtureType.PRODUCTS,
            FixtureType.PRODUCT_TYPES,
            FixtureType.CATEGORIES,
            FixtureType.TAX_CATEGORIES,
            FixtureType.CHANNELS,
            FixtureType.INVENTORY_ENTRIES,
            FixtureType.CUSTOM_TYPES
    )),

    // Scenario for setting up customers
    CUSTOMERS(EnumSet.of(
            FixtureType.CUSTOMERS,
            FixtureType.CUSTOMER_GROUPS
    )),

    // Scenario for setting up orders and carts
    ORDERS_AND_CARTS(EnumSet.of(
            FixtureType.CARTS,
            FixtureType.ORDERS,
            FixtureType.DISCOUNT_CODES,
            FixtureType.PAYMENTS
    )),

    // Scenario for setting up discounts and promotions
    DISCOUNTS_AND_PROMOTIONS(EnumSet.of(
            FixtureType.PRODUCT_DISCOUNTS,
            FixtureType.CART_DISCOUNTS
    )),

    // Scenario for shipping configuration
    SHIPPING(EnumSet.of(
            FixtureType.SHIPPING_METHODS,
            FixtureType.ZONES
    )),

    // Scenario for store setup
    STORES(EnumSet.of(
            FixtureType.STORES
    )),

    FULL(EnumSet.allOf(FixtureType.class));

    @Delegate
    private final Set<FixtureType> delegate;

    FixtureScenario(Set<FixtureType> fixtureTypes) {
        this.delegate = EnumSet.copyOf(fixtureTypes);
    }

}