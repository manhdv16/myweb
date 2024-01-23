package dvm.springbootweb.repository;

import dvm.springbootweb.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    Category findByCategoryId(int id);
    @Query("select count(b) from Book b where b.category.categoryId = ?1")
    int existBookByCategoryId(int id);
}
