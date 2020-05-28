package guru.springframework.services;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.api.v1.model.VendorListDTO;
import guru.springframework.domain.Product;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    public VendorServiceImpl(VendorMapper vendorMapper, VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<VendorDTO> getAll() {
        return vendorRepository.findAll()
                .stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.VendorToVendorDTO(vendor);
                    vendorDTO.setVendor_url(getVendorURL(vendorDTO.getId()));
                    return vendorDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO getById(Long id) {
        return vendorRepository.findById(id)
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.VendorToVendorDTO(vendor);
                    vendorDTO.setVendor_url(getVendorURL(id));
                    return vendorDTO;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO createVendor(VendorDTO vendorDTO) {

        vendorDTO.setId(null);

        Vendor vendor = vendorMapper.VendorDTOToVendor(vendorDTO);
        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO savedVendorDTO = vendorMapper.VendorToVendorDTO(savedVendor);
        savedVendorDTO.setVendor_url(getVendorURL(savedVendorDTO.getId()));

        return savedVendorDTO;
    }

    @Override
    public VendorDTO updateVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id)
                .map(vendor -> {
                    Vendor newVendor  = vendorMapper.VendorDTOToVendor(vendorDTO);
                    newVendor.setId(id);
                    Vendor savedVendor = vendorRepository.save(newVendor);
                    VendorDTO savedVendorDTO = vendorMapper.VendorToVendorDTO(savedVendor);
                    savedVendorDTO.setVendor_url(getVendorURL(id));
                    return savedVendorDTO;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {

        return vendorRepository.findById(id)
                .map(vendor -> {

                    if(vendorDTO.getName()!=null){
                        vendor.setName(vendorDTO.getName());
                    }
                    Vendor savedVendor = vendorRepository.save(vendor);
                    VendorDTO savedVendorDTO = vendorMapper.VendorToVendorDTO(savedVendor);
                    savedVendorDTO.setVendor_url(getVendorURL(id));

                    return savedVendorDTO;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendor(Long id) {
        vendorRepository.deleteById(id);
    }

    public static String getVendorURL(Long id){
        return "/shop/vendors/" + id;
    }
}
