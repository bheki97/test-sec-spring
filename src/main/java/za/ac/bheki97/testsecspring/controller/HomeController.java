package za.ac.bheki97.testsecspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import za.ac.bheki97.testsecspring.dto.AuthRequest;
import za.ac.bheki97.testsecspring.dto.AuthUserInfo;
import za.ac.bheki97.testsecspring.entity.User;
import za.ac.bheki97.testsecspring.repository.UserRepository;
import za.ac.bheki97.testsecspring.service.EventService;
import za.ac.bheki97.testsecspring.service.JwtService;

@RestController
public class HomeController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository repository;

    @Autowired
    private EventService service;

    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping("/home")
    public String getHome(){

        return "Hello World!!!!";
    }

    @GetMapping("/admin")
    public String adminHome(){

        return "Logged In User";
    }

    @PostMapping("/new-account")
    public String addNewUser(@RequestBody User userInfo) {
        return service.addUser(userInfo);
    }

    @PostMapping("/authenticate")
    public AuthUserInfo authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        System.out.println("AUTHENTICATING USER: "+authRequest.getEmail());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {

            String jwtToken = jwtService.generateToken(authRequest.getEmail());
            User user = repository.findByEmail(authRequest.getEmail()).orElseThrow(() ->new UsernameNotFoundException("User Not Found"));
            System.out.println("Is Authenticated: "+ jwtToken);
            AuthUserInfo info = new AuthUserInfo(user,jwtToken);

            return info;
        } else {

            throw new UsernameNotFoundException("invalid user request !");
        }

    }
}
