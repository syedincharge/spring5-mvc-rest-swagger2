package guru.springframework.api.v1.model;

import guru.springframework.domain.Vendor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorListDTO {

    private List<VendorDTO> vendors;
}
