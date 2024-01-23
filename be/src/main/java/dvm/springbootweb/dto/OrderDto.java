package dvm.springbootweb.dto;

import dvm.springbootweb.entity.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private List<Cart> listCart;
    private double totalCost;
}
