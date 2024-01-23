package dvm.springbootweb.service.Impl;

import dvm.springbootweb.entity.Book;
import dvm.springbootweb.entity.OrderDetail;
import dvm.springbootweb.repository.OrderDetailRepository;
import dvm.springbootweb.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

@Autowired
private OrderDetailRepository detailRepository;
    @Override
    public List<OrderDetail> findAllOrderDetail(int orderId) {
        return detailRepository.findAllByOrderId(orderId);
    }
}
