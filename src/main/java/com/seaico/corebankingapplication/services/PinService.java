package com.seaico.corebankingapplication.services;

import com.seaico.corebankingapplication.models.Pin;
import com.seaico.corebankingapplication.models.User;
import com.seaico.corebankingapplication.repositories.PinRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class PinService {
    @Autowired
    private PinRepository pinRepository;

    public boolean createPin(User user, String pin) {
        try {
            String hashedPin = BCrypt.hashpw(pin, BCrypt.gensalt(10));
            Pin newPin = new Pin(null, hashedPin, user, LocalDateTime.now());

            pinRepository.save(newPin);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public String getHashedPin(User userId) {
        List<Pin> pins = pinRepository.findAllByUserId(userId);
        if (!pins.isEmpty())
            return pins.get(0).getHashedPinCode();
        else
            return "";
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
