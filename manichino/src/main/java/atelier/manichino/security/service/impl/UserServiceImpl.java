package atelier.manichino.security.service.impl;

import atelier.manichino.entity.Dress;
import atelier.manichino.entity.User;
import atelier.manichino.exceptions.ApiRequestException;
import atelier.manichino.repository.DressRepository;
import atelier.manichino.repository.UserRepository;
import atelier.manichino.security.service.UserService;
import graphql.schema.DataFetcher;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "*")
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DressRepository dressRepository;
//    private final ReviewRepository reviewRepository;

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiRequestException("User not found.", HttpStatus.NOT_FOUND)); // TODO add test
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiRequestException("Email not found.", HttpStatus.NOT_FOUND)); // TODO add test
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAllByOrderByIdAsc();
    }

    @Override
    public DataFetcher<User> getUserByQuery() {
        return dataFetchingEnvironment -> {
            Long userId = Long.parseLong(dataFetchingEnvironment.getArgument("id"));
            return userRepository.findById(userId).get();
        };
    }

    @Override
    public DataFetcher<List<User>> getAllUsersByQuery() {
        return dataFetchingEnvironment -> userRepository.findAllByOrderByIdAsc();
    }

    @Override
    public List<Dress> getCart(List<Long> dressIds) {
        return dressRepository.findByIdIn(dressIds);
    }

    @Override
    public User updateProfile(String email, User user) {
        User userFromDb = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiRequestException("Email not found.", HttpStatus.NOT_FOUND)); // TODO add test
        userFromDb.setFirstName(user.getFirstName());
        userFromDb.setLastName(user.getLastName());
        userFromDb.setCity(user.getCity());
        userFromDb.setAddress(user.getAddress());
        userFromDb.setPhoneNumber(user.getPhoneNumber());
        userFromDb.setPostIndex(user.getPostIndex());
        userRepository.save(userFromDb);
        return userFromDb;
    }

//    @Override
//    public Dress addReviewToDress(Review review, Long dressId) {
//    	Dress dress = dressRepository.getOne(dressId);
//        List<Review> reviews = dress.getReviews();
//        reviews.add(review);
//        double totalReviews = reviews.size();
//        double sumRating = reviews.stream().mapToInt(Review::getRating).sum();
//        dress.setDressRating(sumRating / totalReviews);
//        reviewRepository.save(review);
//        return dress;
//    }
}
