package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.api.v1.model.CategoryListDTO;
import guru.springframework.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {

    public static final String BASE_URL = "/api/v1/categories";

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public CategoryListDTO getAll(){

        List<CategoryDTO> categoryDTOList = categoryService.getAllCategories();
        return new CategoryListDTO(categoryDTOList);
    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO findByName(@PathVariable String name){

        CategoryDTO categoryDTO = categoryService.getCategoryByName(name);
        return categoryDTO;
    }

}
