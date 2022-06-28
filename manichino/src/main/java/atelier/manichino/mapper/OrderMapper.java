package atelier.manichino.mapper;

import atelier.manichino.entity.Order;
import atelier.manichino.exceptions.InputFieldException;
import atelier.manichino.requests.OrderRequest;
import atelier.manichino.responses.OrderResponse;
import atelier.manichino.security.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final CommonMapper commonMapper;
    private final OrderService orderService;

    public List<OrderResponse> findAllOrders() {
        return commonMapper.convertToResponseList(orderService.findAll(), OrderResponse.class);
    }

    public List<OrderResponse> findOrderByEmail(String email) {
        return commonMapper.convertToResponseList(orderService.findOrderByEmail(email), OrderResponse.class);
    }

    public List<OrderResponse> deleteOrder(Long orderId) {
        return commonMapper.convertToResponseList(orderService.deleteOrder(orderId), OrderResponse.class);
    }

    public OrderResponse postOrder(OrderRequest orderRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        Order order = orderService.postOrder(commonMapper.convertToEntity(orderRequest, Order.class), orderRequest.getDressesId());
        return commonMapper.convertToResponse(order, OrderResponse.class);
    }
}
