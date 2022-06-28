package atelier.manichino.security.service;

import atelier.manichino.entity.Dress;
import atelier.manichino.entity.User;
import graphql.schema.DataFetcher;

import java.util.List;

public interface UserService {

    User findUserById(Long Id);

    User findUserByEmail(String email);

    DataFetcher<List<User>> getAllUsersByQuery();

    DataFetcher<User> getUserByQuery();

    List<User> findAllUsers();

    List<Dress> getCart(List<Long> dressIds);

    User updateProfile(String email, User user);

//	Dress addReviewToDress(Review review, Long dressId);
}
