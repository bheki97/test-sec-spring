package za.ac.bheki97.testsecspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.bheki97.testsecspring.entity.user.guest.Guest;

import java.util.List;

public interface GuestRepo extends JpaRepository<Guest,Integer> {

    boolean existsByAccount_IdNumber(String accId);

    boolean existsByEvent_EventKeyAndAccount_IdNumber(String eventKey,String accId);
    Guest findByEvent_EventKeyAndAccount_IdNumber(String eventKey,String accId);

    void deleteGuestByEvent_EventKeyAndAccount_IdNumber(String eventKey,String accId);



    List<Guest> findAllByAccount_IdNumber(String accId);

    List<Guest> findAllByEvent_EventKey(String eventKey);


}
