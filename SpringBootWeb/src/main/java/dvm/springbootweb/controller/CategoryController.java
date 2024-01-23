package dvm.springbootweb.controller;

import dvm.springbootweb.dto.CategoryDto;
import dvm.springbootweb.entity.Book;
import dvm.springbootweb.entity.Category;
import dvm.springbootweb.payload.response.MessageResponse;
import dvm.springbootweb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/booksOfCategory/{id}")
    public ResponseEntity<?> getAllBooks(@PathVariable int id){
        Set<Book> listBooks = categoryService.getAllBooks(id);
        System.out.println(listBooks);
        return ResponseEntity.ok(listBooks);
    }
    @PostMapping("/addCategory")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addCategory(@RequestBody CategoryDto dto) {
        Category category = new Category();
        category.setCategoryName(dto.getCategoryName());
        category.setDescription(dto.getDescription());
        categoryService.save(category);
        return ResponseEntity.ok(new MessageResponse("added successfully"));
    }

    @PutMapping("/updateCategory/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> update(@PathVariable int id, @RequestParam( required = false) String categoryName,
                                    @RequestParam( required = false) String description) {
        Category category = categoryService.findById(id);
        if (categoryName != null) category.setCategoryName(categoryName);
        if(description != null) category.setDescription(description);
        categoryService.save(category);
        return ResponseEntity.ok(new MessageResponse("updated successfully"));
    }

    @DeleteMapping("/deleteCategory/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable int id) {
        if(!categoryService.checkBookExist(id)) {
            categoryService.delete(id);
            return ResponseEntity.ok(new MessageResponse("deleted successfully"));
        }
        System.out.println("book exist");
        return ResponseEntity.badRequest().body(new MessageResponse("book exist"));
    }
}
