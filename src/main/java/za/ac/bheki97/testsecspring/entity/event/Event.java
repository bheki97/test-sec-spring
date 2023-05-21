package za.ac.bheki97.testsecspring.entity.event;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import za.ac.bheki97.testsecspring.dto.CreateEventDto;
import za.ac.bheki97.testsecspring.entity.user.guest.Guest;
import za.ac.bheki97.testsecspring.entity.user.host.Host;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Event implements Serializable {

    @Id
    @Column(name = "event_key")
    private String eventKey;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "host_id")
    private Host host;

    @OneToMany(mappedBy = "event")
    private List<Guest> guests;
    private String occasion;
    private String description;

    @Column(name = "event_date_time")
    @DateTimeFormat(pattern = "YYYY/mmm/dd HH:mm:ss")
    private LocalDateTime date;

    public Event() {
    }

    public Event(CreateEventDto dto){
        this.date = dto.getLocalDateTime();
        this.eventKey = dto.getEventKey();
        this.host = dto.getHost();
        this.guests = new ArrayList<>();
        this.occasion = dto.getOccasion();
        this.description = dto.getDescription();
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

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventKey='" + eventKey + '\'' +
                ", host=" + host +
                ", occasion='" + occasion + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}
