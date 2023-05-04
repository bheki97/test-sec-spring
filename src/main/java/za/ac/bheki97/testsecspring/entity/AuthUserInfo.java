package za.ac.bheki97.testsecspring.entity;

import za.ac.bheki97.testsecspring.entity.user.User;

public class AuthUserInfo {

    private User user;
    private String jwtToken;

    public AuthUserInfo() {
    }

    public AuthUserInfo(User user, String jwtToken) {
        this.user = user;
        this.jwtToken = jwtToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
