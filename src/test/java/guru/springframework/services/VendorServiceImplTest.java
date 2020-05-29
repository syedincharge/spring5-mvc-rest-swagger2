package guru.springframework.services;

import guru.springframework.api.v1.mapper.ProductMapper;
import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class VendorServiceImplTest {

    @Mock
    VendorRepository vendorRepository;

    VendorService vendorService;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);

        vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository, ProductMapper.INSTANCE);
    }

    @Test
    void getAllVendors() {

        Vendor vendor1 = Vendor.builder().id(1L).name("Vendor1").build();
        Vendor vendor2 = Vendor.builder().id(2L).name("Vendor2").build();
        List<Vendor> vendorList = Arrays.asList(vendor1, vendor2);

        when(vendorRepository.findAll()).thenReturn(vendorList);

        List<VendorDTO> vendorListDTO = vendorService.getAll();

        verify(vendorRepository, times(1)).findAll();
        assertEquals(2, vendorListDTO.size());
    }

    @Test
    void getById() {

        Vendor vendor = Vendor.builder().id(1L).name("VendorName").build();

        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));

        VendorDTO vendorDTO = vendorService.getById(1L);

        assertEquals(1L, vendorDTO.getId());
        assertEquals("VendorName", vendorDTO.getName());
        assertEquals(VendorServiceImpl.getVendorURL(1L), vendorDTO.getVendor_url());
    }

    @Test
    void createVendor() {

        Vendor vendor = Vendor.builder().id(1L).name("VendorName").build();

        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        VendorDTO savedVendorDTO = vendorService.createVendor(new VendorDTO());

        assertEquals(1L, savedVendorDTO.getId());
        assertEquals("VendorName", savedVendorDTO.getName());
        assertEquals( VendorServiceImpl.getVendorURL(1L), savedVendorDTO.getVendor_url());
    }

    @Test
    void updateVendor() {
        Vendor orgVendor = Vendor.builder().id(1L).name("VendorName").build();
        Vendor newVendor = Vendor.builder().id(1L).name("New_VendorName").build();
        VendorDTO toSaveVendorDTO = VendorDTO.builder().id(1L).name("New_VendorName").build();

        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(orgVendor));
        when(vendorRepository.save(any(Vendor.class))).thenReturn(newVendor);

        VendorDTO savedVendorDTO = vendorService.updateVendor(1L, toSaveVendorDTO);

        assertEquals(1L, savedVendorDTO.getId());
        assertEquals("New_VendorName", savedVendorDTO.getName());
        assertEquals( VendorServiceImpl.getVendorURL(1L), savedVendorDTO.getVendor_url());
    }

    @Test
    void patchVendor() {
        Vendor orgVendor = Vendor.builder().id(1L).name("VendorName").build();
        Vendor newVendor = Vendor.builder().id(1L).name("New_VendorName").build();
        VendorDTO toSaveVendorDTO = VendorDTO.builder().id(1L).name("New_VendorName").build();

        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(orgVendor));
        when(vendorRepository.save(any(Vendor.class))).thenReturn(newVendor);

        VendorDTO savedVendorDTO = vendorService.patchVendor(1L, toSaveVendorDTO);

        assertEquals(1L, savedVendorDTO.getId());
        assertEquals("New_VendorName", savedVendorDTO.getName());
        assertEquals( VendorServiceImpl.getVendorURL(1L), savedVendorDTO.getVendor_url());
    }

    @Test
    void deleteVendor() {

        Long id = 1L;
        vendorService.deleteVendor(id);

        verify(vendorRepository, times(1)).deleteById(anyLong());
    }
}