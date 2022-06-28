package atelier.manichino.controllers;

import atelier.manichino.configuration.GraphQLProvider;
import atelier.manichino.mapper.DressMapper;
import atelier.manichino.requests.DressSearchRequest;
import atelier.manichino.requests.GraphQLRequest;
import atelier.manichino.responses.DressResponse;
import graphql.ExecutionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/manichino/dresses")
public class DressController {

    private final DressMapper dressMapper;
    private final GraphQLProvider graphQLProvider;

    @GetMapping
    public ResponseEntity<List<DressResponse>> getAllDresses() {
        return ResponseEntity.ok(dressMapper.findAllDresses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DressResponse> getDress(@PathVariable("id") Long dressId) {
        return ResponseEntity.ok(dressMapper.findDressById(dressId));
    }

    @PostMapping("/ids")
    public ResponseEntity<List<DressResponse>> getDressesByIds(@RequestBody List<Long> dressesIds) {
        return ResponseEntity.ok(dressMapper.findDressesByIds(dressesIds));
    }

    @PostMapping("/search")
    public ResponseEntity<List<DressResponse>> findDressesByFilterParams(@RequestBody DressSearchRequest filter) {
    	return ResponseEntity.ok(dressMapper.filter(filter.getDressSizes(), filter.getDressTypes(), filter.getPrices(), filter.isSortByPrice()));
    }
    
    @PostMapping("/search/dressSize")
    public ResponseEntity<List<DressResponse>> findByDressSize(@RequestBody DressSearchRequest filter) {
    	return ResponseEntity.ok(dressMapper.findByDressSizeOrderByPriceDesc(filter.getDressSize()));
    }

    @PostMapping("/search/dressType")
    public ResponseEntity<List<DressResponse>> findByDressType(@RequestBody DressSearchRequest filter) {
        return ResponseEntity.ok(dressMapper.findByDressTypeOrderByPriceDesc(filter.getDressType()));
    }

    @PostMapping("/graphql/ids")
    public ResponseEntity<ExecutionResult> getDressesByIdsQuery(@RequestBody GraphQLRequest request) {
        return ResponseEntity.ok(graphQLProvider.getGraphQL().execute(request.getQuery()));
    }

    @PostMapping("/graphql/dresses")
    public ResponseEntity<ExecutionResult> getAllDressesByQuery(@RequestBody GraphQLRequest request) {
        return ResponseEntity.ok(graphQLProvider.getGraphQL().execute(request.getQuery()));
    }

    @PostMapping("/graphql/dress")
    public ResponseEntity<ExecutionResult> getDressByQuery(@RequestBody GraphQLRequest request) {
        return ResponseEntity.ok(graphQLProvider.getGraphQL().execute(request.getQuery()));
    }
}
