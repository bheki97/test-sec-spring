package za.ac.bheki97.testsecspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.bheki97.testsecspring.entity.user.host.Host;

public interface HostRepo extends JpaRepository<Host,Integer> {


    boolean existsByAccount_IdNumber(String accId);
}
