package za.ac.bheki97.testsecspring.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import za.ac.bheki97.testsecspring.entity.user.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class UserEntityUserDetails implements UserDetails {

    private String email;
    private String password;
    Collection<GrantedAuthority> grantedAuthorities;


    public UserEntityUserDetails(User user){
        email = user.getEmail();
        password = user.getPassword();
        grantedAuthorities = Arrays.stream("ROLE_user".split("#"))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
