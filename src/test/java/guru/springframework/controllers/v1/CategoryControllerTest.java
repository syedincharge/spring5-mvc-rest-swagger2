package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.domain.Category;
import guru.springframework.services.CategoryService;
import guru.springframework.services.ResourceNotFoundException;
import org.h2.security.auth.ConfigProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CategoryControllerTest {

    public static final String NAME = "Joe";

    @Mock
    CategoryService categoryService;

    @InjectMocks // use this instead of instantiate the controller
    CategoryController categoryController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);

        // replaced by @InjectMocks
        // categoryController = new CategoryController(categoryService);

        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler()) // add exception handler NotFound
                .build();
    }

    @Test
    public void testListCategories() throws Exception {

        CategoryDTO cat1 = new CategoryDTO();
        cat1.setId(1L);
        cat1.setName(NAME);

        CategoryDTO cat2 = new CategoryDTO();
        cat2.setId(1L);
        cat2.setName("Moe");

        List<CategoryDTO> categoryList = Arrays.asList(cat1, cat2);

        when(categoryService.getAllCategories()).thenReturn(categoryList);

        mockMvc.perform(MockMvcRequestBuilders.get(CategoryController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories", hasSize(2)));

        // inspect the jsonPath to have
        // $=root
        // element '.categories' should have 2 elements
    }

    @Test
    public void testGetCategoryByName() throws Exception {

        CategoryDTO cat1 = new CategoryDTO();
        cat1.setId(1L);
        cat1.setName(NAME);

        when(categoryService.getCategoryByName(anyString())).thenReturn(cat1);

        mockMvc.perform(MockMvcRequestBuilders.get(CategoryController.BASE_URL + "/" + NAME)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));

    }

    @Test
    public void testGetCategoryByNameNotFound() throws Exception {

        when(categoryService.getCategoryByName(anyString())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(CategoryController.BASE_URL + "/Foo")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}