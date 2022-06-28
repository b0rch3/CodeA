package atelier.manichino.controllers;

import atelier.manichino.configuration.GraphQLProvider;
import atelier.manichino.entity.Dress;
import atelier.manichino.entity.User;
import atelier.manichino.mapper.OrderMapper;
import atelier.manichino.mapper.UserMapper;
import atelier.manichino.requests.DressRequest;
import atelier.manichino.requests.GraphQLRequest;
import atelier.manichino.requests.OrderRequest;
import atelier.manichino.requests.UserRequest;
import atelier.manichino.responses.DressResponse;
import atelier.manichino.responses.OrderResponse;
import atelier.manichino.responses.UserResponse;
import atelier.manichino.security.service.impl.UserDetailsImpl;
import graphql.ExecutionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/manichino/users")
public class UserController {

    private final UserMapper userMapper;
    private final OrderMapper orderMapper;
    private final GraphQLProvider graphQLProvider;

    @GetMapping("/info/{email}")
    public ResponseEntity<UserResponse> getUserInfoByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(userMapper.findUserByEmail(email));
    }

    @PostMapping("/graphql/info")
    public ResponseEntity<ExecutionResult> getUserInfoByQuery(@RequestBody GraphQLRequest request) {
        return ResponseEntity.ok(graphQLProvider.getGraphQL().execute(request.getQuery()));
    }

    @PutMapping("/edit/{email}")
    public ResponseEntity<UserResponse> updateUserInfo(@PathVariable("email") String email,
    													@RequestBody UserRequest request,
    													BindingResult bindingResult) {
        return ResponseEntity.ok(userMapper.updateProfile(email, request, bindingResult));
    }

    @PostMapping("/cart")
    public ResponseEntity<List<DressResponse>> getCart(@RequestBody List<Long> dressesIds) {
        return ResponseEntity.ok(userMapper.getCart(dressesIds));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getUserOrders(@AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(orderMapper.findOrderByEmail(user.getEmail()));
    }

    @PostMapping("/graphql/orders")
    public ResponseEntity<ExecutionResult> getUserOrdersByQuery(@RequestBody GraphQLRequest request) {
        return ResponseEntity.ok(graphQLProvider.getGraphQL().execute(request.getQuery()));
    }

    @PostMapping("/order")
    public ResponseEntity<OrderResponse> postOrder(@Valid @RequestBody OrderRequest order, BindingResult bindingResult) {
        return ResponseEntity.ok(orderMapper.postOrder(order, bindingResult));
    }

}
