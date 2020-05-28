package guru.springframework.api.v1.model;

import guru.springframework.domain.Category;
import guru.springframework.domain.Vendor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    private String name;

    private String product_url;

    private Set<CategoryDTO> categories = new HashSet<>();

    private Long vendorId;
}
