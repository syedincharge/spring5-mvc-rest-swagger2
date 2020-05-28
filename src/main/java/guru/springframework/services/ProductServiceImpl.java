package guru.springframework.services;

import guru.springframework.api.v1.mapper.ProductMapper;
import guru.springframework.api.v1.model.ProductDTO;
import guru.springframework.domain.Product;
import guru.springframework.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductMapper productMapper, ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public List<ProductDTO> getAll(){

        return productRepository.findAll()
                .stream()
                .map(product -> {
                    ProductDTO productDTO = productMapper.productToProductDTO(product);
                    productDTO.setProduct_url(getProductUrl(product.getId()));
                    return productDTO;
                }).collect(Collectors.toList());
    }

    @Override
    public ProductDTO findById(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    ProductDTO productDTO = productMapper.productToProductDTO(product);
                    productDTO.setProduct_url(getProductUrl(product.getId()));
                    return productDTO;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public ProductDTO postProduct(ProductDTO productDTO) {

        // clear the id
        productDTO.setId(null);

        Product product = productMapper.productDTOToProduct(productDTO);
        Product savedProduct = productRepository.save(product);
        ProductDTO savedProductDTO = productMapper.productToProductDTO(savedProduct);
        savedProductDTO.setProduct_url(getProductUrl(savedProduct.getId()));

        return savedProductDTO;
    }

    @Override
    public ProductDTO putProduct(Long id, ProductDTO productDTO) {

        return productRepository.findById(id)
                .map(product -> {
                    productDTO.setId(id);
                    Product productToSave = productMapper.productDTOToProduct(productDTO);
                    Product savedProduct = productRepository.save(productToSave);
                    ProductDTO returnedProduct = productMapper.productToProductDTO(savedProduct);
                    returnedProduct.setProduct_url(getProductUrl(product.getId()));
                    return returnedProduct;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public ProductDTO patchProduct(Long id, ProductDTO productDTO) {
        return productRepository.findById(id)
                .map(product -> {

                    if(productDTO.getName() != null){
                        product.setName(productDTO.getName());
                    }

                    Product savedProduct = productRepository.save(product);
                    ProductDTO returnedProduct = productMapper.productToProductDTO(savedProduct);
                    returnedProduct.setProduct_url(getProductUrl(product.getId()));
                    return returnedProduct;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDTO> findProductByVendorId(Long id) {
        return productRepository.findProductByVendorId(id)
                .stream()
                .map(product -> {
                    ProductDTO productDTO = productMapper.productToProductDTO(product);
                    return productDTO;
                }).collect(Collectors.toList());
    }

    public static String getProductUrl(Long id){
        return "/shop/products/" + id;
    }
}
