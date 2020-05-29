package guru.springframework.services;

import guru.springframework.api.v1.mapper.ProductMapper;
import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.ProductDTO;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;
    private final ProductMapper productMapper;

    public VendorServiceImpl(VendorMapper vendorMapper, VendorRepository vendorRepository, ProductMapper productMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
        this.productMapper = productMapper;
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

    @Override
    public List<ProductDTO> getProductByVendorId(Long id) {
        return vendorRepository.findById(id)
                .map(vendor -> {
                    return vendor.getProducts()
                            .stream()
                            .map(product -> {

                                ProductDTO productDTO = productMapper.productToProductDTO(product);
                                return productDTO;
                            }).collect(Collectors.toList());
                }).orElseThrow(ResourceNotFoundException::new);
    }

    public static String getVendorURL(Long id){
        return "/shop/vendors/" + id;
    }
}
