package com.seaico.corebankingapplication.services;

import com.seaico.corebankingapplication.models.User;
import com.seaico.corebankingapplication.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private PinService pinService;

    public Optional<User> fetchUserByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User does not exist!")));
    }

    public User fetchUserByAccount(String accountNumber) {
        return userRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new EntityNotFoundException("User not Found"));
    }

    public String getCurrentPin(User user) {
        return pinService.getHashedPin(user);
    }

    public void saveUser(User user) {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
