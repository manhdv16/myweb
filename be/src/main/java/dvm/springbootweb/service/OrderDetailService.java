package dvm.springbootweb.service;

import dvm.springbootweb.entity.Book;
import dvm.springbootweb.entity.OrderDetail;

import java.util.List;
import java.util.Set;

public interface OrderDetailService {
    List<OrderDetail> findAllOrderDetail(int orderId);
}
