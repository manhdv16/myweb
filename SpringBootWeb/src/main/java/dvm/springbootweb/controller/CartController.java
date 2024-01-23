package dvm.springbootweb.controller;

import dvm.springbootweb.dto.CartDto;
import dvm.springbootweb.entity.Cart;
import dvm.springbootweb.entity.User;
import dvm.springbootweb.jwt.JwtTokenProvider;
import dvm.springbootweb.payload.response.MessageResponse;
import dvm.springbootweb.service.CartService;
import dvm.springbootweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1")
public class CartController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @PostMapping("/addToCart")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addToCart(@RequestBody CartDto cartDto,
                                       @RequestHeader("Authorization") String token) {
        String userName = jwtTokenProvider.getUserNameFromJwt(token.substring(7));
        System.out.println(userName);
        if(userName== null) return ResponseEntity.status(400).body(new MessageResponse("Lỗi token"));
        User user = userService.findByUserName(userName);
        Cart cart = cartService.AddToCart(cartDto, user);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/viewCart")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> viewCart(@RequestHeader("Authorization") String token) {
        String token1 = token.substring(7);
        String userName = jwtTokenProvider.getUserNameFromJwt(token1);
        List<Cart> listCart = cartService.getAllCartByUserName(userName);
        if (ObjectUtils.isEmpty(listCart)) {
            return ResponseEntity.ok(new MessageResponse("Cart Null"));
        }
        return ResponseEntity.ok(listCart);
    }

    @DeleteMapping("/deleteCart/{bookId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteCart(@PathVariable Integer bookId, @RequestHeader("Authorization") String token) {
        String userName = jwtTokenProvider.getUserNameFromJwt(token.substring(7));
        cartService.deleteCartByBookId(bookId);
        return ResponseEntity.ok(new MessageResponse("Delete Successfully"));
    }
}
