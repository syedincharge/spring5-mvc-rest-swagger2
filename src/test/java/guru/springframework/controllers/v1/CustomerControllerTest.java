package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.domain.Customer;
import guru.springframework.services.CustomerService;
import guru.springframework.services.ResourceNotFoundException;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
class CustomerControllerTest {

    public static final long ID = 1L;
    public static final long ID1 = 2L;
    public static final String FIRST_NAME = "Lior";
    public static final String FIRST_NAME1 = "Meitar";
    public static final String LAST_NAME = "Lavon";
    public static final String LIOR_V_1 = "Lior_v1";
    public static final String LAVON_V_1 = "Lavon_v1";
    public static final String NEW_VALUE = "NewValue";

    @InjectMocks
    CustomerController customerController;

    @Mock
    CustomerService customerService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler()) // add exception handler NotFound
                .build();
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
        mockMvc.perform(MockMvcRequestBuilders.get(CustomerController.BASE_URL)
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
        mockMvc.perform(MockMvcRequestBuilders.get(CustomerController.BASE_URL + "/" + ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)));

    }

    @Test
    void createNewCustomer() throws Exception {
        CustomerDTO customerDTO = CustomerDTO.builder().firstName(FIRST_NAME).lastName(LAST_NAME).build();
        CustomerDTO returnCustomerDTO = CustomerDTO.builder().id(1L).firstName(FIRST_NAME).lastName(LAST_NAME).build();

        when(customerService.createCustomer(ArgumentMatchers.any())).thenReturn(returnCustomerDTO);

        //String stringValue = ;
        mockMvc.perform(post(CustomerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)));
        //.andReturn().getResponse().getContentAsString();

    }

    @Test
    void updateExistingCustomer() throws Exception {

        CustomerDTO existCustomerDTO = CustomerDTO.builder().id(1L).firstName(FIRST_NAME).lastName(LAST_NAME).build();

        CustomerDTO customerDTOToUpdate = CustomerDTO.builder().id(1L).firstName(LIOR_V_1).lastName(LAVON_V_1).build();

        when(customerService.updateCustomer(ArgumentMatchers.any(), ArgumentMatchers.any(CustomerDTO.class))).thenReturn(customerDTOToUpdate);

        //String stringValue = ;
        mockMvc.perform(put(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTOToUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(LIOR_V_1)))
                .andExpect(jsonPath("$.lastName", equalTo(LAVON_V_1)));
        //.andReturn().getResponse().getContentAsString();

    }

    @Test
    void patchExistingCustomer() throws Exception {

        CustomerDTO customerDTO = CustomerDTO.builder().firstName(NEW_VALUE).build();

        CustomerDTO returnedCustomerDTO = CustomerDTO.builder().id(ID).firstName(NEW_VALUE).lastName(LAST_NAME).build();

        when(customerService.patchCustomer(ArgumentMatchers.any(), ArgumentMatchers.any(CustomerDTO.class))).thenReturn(returnedCustomerDTO);

        //String stringValue = ;
        mockMvc.perform(patch(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.firstName", equalTo(NEW_VALUE)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)));

//                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void deleteById() throws Exception {

        mockMvc.perform(delete(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getCustomerByIdNotFound() throws Exception {

        when(customerService.getById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(CustomerController.BASE_URL + "/" + 100)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}