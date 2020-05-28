package guru.springframework.services;

import guru.springframework.api.v1.model.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getAll();

    ProductDTO findById(Long id);

    ProductDTO postProduct(ProductDTO productDTO);

    ProductDTO putProduct(Long id, ProductDTO productDTO);

    ProductDTO patchProduct(Long id, ProductDTO productDTO);

    void deleteProductById(Long id);
}
