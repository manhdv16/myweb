package dvm.springbootweb.service.Impl;

import dvm.springbootweb.entity.Book;
import dvm.springbootweb.entity.Category;
import dvm.springbootweb.repository.CategoryRepository;
import dvm.springbootweb.service.BookService;
import dvm.springbootweb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BookService bookService;
    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(int id) {
        return categoryRepository.findByCategoryId(id);
    }

    @Override
    public Set<Book> getAllBooks(int id) {
        return bookService.findAllByCategoryId(id);
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void delete(int id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public boolean checkBookExist(int id) {
        return categoryRepository.existBookByCategoryId(id)>0;
    }
}
