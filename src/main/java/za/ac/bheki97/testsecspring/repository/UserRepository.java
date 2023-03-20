package za.ac.bheki97.testsecspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.bheki97.testsecspring.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findByEmail(String username);

    boolean existsByEmail(String email);
}
