package za.ac.bheki97.testsecspring.entity.event;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import za.ac.bheki97.testsecspring.entity.user.guest.Guest;
import za.ac.bheki97.testsecspring.entity.user.programdir.MasterOfCeremony;
import za.ac.bheki97.testsecspring.entity.user.speaker.Speaker;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Event implements Serializable {

    @Id
    @Column(name = "event_key")
    private String eventKey;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private MasterOfCeremony mc;
    @OneToMany(targetEntity = Guest.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "identity_num")
    private List<Speaker> speakers;
    @OneToMany(targetEntity = Guest.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "guest_id")
    private List<Guest> guests;
    private String occasion;
    private String description;

    @Column(name = "event_date_time")
    @DateTimeFormat(pattern = "YYYY/mmm/dd HH:mm:ss")
    private LocalDateTime date;

    public Event() {
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }


    public MasterOfCeremony getMc() {
        return mc;
    }

    public void setMc(MasterOfCeremony mc) {
        this.mc = mc;
    }

    public List<Speaker> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<Speaker> speakers) {
        this.speakers = speakers;
    }

    public List<Guest> getGuests() {
        return guests;
    }

    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }

    public String getOccasion() {
        return occasion;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventKey='" + eventKey + '\'' +
                ", mc=" + mc.toString() +
                ", speakers=" + speakers.toString()+
                ", guests=" + guests.toString() +
                ", occasion='" + occasion + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
