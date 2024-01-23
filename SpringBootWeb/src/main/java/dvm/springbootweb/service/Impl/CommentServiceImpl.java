package dvm.springbootweb.service.Impl;

import dvm.springbootweb.entity.Comment;
import dvm.springbootweb.repository.CommentReposirory;
import dvm.springbootweb.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentReposirory commentReposirory;
    @Override
    public List<Comment> findAll() {
        return commentReposirory.findAll();
    }

    @Override
    public void save(Comment comment) {
        commentReposirory.save(comment);
    }

    @Override
    public void delete(int id) {
        commentReposirory.deleteById(id);
    }

    @Override
    public Set<Comment> findAllCommentByUserId(int id) {
        return commentReposirory.findAllByUserId(id);
    }

    @Override
    public Set<Comment> findAllCommentByBookId(int id) {
        return commentReposirory.findAllByBookId(id);
    }

    @Override
    public Comment findById(int id) {
        return commentReposirory.findCommentByCommentId(id);
    }
}
