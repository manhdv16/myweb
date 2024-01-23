package dvm.springbootweb.controller;

import dvm.springbootweb.dto.CommentDto;
import dvm.springbootweb.entity.*;
import dvm.springbootweb.jwt.JwtTokenProvider;
import dvm.springbootweb.payload.response.MessageResponse;
import dvm.springbootweb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDetailService detailService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/comments")
    public ResponseEntity<?> findAll() {
        List<Comment> listComments = commentService.findAll();
        return ResponseEntity.ok(listComments);
    }

    @GetMapping("/searchCommentByUser")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findAllByUserId(@RequestParam int userId) {
        Set<Comment> listComments = commentService.findAllCommentByUserId(userId);
        return new ResponseEntity<>(listComments, HttpStatus.OK);
    }

    @GetMapping("/searchCommentByBook/{id}")
    public ResponseEntity<?> findAllByBookId(@PathVariable int id) {
        Set<Comment> listComments = commentService.findAllCommentByBookId(id);
        return new ResponseEntity<>(listComments, HttpStatus.OK);
    }

    @PostMapping("/addComment")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addComment(@RequestHeader("Authorization") String token, @RequestBody CommentDto dto) {
        String userName = jwtTokenProvider.getUserNameFromJwt(token.substring(7));
        User user = userService.findByUserName(userName);
        Order order = orderService.findById(dto.getOrderId());
        List<OrderDetail> list = detailService.findAllOrderDetail(dto.getOrderId());
        for(OrderDetail d : list){
            Comment comment = new Comment();
            comment.setRating(dto.getRating());
            comment.setContent(dto.getContent());
            comment.setUser(user);
            comment.setBook(d.getBook());
            commentService.save(comment);
            System.out.println(d.getBook().getBookId());
        }
        order.setReviewed(true);
        orderService.update(order);
        return ResponseEntity.ok(new MessageResponse("Comment added successfully"));
    }

    @PutMapping("/updateComment/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestParam(required = false) String content) {
        Comment comment = commentService.findById(id);
        if (content != null) comment.setContent(content);
        commentService.save(comment);
        return ResponseEntity.ok(new MessageResponse("Content has been updated"));
    }

    @DeleteMapping("/deleteComment/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable int id) {
        commentService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Comment deleted"));
    }
}
