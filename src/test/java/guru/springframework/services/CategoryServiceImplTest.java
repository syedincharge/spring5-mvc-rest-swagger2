package guru.springframework.services;

import guru.springframework.api.v1.mapper.CategoryMapper;
import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.domain.Category;
import guru.springframework.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CategoryServiceImplTest {

    public static final String NAME = "Joe";
    CategoryServiceImpl categoryService;

    @Mock
    CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);

        categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE, categoryRepository);
    }

    @Test
    void getAllCategories() {

        List<Category> categoryList = Arrays.asList(new Category(), new Category(), new Category());

        when(categoryRepository.findAll()).thenReturn(categoryList);

        List<CategoryDTO> categoryDTOList = categoryService.getAllCategories();

        assertEquals(3, categoryDTOList.size());
    }

    @Test
    void getCategoryByName() {

        Category cat = new Category();
        cat.setId(1L);
        cat.setName(NAME);

        when(categoryRepository.findByLastName(anyString())).thenReturn(cat);

        CategoryDTO categoryDTO = categoryService.getCategoryByName(NAME);

        assertEquals(1L, categoryDTO.getId());
        assertEquals(NAME, categoryDTO.getName());
    }
}