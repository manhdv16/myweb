package dvm.springbootweb.controller;

import dvm.springbootweb.cloudinary.CloudinaryService;
import dvm.springbootweb.entity.Book;
import dvm.springbootweb.entity.Category;
import dvm.springbootweb.payload.response.MessageResponse;
import dvm.springbootweb.service.BookService;
import dvm.springbootweb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@CrossOrigin("*")
@RestController
@RequestMapping("api/v1")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping("/books")
    public ResponseEntity<?> getAllBooks(){
        List<Book> listBooks = bookService.findAllBooks();
        return new ResponseEntity<>(listBooks, HttpStatus.OK);
    }
    @PostMapping("/addBook")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addBook(@RequestHeader("Authorization") String token, @RequestParam String bookName, @RequestParam String author,
                                     @RequestParam String description, @RequestParam(required = false) MultipartFile image,
                                     @RequestParam double price, @RequestParam int categoryId, @RequestParam int quantity) throws IOException {
        Book book = new Book();
        book.setBookName(bookName);
        book.setAuthor(author);
        book.setDescription(description);
        book.setPrice(price);
        if(image!= null){
            book.setImage(cloudinaryService.uploadFile(image));
        }
        book.setQuantity(quantity);
        Category category = categoryService.findById(categoryId);
        book.setCategory(category);
        bookService.saveOrUpdate(book);
        return ResponseEntity.ok(new MessageResponse("Book was added successfully!"));
    }

    @PutMapping("/updateBook/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> updateBookById(@PathVariable int id, @RequestParam(required = false) String bookName, @RequestParam(required = false) String author,
                                            @RequestParam(required = false) String description, @RequestParam(required = false) MultipartFile image,
                                            @RequestParam(required = false) Double price, @RequestParam(required = false) Integer categoryId, @RequestParam(required = false) Integer quantity ) throws IOException {
        Book book = bookService.findByBookId(id);
        if(bookName!= null) book.setBookName(bookName);
        if(description!= null) book.setDescription(description);
        if(image != null) book.setImage(cloudinaryService.uploadFile(image));
        if(author!= null) book.setAuthor(author);
        if(price != null) book.setPrice(price);
        if(categoryId != null) book.setCategory(categoryService.findById(categoryId));
        if(quantity != null) book.setQuantity(quantity);
        bookService.saveOrUpdate(book);
        return ResponseEntity.ok(new MessageResponse("Updated successfully!"));
    }

    @DeleteMapping("/deleteBook/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteBookById(@PathVariable int id){
        try{
            bookService.delete(id);
            return ResponseEntity.ok(new MessageResponse("Deleted successfully !"));
        }catch (Exception exception){
            return ResponseEntity.badRequest().body(new MessageResponse("Delete failed"));
        }

    }
}
