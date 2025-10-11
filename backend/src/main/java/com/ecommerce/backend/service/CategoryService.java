package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.CategoryDTO;
import com.ecommerce.backend.entity.Category;
import com.ecommerce.backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepo;
    public CategoryService(CategoryRepository categoryRepo) { this.categoryRepo = categoryRepo; }

    public List<CategoryDTO> getCategoryTree() {
        List<Category> all = categoryRepo.findAll();
        Map<Integer, CategoryDTO> dtoMap = new HashMap<>();
        for (Category c: all) {
            dtoMap.put(c.getId(), new CategoryDTO(c.getId(), c.getName(), new ArrayList<>()));
        }
        List<CategoryDTO> roots = new ArrayList<>();
        for (Category c: all) {
            CategoryDTO dto = dtoMap.get(c.getId());
            if (c.getParent() != null) {
                CategoryDTO parentDto = dtoMap.get(c.getParent().getId());
                if (parentDto != null) parentDto.getChildren().add(dto);
            } else {
                roots.add(dto);
            }
        }
        return roots;
    }

    public List<Integer> getDescendantCategoryIds(Integer categoryId) {
        List<Category> all = categoryRepo.findAll();
        Map<Integer, List<Integer>> childrenMap = new HashMap<>();
        for (Category c : all) {
            Integer pid = c.getParent() == null ? null : c.getParent().getId();
            if (pid != null) childrenMap.computeIfAbsent(pid, k -> new ArrayList<>()).add(c.getId());
        }
        List<Integer> res = new ArrayList<>();
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(categoryId);
        while (!stack.isEmpty()) {
            Integer cur = stack.pop();
            res.add(cur);
            List<Integer> ch = childrenMap.get(cur);
            if (ch != null) for (Integer kid : ch) stack.push(kid);
        }
        return res;
    }
}