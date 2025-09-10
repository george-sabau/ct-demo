package at.ct.mock.demo.fixtures;

public enum FixtureType {

    // -------------------------
    // Products / Catalog
    // -------------------------
    PRODUCTS,              // Products in the catalog
    PRODUCT_TYPES,         // Product type definitions (attributes)
    CATEGORIES,            // Categories for products
    TAX_CATEGORIES,        // Tax categories
    CHANNELS,              // Sales / inventory channels
    INVENTORY_ENTRIES,     // Inventory for product variants
    CUSTOM_TYPES,          // Custom fields for products or other resources

    // -------------------------
    // Customers / Users
    // -------------------------
    CUSTOMERS,             // Customer accounts
    CUSTOMER_GROUPS,       // Customer segmentation

    // -------------------------
    // Orders / Cart
    // -------------------------
    CARTS,                 // Shopping carts
    ORDERS,                // Placed orders
    DISCOUNT_CODES,        // Discount codes
    PAYMENTS,              // Payments

    // -------------------------
    // Discounts / Promotions
    // -------------------------
    PRODUCT_DISCOUNTS,     // Discounts on specific products
    CART_DISCOUNTS,        // Discounts on carts

    // -------------------------
    // Shipping / Zones
    // -------------------------
    SHIPPING_METHODS,      // Shipping methods
    ZONES,                 // Shipping zones

    // -------------------------
    // Stores / Misc
    // -------------------------
    STORES                 // Store objects
}
