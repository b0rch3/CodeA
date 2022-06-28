package atelier.manichino.security.service;

import graphql.schema.DataFetcher;

import java.util.List;
import java.util.Map;

import atelier.manichino.entity.Order;

public interface OrderService {

    List<Order> findAll();

    List<Order> findOrderByEmail(String email);

    Order postOrder(Order validOrder, Map<Long, Long> dressesId);

    List<Order> deleteOrder(Long orderId);

    DataFetcher<List<Order>> getAllOrdersByQuery();

    DataFetcher<List<Order>> getUserOrdersByEmailQuery();
}
