package atelier.manichino.security.service.impl;

import atelier.manichino.entity.Dress;
import atelier.manichino.entity.Order;
import atelier.manichino.entity.OrderItem;
import atelier.manichino.exceptions.ApiRequestException;
import atelier.manichino.repository.DressRepository;
import atelier.manichino.repository.OrderItemRepository;
import atelier.manichino.repository.OrderRepository;
//import atelier.manichino.security.service.MailSender;
import atelier.manichino.security.service.OrderService;
import graphql.schema.DataFetcher;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final DressRepository dressRepository;
//    private final MailSender mailSender;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAllByOrderByIdAsc();
    }

    @Override
    public DataFetcher<List<Order>> getAllOrdersByQuery() {
        return dataFetchingEnvironment -> orderRepository.findAllByOrderByIdAsc();
    }

    @Override
    public DataFetcher<List<Order>> getUserOrdersByEmailQuery() {
        return dataFetchingEnvironment -> {
            String email = dataFetchingEnvironment.getArgument("email").toString();
            return orderRepository.findOrderByEmail(email);
        };
    }

    @Override
    public List<Order> findOrderByEmail(String email) {
        return orderRepository.findOrderByEmail(email);
    }

    @Override
    @Transactional
    public Order postOrder(Order validOrder, Map<Long, Long> dressesId) {
        Order order = new Order();
        List<OrderItem> orderItemList = new ArrayList<>();

        for (Map.Entry<Long, Long> entry : dressesId.entrySet()) {
            Dress dress = dressRepository.findById(entry.getKey()).get();
            OrderItem orderItem = new OrderItem();
            orderItem.setDress(dress);
            orderItem.setAmount((dress.getPrice() * entry.getValue()));
//            orderItem.setQuantity(entry.getValue());
            orderItemList.add(orderItem);
            orderItemRepository.save(orderItem);
        }
        order.getOrderItems().addAll(orderItemList);
        order.setTotalPrice(validOrder.getTotalPrice());
        order.setFirstName(validOrder.getFirstName());
        order.setLastName(validOrder.getLastName());
        order.setCity(validOrder.getCity());
        order.setAddress(validOrder.getAddress());
        order.setPostIndex(validOrder.getPostIndex());
        order.setEmail(validOrder.getEmail());
        order.setPhoneNumber(validOrder.getPhoneNumber());
        orderRepository.save(order);

//        String subject = "Order #" + order.getId();
//        String template = "order-template";
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("order", order);
//        mailSender.sendMessageHtml(order.getEmail(), subject, template, attributes);
        return order;
    }

    @Override
    @Transactional
    public List<Order> deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ApiRequestException("Order not found.", HttpStatus.NOT_FOUND)); // TODO add test
        order.getOrderItems().forEach(orderItem -> orderItemRepository.deleteById(orderItem.getId()));
        orderRepository.delete(order);
        return orderRepository.findAllByOrderByIdAsc();
    }
}
