package za.ac.bheki97.testsecspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.ac.bheki97.testsecspring.entity.user.User;
import za.ac.bheki97.testsecspring.exception.UserExistsException;
import za.ac.bheki97.testsecspring.repository.UserRepository;

@Service
public class EventService {

    @Autowired
    private UserRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User addUser(User userInfo) throws UserExistsException {
        if(repository.existsById(userInfo.getIdNumber())){
            throw new UserExistsException("User with Id: "+userInfo.getIdNumber()+" Already Exists");
        }

        if (repository.existsByEmail(userInfo.getEmail())){
            throw new UserExistsException("User with email: "+userInfo.getEmail()+" Already Exists");
        }
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));

        return repository.save(userInfo);

    }


}
