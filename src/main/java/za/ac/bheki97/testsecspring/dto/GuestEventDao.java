package za.ac.bheki97.testsecspring.dto;

import za.ac.bheki97.testsecspring.entity.user.host.Host;
import za.ac.bheki97.testsecspring.entity.user.speaker.Speaker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class GuestEventDao {

    private String eventKey;
    private Host host;
    private List<Speaker> speakers;
    private LocalDateTime date;
    private String occasion;
    private String description;

    public GuestEventDao() {
    }

    public GuestEventDao(String eventKey, Host host, List<Speaker> speakers, LocalDateTime date, String occasion, String description) {
        this.eventKey = eventKey;
        this.host = host;
        this.speakers = speakers;
        this.date = date;
        this.occasion = occasion;
        this.description = description;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public List<Speaker> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<Speaker> speakers) {
        this.speakers = speakers;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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
}
