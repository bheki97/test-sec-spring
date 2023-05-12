package za.ac.bheki97.testsecspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import za.ac.bheki97.testsecspring.dto.AuthRequest;
import za.ac.bheki97.testsecspring.dto.AuthUserInfo;
import za.ac.bheki97.testsecspring.entity.user.Account;
import za.ac.bheki97.testsecspring.exception.UserExistsException;
import za.ac.bheki97.testsecspring.repository.UserRepository;
import za.ac.bheki97.testsecspring.service.AccountService;
import za.ac.bheki97.testsecspring.service.JwtService;

@RestController
public class HomeController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository repository;

    @Autowired
    private AccountService service;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/account")
    public Account addNewUser(@RequestBody Account accountInfo) throws UserExistsException {
        return service.addUser(accountInfo);
    }

    @PutMapping("/account/update")
    public AuthUserInfo updateAcc(@RequestBody Account account){
        String jwt  = jwtService.generateToken(account.getEmail());
        System.out.println(jwt);
        return new AuthUserInfo(repository.save(account),jwt);
    }

    @DeleteMapping("account/{id}")
    public String deleteUser(@PathVariable("id") String id){
        repository.deleteById(id);

        return "User Deleted Successfully";
    }

    @PostMapping("/authenticate")
    public AuthUserInfo authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        System.out.println("AUTHENTICATING USER: "+authRequest.getEmail());

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        if (authentication.isAuthenticated()) {

            String jwtToken = jwtService.generateToken(authRequest.getEmail());
            Account account = repository.findByEmail(authRequest.getEmail()).orElseThrow(() ->new UsernameNotFoundException("User Not Found"));

            System.out.println("Is Authenticated: "+ account.getFirstname()+" "+ account.getEmail());
            AuthUserInfo info = new AuthUserInfo(account,jwtToken);

            return info;
        } else {

            throw new UsernameNotFoundException("invalid user request!");
        }

    }
}
