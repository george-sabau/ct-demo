package at.ct.mock.demo.controller;

import at.ct.mock.demo.client.model.CustomerResponse;
import at.ct.mock.demo.client.model.CustomerUpdate;
import at.ct.mock.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
}
