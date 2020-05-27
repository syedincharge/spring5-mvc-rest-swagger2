package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.api.v1.model.CustomerListDTO;
import guru.springframework.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {

    public static final String BASE_URL = "/api/v1/customers";

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public CustomerListDTO getAllCustomers(){

        List<CustomerDTO> customerDTOList = customerService.getAll();
        return new CustomerListDTO(customerDTOList);
    }

    // get single customer
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomerById(@PathVariable Long id){

        return customerService.getById(id);
    }

    // create new customer
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createNewCustomer(@RequestBody CustomerDTO customerDTO){

        return customerService.createCustomer(customerDTO);
    }

    // update existing / create new customer
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO updateExistingCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO){

        return customerService.updateCustomer(id, customerDTO);
    }

    // partial update customer
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO patchCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO){

        return customerService.patchCustomer(id, customerDTO);
    }

    // delete customer
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Void deleteById(@PathVariable Long id){
        customerService.deleteCustomerById(id);
        return null;
    }


}
