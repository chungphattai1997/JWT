package phattai.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import phattai.model.Category;
import phattai.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	CategoryRepository categoryRepository;

	// Find all category
	public Iterable<Category> findAll() {
		return categoryRepository.findAll();
	}

	// Find category by Id
	public Category findById(long id) {
		Optional<Category> op = categoryRepository.findById(id);
		if (!op.isPresent()) {
			return null;
		}
		return op.get();
	}

	// Add or update category
	public void save(Category category) {
		categoryRepository.save(category);
	}

	// Delete category
	public void delete(long id) {
		categoryRepository.deleteById(id);
	}
}
