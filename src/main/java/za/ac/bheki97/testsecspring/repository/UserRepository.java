package za.ac.bheki97.testsecspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.bheki97.testsecspring.entity.user.Account;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Account,String> {

    Optional<Account> findByEmail(String username);

    boolean existsByEmail(String email);
}
