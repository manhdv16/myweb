package dvm.springbootweb.repository;

import dvm.springbootweb.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer> {
    @Query("select o from OrderDetail o where o.orderId =?1")
    List<OrderDetail> findAllByOrderId(int orderId);
}
