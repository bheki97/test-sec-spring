package za.ac.bheki97.testsecspring.dto;

import za.ac.bheki97.testsecspring.entity.user.Account;

public class AuthUserInfo {

    private Account account;
    private String jwtToken;


    public AuthUserInfo(Account account, String jwtToken) {
        this.account = account;
        this.jwtToken = jwtToken;
    }

    public AuthUserInfo() {
    }

    public Account getUser() {
        return account;
    }

    public void setUser(Account account) {
        this.account = account;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
