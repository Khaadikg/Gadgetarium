package com.peaksoft.gadgetariumm5.service;

import com.peaksoft.gadgetariumm5.model.entity.User;
import com.peaksoft.gadgetariumm5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private UserRepository repository;
    private final BCryptPasswordEncoder encoder;

    {
        this.encoder = new BCryptPasswordEncoder();
    }

    public void sendSimpleMessage(int stringPinCode, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        String pinCode = String.valueOf(stringPinCode);
        message.setTo(email);
        message.setFrom("tairovasan11@gmail.com");
        message.setSubject("Password change");
        message.setText(pinCode);
        emailSender.send(message);

    }

    public int pinCode() {
        Random random = new Random();
        int pinCode = random.nextInt(1000, 9999);
        return pinCode;
    }

    public String checkPinCode(int pinCheck, String email, String password, String confirm) {
        if (!confirm.equals(password)) {
            return "NOT EQUALS";
        } else {
            User user = repository.findByEmail(email).get();
            if (pinCheck == user.getPinCode()) {
                user.setPassword(encoder.encode(password));
                repository.save(user);
                return "SUCCESS";
            }
        }
        return "FAIL";
    }

    public User resetPassword(String email) {
        User user = repository.findByEmail(email).get();
        user.setPinCode(pinCode());
        return repository.save(user);
    }
}
