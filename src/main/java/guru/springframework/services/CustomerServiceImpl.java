package guru.springframework.services;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> getAll() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomer_url("http://" +
                            customerDTO.getFirstName() +
                            "." +
                            customerDTO.getLastName() + ".com");
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getById(Long id) {

        return customerRepository.findById(id)
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomer_url("http://" +
                    customer.getFirstName() +
                    "." +
                    customer.getLastName() + ".com");
                    return customerDTO;
                })
                .orElseThrow(RuntimeException::new);


//        Customer customer = customerRepository.getCustomerById(id);
//        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
//        customerDTO.setCustomer_url("http://" +
//                customer.getFirstName() +
//                "." +
//                customer.getLastName() + ".com");
//        return customerDTO;
    }

}
