package atelier.manichino.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import atelier.manichino.entity.Role;
import atelier.manichino.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
  
	List<User> findAllByOrderByIdAsc();
  
	Optional<User> findByRoles(Role roles);

//	Optional<User> findByActivationCode(String code);

	Optional<User> findByEmail(String email);

//	Optional<User> findByPasswordResetCode(String code);
  
}
