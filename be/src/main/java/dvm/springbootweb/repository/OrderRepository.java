package dvm.springbootweb.repository;

import dvm.springbootweb.entity.Order;
import dvm.springbootweb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> getAllByUser(User user);
}
