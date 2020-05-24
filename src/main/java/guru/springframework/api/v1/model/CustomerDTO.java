package guru.springframework.api.v1.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDTO {
    Long id;
    String firstName;
    String lastName;
    String customer_url;
}
