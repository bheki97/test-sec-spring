package za.ac.bheki97.testsecspring.entity.user.host;

import jakarta.persistence.*;
import za.ac.bheki97.testsecspring.entity.user.User;

import java.io.Serializable;

@Entity
public class Host implements Serializable {

    @Id
    @Column(name = "host_id")
    private int hostId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Host(){

    }

    public int getHostId() {
        return hostId;
    }

    public void setHostId(int hostId) {
        this.hostId = hostId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
