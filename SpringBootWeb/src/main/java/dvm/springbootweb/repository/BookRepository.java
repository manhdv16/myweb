package dvm.springbootweb.repository;

import dvm.springbootweb.dto.BookDto;
import dvm.springbootweb.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface BookRepository extends JpaRepository<Book,Integer> {
    @Query("select b from Book b where b.bookId =?1")
    Book findAllByBookId(Integer id);
    @Query("select b from Book b where b.category.categoryId =?1")
    Set<Book> findAllByCategoryId(int categoryId);
}