package com.tracker.expensetracker.service;

import com.tracker.expensetracker.dto.CategoryDTO;
import com.tracker.expensetracker.entity.Category;
import com.tracker.expensetracker.entity.TransactionType;
import com.tracker.expensetracker.exception.ResourceNotFoundException;
import com.tracker.expensetracker.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<CategoryDTO> getCategoriesByType(TransactionType type) {
        return categoryRepository.findByType(type).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return mapToDTO(category);
    }

    public CategoryDTO createCategory(CategoryDTO dto) {
        Category category = Category.builder()
                .name(dto.getName())
                .type(dto.getType())
                .colorHex(dto.getColorHex() != null ? dto.getColorHex() : "#4F46E5")
                .icon(dto.getIcon() != null ? dto.getIcon() : "folder")
                .build();
        Category saved = categoryRepository.save(category);
        return mapToDTO(saved);
    }

    public Category mapToEntity(CategoryDTO dto) {
        return Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .type(dto.getType())
                .colorHex(dto.getColorHex())
                .icon(dto.getIcon())
                .build();
    }

    public CategoryDTO mapToDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .type(category.getType())
                .colorHex(category.getColorHex())
                .icon(category.getIcon())
                .build();
    }
}
