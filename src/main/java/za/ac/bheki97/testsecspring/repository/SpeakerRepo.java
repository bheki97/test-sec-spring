package za.ac.bheki97.testsecspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.bheki97.testsecspring.entity.user.speaker.Speaker;

public interface SpeakerRepo extends JpaRepository<Speaker,Integer> {
}
