package dvm.springbootweb.repository;

import dvm.springbootweb.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface CommentReposirory extends JpaRepository<Comment,Integer> {
    @Query("select c from Comment c where c.user.userId = ?1")
    Set<Comment> findAllByUserId(int userId);
    @Query("select c from Comment c where c.book.bookId=?1")
    Set<Comment> findAllByBookId(int bookId);
    Comment findCommentByCommentId(int id);
}
