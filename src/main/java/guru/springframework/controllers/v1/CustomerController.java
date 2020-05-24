package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.api.v1.model.CustomerListDTO;
import guru.springframework.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1/customers")
public class CustomerController {

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
        return new ResponseEntity<CustomerDTO>(customerDTO, HttpStatus.OK);
    }

}
