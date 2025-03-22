package com.example.finance_tracker;

import com.example.finance_tracker.Controller.CategoryController;
import com.example.finance_tracker.Entity.Category;
import com.example.finance_tracker.Service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    public void testCreateCategory() throws Exception {
        Category category = new Category();
        category.setName("Food");

        when(categoryService.createCategory(Mockito.any(Category.class))).thenReturn(category);

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Food\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Food"));
    }

    @Test
    public void testCreateCategoryBadRequest() throws Exception {
        when(categoryService.createCategory(Mockito.any(Category.class)))
                .thenThrow(new IllegalArgumentException("Invalid category"));

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid category"));
    }

    @Test
    public void testGetAllCategories() throws Exception {
        Category category = new Category();
        category.setName("Food");
        when(categoryService.getAllCategories()).thenReturn(List.of(category));

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Food"));
    }

    @Test
    public void testUpdateCategory() throws Exception {
        Category updatedCategory = new Category();
        updatedCategory.setName("Updated Food");

        when(categoryService.updateCategory(Mockito.anyString(), Mockito.any(Category.class))).thenReturn(updatedCategory);

        mockMvc.perform(put("/api/categories/{categoryId}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Food\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Food"));
    }

    @Test
    public void testUpdateCategoryBadRequest() throws Exception {
        when(categoryService.updateCategory(Mockito.anyString(), Mockito.any(Category.class)))
                .thenThrow(new IllegalArgumentException("Invalid category update"));

        mockMvc.perform(put("/api/categories/{categoryId}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid category update"));
    }
}
