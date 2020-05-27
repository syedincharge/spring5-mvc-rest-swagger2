package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.api.v1.model.VendorListDTO;
import guru.springframework.domain.Vendor;
import guru.springframework.services.ResourceNotFoundException;
import guru.springframework.services.VendorService;
import guru.springframework.services.VendorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.session.MockitoSessionBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static guru.springframework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VendorControllerTest {

    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorController vendorController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler()) // add exception handler NotFound
                .build();
    }

    @Test
    void getAllVendors() throws Exception {

        VendorDTO vendor1 = VendorDTO.builder().id(1L).name("Vendor1").build();
        VendorDTO vendor2 = VendorDTO.builder().id(2L).name("Vendor2").build();
        List<VendorDTO> vendorListDTO = Arrays.asList(vendor1, vendor2);

        when(vendorService.getAll()).thenReturn(vendorListDTO);

        mockMvc.perform(MockMvcRequestBuilders.get(VendorController.BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    void getById() throws Exception {

        VendorDTO vendor = VendorDTO.builder().id(1L).name("VendorName").vendor_url(VendorServiceImpl.getVendorURL(1L)).build();

        when(vendorService.getById(anyLong())).thenReturn(vendor);

        mockMvc.perform(MockMvcRequestBuilders.get(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("VendorName")))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorServiceImpl.getVendorURL(1L))));
    }

    @Test
    void createNewVendor() throws Exception {

        VendorDTO vendor = VendorDTO.builder().id(1L).name("VendorName").vendor_url(VendorServiceImpl.getVendorURL(1L)).build();

        when(vendorService.createVendor(any())).thenReturn(vendor);

        mockMvc.perform(MockMvcRequestBuilders.post(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(vendor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("VendorName")))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorServiceImpl.getVendorURL(1L))));
    }

    @Test
    void updateVendor() throws Exception {

        VendorDTO vendor = VendorDTO.builder().name("NewVendorName").build();
        VendorDTO updatedVendor = VendorDTO.builder().id(1L).name("NewVendorName").vendor_url(VendorServiceImpl.getVendorURL(1L)).build();

        when(vendorService.updateVendor(anyLong(), any())).thenReturn(updatedVendor);

        mockMvc.perform(MockMvcRequestBuilders.put(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("NewVendorName")))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorServiceImpl.getVendorURL(1L))));
    }

    @Test
    void patchVendor() throws Exception {

        VendorDTO vendor = VendorDTO.builder().name("NewVendorName").build();
        VendorDTO updatedVendor = VendorDTO.builder().id(1L).name("NewVendorName").vendor_url(VendorServiceImpl.getVendorURL(1L)).build();

        when(vendorService.patchVendor(anyLong(), any())).thenReturn(updatedVendor);

        mockMvc.perform(MockMvcRequestBuilders.patch(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("NewVendorName")))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorServiceImpl.getVendorURL(1L))));
    }

    @Test
    void deleteVendor() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void getByIdNotFound() throws Exception {

        when(vendorService.getById(anyLong())).thenThrow(new ResourceNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get(VendorController.BASE_URL + "/100")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

}