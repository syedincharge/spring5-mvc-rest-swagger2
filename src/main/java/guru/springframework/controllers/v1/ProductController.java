package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.ProductDTO;
import guru.springframework.api.v1.model.ProductListDTO;
import guru.springframework.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ProductController.BASE_URL)
public class ProductController {

    public static final String BASE_URL = "/api/v1/products";

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ProductListDTO getAll(){
        return new ProductListDTO(productService.getAll());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO getById(@PathVariable Long id){
        return productService.findById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO postProduct(@RequestBody ProductDTO productDTO){
        return productService.postProduct(productDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO putProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO){
        return productService.putProduct(id, productDTO);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO patchProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO){
        return productService.patchProduct(id, productDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteByIdProduct(@PathVariable Long id){
        productService.deleteProductById(id);
    }



}
