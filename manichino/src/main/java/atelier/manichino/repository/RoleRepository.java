package atelier.manichino.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import atelier.manichino.entity.ERole;
import atelier.manichino.entity.Role;



@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
  Optional<Role> findByName(ERole name);
  
}
