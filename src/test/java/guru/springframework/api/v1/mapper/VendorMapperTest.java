package guru.springframework.api.v1.mapper;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VendorMapperTest {

    public static final long ID = 1L;
    public static final String VENDOR_NAME = "VENDOR_NAME";
    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @BeforeEach
    void setUp() {
    }

    @Test
    void vendorToVendorDTO() {

        Vendor vendor = Vendor.builder().id(ID).name(VENDOR_NAME).build();

        VendorDTO vendorDTO = vendorMapper.VendorToVendorDTO(vendor);

        assertEquals(ID, vendorDTO.getId());
        assertEquals(VENDOR_NAME, vendorDTO.getName());
    }

    @Test
    void vendorDTOToVendor() {

        VendorDTO vendorDTO = VendorDTO.builder().id(ID).name(VENDOR_NAME).build();

        Vendor vendor = vendorMapper.VendorDTOToVendor(vendorDTO);

        assertEquals(ID, vendor.getId());
        assertEquals(VENDOR_NAME, vendor.getName());
    }
}