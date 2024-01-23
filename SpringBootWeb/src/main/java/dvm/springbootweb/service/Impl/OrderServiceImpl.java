package dvm.springbootweb.service.Impl;

import dvm.springbootweb.dto.OrderDto;
import dvm.springbootweb.entity.*;
import dvm.springbootweb.repository.BookRepository;
import dvm.springbootweb.repository.OrderDetailRepository;
import dvm.springbootweb.repository.OrderRepository;
import dvm.springbootweb.service.BookService;
import dvm.springbootweb.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository detailRepository;
    @Autowired
    BookService bookService;
    @Override
    public List<Order> getAllOrderByUser(User user) {
        return orderRepository.getAllByUser(user);
    }

    @Override
    public void save(OrderDto orderDto, User user) {
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setUser(user);
        order.setTotalAmount(orderDto.getTotalCost());
        order.setTotalBook(orderDto.getListCart().size());
        order.setImage(orderDto.getListCart().get(0).getBook().getImage());
        order.setStatus("PROCESSING");
        order.setReviewed(false);
        orderRepository.save(order);
        for (Cart c : orderDto.getListCart()) {
            OrderDetail detail = new OrderDetail();
            Book book = c.getBook();
            book.setSold(book.getSold() + c.getQuantity());
            detail.setBook(book);
            detail.setQuantity(c.getQuantity());
            detail.setOrderId(order.getOrderId());
            bookService.saveOrUpdate(book);
            detailRepository.save(detail);
        }
    }

    @Override
    public void update(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Order findById(int id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public void deleteOrder(int id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
