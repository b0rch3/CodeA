package atelier.manichino.controllers;

import atelier.manichino.configuration.GraphQLProvider;
import atelier.manichino.entity.Dress;
import atelier.manichino.mapper.DressMapper;
import atelier.manichino.mapper.OrderMapper;
import atelier.manichino.mapper.UserMapper;
import atelier.manichino.requests.DressRequest;
import atelier.manichino.requests.GraphQLRequest;
import atelier.manichino.requests.UserRequest;
import atelier.manichino.responses.DressResponse;
import atelier.manichino.responses.OrderResponse;
import atelier.manichino.responses.UserResponse;
import graphql.ExecutionResult;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
//import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;

//import javax.validation.Valid;
import java.util.List;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/manichino/admin")
public class AdminController {

	private final UserMapper userMapper;
    private final DressMapper dressMapper;
    private final OrderMapper orderMapper;
    private final GraphQLProvider graphQLProvider;

//    @PostMapping("/add")
//    public ResponseEntity<DressResponse> addDress(@RequestBody DressRequest dressRequest) {
//    	return ResponseEntity.ok(dressMapper.saveDress(dressRequest));
//    }
//    
//    @PutMapping("/edit")
//    public ResponseEntity<DressResponse> updateDress(@RequestBody DressRequest dressRequest) {
//        return ResponseEntity.ok(dressMapper.saveDress(dressRequest));
//    }
    
    @PostMapping("/add")
    public ResponseEntity<DressResponse> addDress(@RequestPart DressRequest dressRequest,
                                                      @RequestPart("dress") @Valid Dress dress,
                                                      BindingResult bindingResult) {
        return ResponseEntity.ok(dressMapper.saveDress(dressRequest, bindingResult));
    }

    @PutMapping("/edit")
    public ResponseEntity<DressResponse> updateDress(@RequestPart DressRequest dressRequest,
                                                         @RequestPart("dress") @Valid Dress dress,
                                                         BindingResult bindingResult) {
        return ResponseEntity.ok(dressMapper.saveDress(dressRequest, bindingResult));
    }

    @DeleteMapping("/delete/{dressId}")
    public ResponseEntity<List<DressResponse>> deleteDress(@PathVariable(value = "dressId") Long dressId) {
        return ResponseEntity.ok(dressMapper.deleteOrder(dressId));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderMapper.findAllOrders());
    }

    @PostMapping("/order")
    public ResponseEntity<List<OrderResponse>> getUserOrdersByEmail(@RequestBody UserRequest user) {
        return ResponseEntity.ok(orderMapper.findOrderByEmail(user.getEmail()));
    }

    @DeleteMapping("/order/delete/{orderId}")
    public ResponseEntity<List<OrderResponse>> deleteOrder(@PathVariable(value = "orderId") Long orderId) {
        return ResponseEntity.ok(orderMapper.deleteOrder(orderId));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(userMapper.findUserById(userId));
    }

    @GetMapping("/user/all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userMapper.findAllUsers());
    }

    @PostMapping("/graphql/user")
    public ResponseEntity<ExecutionResult> getUserByQuery(@RequestBody GraphQLRequest request) {
        return ResponseEntity.ok(graphQLProvider.getGraphQL().execute(request.getQuery()));
    }

    @PostMapping("/graphql/user/all")
    public ResponseEntity<ExecutionResult> getAllUsersByQuery(@RequestBody GraphQLRequest request) {
        return ResponseEntity.ok(graphQLProvider.getGraphQL().execute(request.getQuery()));
    }

    @PostMapping("/graphql/orders")
    public ResponseEntity<ExecutionResult> getAllOrdersQuery(@RequestBody GraphQLRequest request) {
        return ResponseEntity.ok(graphQLProvider.getGraphQL().execute(request.getQuery()));
    }

    @PostMapping("/graphql/order")
    public ResponseEntity<ExecutionResult> getUserOrdersByEmailQuery(@RequestBody GraphQLRequest request) {
        return ResponseEntity.ok(graphQLProvider.getGraphQL().execute(request.getQuery()));
    }
}
