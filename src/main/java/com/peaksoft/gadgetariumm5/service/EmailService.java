package com.peaksoft.gadgetariumm5.service;

import com.peaksoft.gadgetariumm5.model.entity.Order;
import com.peaksoft.gadgetariumm5.model.entity.User;
import com.peaksoft.gadgetariumm5.repository.OrderRepository;
import com.peaksoft.gadgetariumm5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.*;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private UserRepository repository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public void sendOrderMassage(String email, Long orderId) {
        User user = repository.findByEmail(email).get();
        for (int i = 0; i < user.getOrders().size(); i++) {
            Order order = user.getOrders().get(i);
            if (Objects.equals(orderId, order.getId())) {
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(email);
                mailMessage.setFrom("gadgetarium_application@gmail.com");
                mailMessage.setSubject("Good Order");
                String massage = "Номер заказа : " + order.getApplicationNumber() +
                        "\n Дата заказа : " + order.getCreated() +
                        "\n Статус заказа : " + order.getStatus();
                mailMessage.setText(massage);
                emailSender.send((mailMessage));
            }
        }
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
        return random.nextInt(1000, 9999);
    }

    public String checkPinCode(int pinCheck, String email, String password, String confirm) {
        if (!confirm.equals(password)) {
            // TODO: 14.07.2023 You have to throw modification exception 
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
