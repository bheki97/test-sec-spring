package za.ac.bheki97.testsecspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import za.ac.bheki97.testsecspring.entity.user.speaker.Speaker;

import java.util.List;

public interface SpeakerRepo extends JpaRepository<Speaker,Integer> {

    List<Speaker> findAllByEvent_EventKey(String eventKey);
    @Modifying
    @Query("UPDATE Speaker s SET s.speech = :newSpeech WHERE s.guestId = :guestId")
    void updateSpeechById(Integer guestId, String newSpeech);

    @Query("SELECT s.speech FROM Speaker s WHERE s.guestId = :guestId")
    String getSpeechByGuestId(Integer guestId);
}
