package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static guru.springframework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
class CustomerControllerTest {

    public static final long ID = 1L;
    public static final long ID1 = 2L;
    public static final String FIRST_NAME = "Lior";
    public static final String FIRST_NAME1 = "Meitar";
    public static final String LAST_NAME = "Lavon";

    @InjectMocks
    CustomerController customerController;

    @Mock
    CustomerService customerService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void getAllCustomers() throws Exception {

        // given
        CustomerDTO cus1 = CustomerDTO.builder().id(ID).firstName(FIRST_NAME).build();
        CustomerDTO cus2 = CustomerDTO.builder().id(ID1).firstName(FIRST_NAME1).build();

        List<CustomerDTO> customerList = Arrays.asList(cus1, cus2);

        // when
        when(customerService.getAll()).thenReturn(customerList);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    void getCustomerById() throws Exception {

        // given
        CustomerDTO cus1 = CustomerDTO.builder().id(ID).firstName(FIRST_NAME).build();

        // when
        when(customerService.getById(ID)).thenReturn(cus1);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/" + ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)));

    }

    @Test
    void createNewCustomer() throws Exception {
        CustomerDTO customerDTO = CustomerDTO.builder().firstName(FIRST_NAME).lastName(LAST_NAME).build();
        CustomerDTO returnCustomerDTO = CustomerDTO.builder().id(1L).firstName(FIRST_NAME).lastName(LAST_NAME).build();

        when(customerService.createNewCustomer(ArgumentMatchers.any())).thenReturn(returnCustomerDTO);

        //String stringValue = ;
        mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)));

    }

    @Test
    void createNewToDo() throws Exception {

        CustomerDTO customer = CustomerDTO.builder().firstName("Lior").lastName("Lavon").build();
        CustomerDTO returnCustomerDTO = CustomerDTO.builder().id(1L).firstName("Lior").lastName("Lavon").build();

        when(customerService.createNewCustomer(ArgumentMatchers.any())).thenReturn(returnCustomerDTO);

        mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo("Lior")))
                .andExpect(jsonPath("$.lastName", equalTo("Lavon")));
                //.andReturn().getResponse().getContentAsString();

    }

}