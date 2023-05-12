package za.ac.bheki97.testsecspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.ac.bheki97.testsecspring.entity.user.Account;
import za.ac.bheki97.testsecspring.exception.UserExistsException;
import za.ac.bheki97.testsecspring.repository.UserRepository;

@Service
public class AccountService {

    @Autowired
    private UserRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Account addUser(Account accountInfo) throws UserExistsException {
        if(repository.existsById(accountInfo.getIdNumber())){
            throw new UserExistsException("User with Id: "+ accountInfo.getIdNumber()+" Already Exists");
        }

        if (repository.existsByEmail(accountInfo.getEmail())){
            throw new UserExistsException("User with email: "+ accountInfo.getEmail()+" Already Exists");
        }
        accountInfo.setPassword(passwordEncoder.encode(accountInfo.getPassword()));

        return repository.save(accountInfo);

    }


}
