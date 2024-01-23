package dvm.springbootweb.service;

import dvm.springbootweb.dto.CartDto;
import dvm.springbootweb.entity.Cart;
import dvm.springbootweb.entity.User;

import java.util.List;

public interface CartService {
    Cart AddToCart(CartDto cartDto, User user);
    List<Cart> getAllCartByUserName(String userName);
    List<Cart> getAllCart();
    void deleteCartByBookId(Integer bookId);
    void deleteAllCart(Integer userId);
}
