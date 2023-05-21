package za.ac.bheki97.testsecspring.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.bheki97.testsecspring.entity.event.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepo extends JpaRepository<Event,String> {


    boolean existsByHost_Account_IdNumberAndOccasion(String accountId,String occasion);
    boolean existsByHost_Account_IdNumberAndDate(String accId, LocalDateTime date);

    boolean existsByHost_HostId(int hostId);

    boolean existsByHost_Account_IdNumberAndEventKey(String hostId,String eventKey);

    List<Event> findAllByHost_Account_IdNumber(String hostId);


}
