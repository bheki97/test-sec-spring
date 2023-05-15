package za.ac.bheki97.testsecspring.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import za.ac.bheki97.testsecspring.entity.event.Event;
import za.ac.bheki97.testsecspring.entity.user.guest.Guest;
import za.ac.bheki97.testsecspring.entity.user.host.Host;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CreateEventDto {

    private String eventKey;
    private Host host;
    private List<Guest> guests;
    private String occasion;
    private String description;
    private String date;

    public CreateEventDto() {
    }

    public CreateEventDto(Event event){
        this.occasion = event.getOccasion();
        this.description = event.getDescription();
        this.host = event.getHost();
        this.date = event.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        this.guests = event.getGuests();
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CreateEventDto{" +
                "eventKey='" + eventKey + '\'' +
                ", host=" + host +
                ", guests=" + guests +
                ", occasion='" + occasion + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public LocalDateTime getLocalDateTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
        return localDateTime;
    }
}
