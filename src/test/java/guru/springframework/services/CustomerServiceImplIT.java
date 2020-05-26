package guru.springframework.services;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.bootstrap.Bootstrap;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.not;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class CustomerServiceImplIT {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CustomerRepository customerRepository;

    CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() throws Exception {

        System.out.println("Loading Bootstrap content");

        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository);
        bootstrap.run();

        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    void patchCustomerUpdateFirstName() {

        String updateFirstName = "UpdateName";
        Long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);

        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(updateFirstName);

        customerService.patchCustomer(id, customerDTO);

        Customer customer = customerRepository.findById(id).get();

        assertNotNull(customer);
        assertEquals(updateFirstName, customer.getFirstName());
        assertNotEquals(originalFirstName, customer.getFirstName());
        assertEquals(originalLastName, customer.getLastName());
    }

    @Test
    void patchCustomerUpdateLastName() {
        String updateLastName = "UpdateName";
        Long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);

        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastName(updateLastName);

        customerService.patchCustomer(id, customerDTO);

        Customer customer = customerRepository.findById(id).get();

        assertNotNull(customer);
        assertEquals(updateLastName, customer.getLastName());
        assertNotEquals(originalLastName, customer.getLastName());
        assertEquals(originalFirstName, customer.getFirstName());
    }

    private Long getCustomerIdValue() {

        return customerRepository.findAll()
                .stream()
                .findFirst()
                .map(customer -> customer.getId())
                .orElseThrow(RuntimeException::new);
    }


}