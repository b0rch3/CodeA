package atelier.manichino.mapper;

import atelier.manichino.entity.User;
import atelier.manichino.exceptions.InputFieldException;
import atelier.manichino.requests.UserRequest;
import atelier.manichino.responses.DressResponse;
import atelier.manichino.responses.UserResponse;
import atelier.manichino.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "*")
@Component
@RequiredArgsConstructor
public class UserMapper {

    private final CommonMapper commonMapper;
    private final UserService userService;

    public UserResponse findUserById(Long Id) {
        return commonMapper.convertToResponse(userService.findUserById(Id), UserResponse.class);
    }

    public UserResponse findUserByEmail(String email) {
        return commonMapper.convertToResponse(userService.findUserByEmail(email), UserResponse.class);
    }

    public List<DressResponse> getCart(List<Long> dressesIds) {
        return commonMapper.convertToResponseList(userService.getCart(dressesIds), DressResponse.class);
    }

    public List<UserResponse> findAllUsers() {
        return commonMapper.convertToResponseList(userService.findAllUsers(), UserResponse.class);
    }

    public UserResponse updateProfile(String email, UserRequest userRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        User user = commonMapper.convertToEntity(userRequest, User.class);
        return commonMapper.convertToResponse(userService.updateProfile(email, user), UserResponse.class);
    }
    
//    public DressResponse addReviewToDress(ReviewRequest reviewRequest, Long dressId, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            throw new InputFieldException(bindingResult);
//        }
//        Review review = commonMapper.convertToEntity(reviewRequest, Review.class);
//        return commonMapper.convertToResponse(userService.addReviewToDress(review, dressId), DressResponse.class);
//    }

}
