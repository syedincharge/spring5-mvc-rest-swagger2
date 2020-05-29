package guru.springframework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    Long id;
    @ApiModelProperty(value = "This is the first name", required = true)
    String firstName;
    @ApiModelProperty(value = "This is the last name", required = true)
    String lastName;

    @JsonProperty("customer_url")
    String customer_url;
}
