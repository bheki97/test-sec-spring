package za.ac.bheki97.testsecspring.entity.user.speaker;

import jakarta.persistence.*;
import za.ac.bheki97.testsecspring.entity.user.User;

import java.io.Serializable;

@Entity
public class Speaker  implements Serializable {

    @Id
    private int speakerId;
    private String homeLaguageCode;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private String speech;


    public Speaker() {
    }

    public String getHomeLaguageCode() {
        return homeLaguageCode;
    }

    public void setHomeLaguageCode(String homeLaguageCode) {
        this.homeLaguageCode = homeLaguageCode;
    }

    public String getSpeech() {
        return speech;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
    }
}
