package za.ac.bheki97.testsecspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.ac.bheki97.testsecspring.entity.User;
import za.ac.bheki97.testsecspring.exception.UserExistsException;
import za.ac.bheki97.testsecspring.repository.UserRepository;

import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private UserRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public String addUser(User userInfo) throws UserExistsException {
        Optional<User> validateUser =  repository.findByEmail(userInfo.getEmail());

        if(validateUser==null){
            userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
            repository.save(userInfo);
        }else{
            throw new UserExistsException("The User with "+ userInfo.getEmail()+" already exists");
        }

        return "user added to system";
    }


}
