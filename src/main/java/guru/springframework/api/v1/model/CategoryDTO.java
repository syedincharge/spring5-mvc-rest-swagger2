package guru.springframework.api.v1.model;

import lombok.Data;

//Expose category DTO thrw the rest controller
//DTO : Data transfer objects
@Data
public class CategoryDTO {
    private Long id;
    private String name;
}
