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
import za.ac.bheki97.testsecspring.entity.User;
import za.ac.bheki97.testsecspring.service.EventService;
import za.ac.bheki97.testsecspring.service.JwtService;

@RestController
public class HomeController {

    @Autowired
    private JwtService jwtService;

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

    @PostMapping("/new")
    public String addNewUser(@RequestBody User userInfo) {
        return service.addUser(userInfo);
    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getEmail());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }

    }
}
