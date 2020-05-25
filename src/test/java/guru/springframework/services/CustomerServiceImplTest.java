package guru.springframework.services;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class CustomerServiceImplTest {

    public static final long ID = 1L;
    public static final long ID1 = 2L;
    public static final String FIRST_NAME = "Lior";
    public static final String FIRST_NAME1 = "Meitar";
    public static final String LAST_NAME = "Lavon";
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;


    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);

        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    void getAllCustomers() {

        // given
        Customer cus1 = Customer.builder().id(ID).firstName(FIRST_NAME).build();
        Customer cus2 = Customer.builder().id(ID1).firstName(FIRST_NAME1).build();

        //when
        List<Customer> customerList = Arrays.asList(cus1, cus2);
        when(customerRepository.findAll()).thenReturn(customerList);

        List<CustomerDTO> customerDTOList = customerService.getAll();

        //then
        assertEquals(2, customerDTOList.size());
    }

    @Test
    void getCustomerById() {

        // given
        Customer cus1 = Customer.builder().id(ID).firstName(FIRST_NAME).build();

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(cus1));

        //when
        CustomerDTO customerDTO = customerService.getById(ID);

        //then
        assertEquals(ID, customerDTO.getId());
        assertEquals(FIRST_NAME, customerDTO.getFirstName());
    }

    @Test
    void createNewCustomer() {

        // given
        CustomerDTO customerDTO = CustomerDTO.builder().firstName(FIRST_NAME).lastName(LAST_NAME).build();

        Customer savedCustomer = Customer.builder().id(1L).firstName(FIRST_NAME).lastName(LAST_NAME).build();

        when(customerRepository.save(ArgumentMatchers.any())).thenReturn(savedCustomer);

        // when
        CustomerDTO savedDTOCustomer = customerService.createCustomer(customerDTO);

        // then
        assertEquals(FIRST_NAME, savedDTOCustomer.getFirstName());
        assertEquals("http://Lior.Lavon.com", savedDTOCustomer.getCustomer_url());
    }
}