package za.ac.bheki97.testsecspring.entity.user.speaker;

import jakarta.persistence.*;
import za.ac.bheki97.testsecspring.entity.user.guest.Guest;

import java.io.Serializable;

@Entity
public class Speaker  extends Guest implements Serializable {


    private String speech;


    public Speaker() {
    }

    public static Speaker buildSpeaker(Guest guest){
        return (Speaker) guest;
    }




    public String getSpeech() {
        return speech;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
    }
}
