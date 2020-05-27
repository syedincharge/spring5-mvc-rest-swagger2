package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.api.v1.model.CustomerListDTO;
import guru.springframework.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {

    public static final String BASE_URL = "/api/v1/customers";

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("")
    public ResponseEntity<CustomerListDTO> getAllCustomers(){
        List<CustomerDTO> customerDTOList = customerService.getAll();
        CustomerListDTO customerListDTO = new CustomerListDTO(customerDTOList);
        return new ResponseEntity<>(customerListDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id){

        CustomerDTO customerDTO = customerService.getById(id);
        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<CustomerDTO> createNewCustomer(@RequestBody CustomerDTO customerDTO){

        CustomerDTO savedCustomerDTO = customerService.createCustomer(customerDTO);
        return new ResponseEntity<CustomerDTO>(savedCustomerDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateExistingCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO){

        CustomerDTO savedCustomerDTO = customerService.updateCustomer(id, customerDTO);
        return new ResponseEntity<CustomerDTO>(savedCustomerDTO, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomerDTO> patchCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO){

        CustomerDTO updatedCustomerDTO = customerService.patchCustomer(id, customerDTO);
        return new ResponseEntity<>(updatedCustomerDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        customerService.deleteCustomerById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


}
