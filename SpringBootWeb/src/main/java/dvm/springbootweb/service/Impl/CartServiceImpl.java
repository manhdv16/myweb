package dvm.springbootweb.service.Impl;

import dvm.springbootweb.dto.CartDto;
import dvm.springbootweb.entity.Cart;
import dvm.springbootweb.entity.User;
import dvm.springbootweb.repository.BookRepository;
import dvm.springbootweb.repository.CartRepository;
import dvm.springbootweb.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BookRepository bookRepository;
    @Override
    public Cart AddToCart(CartDto cartDto, User user) {
        Cart cart =null;
        cart = cartRepository.findCartByBookId(cartDto.getBookId());
        if(cart != null){
            cart.setQuantity(cart.getQuantity()+cartDto.getQuantity());
            cartRepository.save(cart);
            return cart;
        }
        cart = new Cart();
        cart.setBook(bookRepository.findById(cartDto.getBookId()).get());
        cart.setUser(user);
        cart.setQuantity(cartDto.getQuantity());
        cartRepository.save(cart);
        return cart;
    }

    @Override
    public List<Cart> getAllCartByUserName(String userName) {
        List<Cart> listCart = cartRepository.findAllByUserName(userName);
        return listCart;
    }

    @Override
    public List<Cart> getAllCart() {
        return cartRepository.findAll();
    }

    @Override
    public void deleteCartByBookId(Integer bookId) {
        cartRepository.deleteCartByBookId(bookId);
    }

    @Override
    public void deleteAllCart(Integer userId) {
        cartRepository.deleteAllByUserUserId(userId);
    }
}
