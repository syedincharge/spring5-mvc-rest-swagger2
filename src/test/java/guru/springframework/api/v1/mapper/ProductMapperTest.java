package guru.springframework.api.v1.mapper;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.api.v1.model.ProductDTO;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Category;
import guru.springframework.domain.Product;
import guru.springframework.domain.Vendor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductMapperTest {

    public static final String PRODUCT_NAME = "ProductName";
    public static final long ID = 1L;
    public static final String CAT_1 = "cat1";
    ProductMapper productMapper = ProductMapper.INSTANCE;

    @BeforeEach
    void setUp() {
    }

    @Test
    void productToProductDTO() {

        Product product = new Product();
        product.setId(ID);
        product.setName(PRODUCT_NAME);

        Category cat1 = Category.builder().id(ID).name(CAT_1).build();
        product.getCategories().add(cat1);

        ProductDTO productDTO = productMapper.productToProductDTO(product);

        assertEquals(ID, productDTO.getId());
        assertEquals(PRODUCT_NAME, productDTO.getName());
        assertEquals(1, productDTO.getCategories().size());
    }

    @Test
    void productDTOToProduct() {

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(ID);
        productDTO.setName(PRODUCT_NAME);
        CategoryDTO cat1 = CategoryDTO.builder().id(ID).name(CAT_1).build();
        productDTO.getCategories().add(cat1);

        Product product = productMapper.productDTOToProduct(productDTO);

        assertEquals(ID, product.getId());
        assertEquals(PRODUCT_NAME, product.getName());
        assertEquals(1, product.getCategories().size());
    }
}