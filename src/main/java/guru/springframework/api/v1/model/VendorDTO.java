package guru.springframework.api.v1.model;

import guru.springframework.domain.Product;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorDTO {

    private Long id;
    @ApiModelProperty(value = "Vendor name", required = true)
    private String name;
    @ApiModelProperty(value = "Vendor url", required = true)
    private String vendor_url;
    @ApiModelProperty(value = "Vendor product list", required = true)
    private Set<Product> products;
}
