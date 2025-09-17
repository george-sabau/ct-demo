package at.ct.mock.demo.controller;

import at.ct.mock.demo.client.model.CustomerResponse;
import at.ct.mock.demo.client.model.CustomerUpdate;
import at.ct.mock.demo.service.CustomerService;
import com.commercetools.api.models.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PutMapping("/{key}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable String key,
            @RequestBody CustomerUpdate update) {

        if (!key.equals(update.getKey())) {
            return ResponseEntity.badRequest().build();
        }

        customerService.update(update.getKey(), update.getFirstName(), update.getLastName());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{key}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable String key) {
        var customer = customerService.get(key);

        if (customer == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(toResponse(customer));
    }

    private static CustomerResponse toResponse(final Customer customer) {
        var response = new CustomerResponse();
        response.setKey(customer.getKey());
        response.setFirstName(customer.getFirstName());
        response.setLastName(customer.getLastName());
        response.setUpdatedAt(customer.getCreatedAt().toOffsetDateTime());
        return response;
    }
}
