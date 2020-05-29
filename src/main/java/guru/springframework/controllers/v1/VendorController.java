package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.ProductListDTO;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.api.v1.model.VendorListDTO;
import guru.springframework.services.ProductService;
import guru.springframework.services.VendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(description = "This is my Vendor Controller")
@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {

    public static final String BASE_URL = "/api/v1/vendors";

    private final VendorService vendorService;
    private final ProductService productService;

    public VendorController(VendorService vendorService, ProductService productService) {
        this.vendorService = vendorService;
        this.productService = productService;
    }

    @ApiOperation(value = "Get list of vendors.")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO getAllVendors(){
        return new VendorListDTO(vendorService.getAll());
    }

    @ApiOperation(value = "Get specific vendor.")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getById(@PathVariable Long id){
        return vendorService.getById(id);
    }

    @ApiOperation(value = "Create new vendor.")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO){
        return vendorService.createVendor(vendorDTO);
    }

    @ApiOperation(value = "Update vendor.")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO){
        return vendorService.updateVendor(id, vendorDTO);
    }

    @ApiOperation(value = "Update partially vendor.")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO patchVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO){
        return vendorService.patchVendor(id, vendorDTO);
    }

    @ApiOperation(value = "Delete a vendor.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(@PathVariable Long id){
        vendorService.deleteVendor(id);
    }

    // Products
    @ApiOperation(value = "Get all vendor products")
    @GetMapping("/{id}/products")
    @ResponseStatus(HttpStatus.OK)
    public ProductListDTO getProductsByVendorId(@PathVariable Long id){
        return new ProductListDTO(vendorService.getProductByVendorId(id));
    }

}
