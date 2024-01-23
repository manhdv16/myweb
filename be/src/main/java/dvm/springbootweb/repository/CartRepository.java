package dvm.springbootweb.repository;

import dvm.springbootweb.entity.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query("select c from Cart c where c.user.userName=?1")
    List<Cart> findAllByUserName(String userName);

    @Query("select c from Cart c where c.book.bookId=?1")
    Cart findCartByBookId(int id);
    @Modifying
    @Transactional
    @Query("delete from Cart c where c.book.bookId=:bookId")
    void deleteCartByBookId(Integer bookId);
    @Modifying
    @Transactional
    @Query("delete from Cart c where c.user.userId=?1")
    void deleteAllByUserUserId(Integer userId);
}
