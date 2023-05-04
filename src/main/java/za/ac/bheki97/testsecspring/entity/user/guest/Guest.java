package za.ac.bheki97.testsecspring.entity.user.guest;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import za.ac.bheki97.testsecspring.entity.user.User;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Guest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "guest_id")
    private int guestId;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Temporal(value = TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "YYYY/mmm/dd HH:mm:ss")
    private LocalDateTime joindate;

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getJoindate() {
        return joindate;
    }

    public void setJoindate(LocalDateTime joindate) {
        this.joindate = joindate;
    }
}
