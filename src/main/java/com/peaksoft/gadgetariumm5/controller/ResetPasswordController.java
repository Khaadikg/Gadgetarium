package com.peaksoft.gadgetariumm5.controller;

import com.peaksoft.gadgetariumm5.model.entity.User;
import com.peaksoft.gadgetariumm5.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@Tag(name = "Forgot your password",description = "Reset password and change password")
public class ResetPasswordController {
    private final EmailService emailService;

    @Autowired
    public ResetPasswordController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/resetPassword")
    @Operation(summary = "",description = "")
    public String sendEmail(@RequestParam String email) {
        User user = emailService.resetPassword(email);
        emailService.sendSimpleMessage(user.getPinCode(), email);
        return user.getEmail();
    }

    @GetMapping("/changePassword")
    public String changePassword(@RequestParam int pinCode,
                                 @RequestParam String password,
                                 @RequestParam String passwordConfirm,
                                 @RequestParam String email) {
        return emailService.checkPinCode(pinCode, email, password, passwordConfirm);
    }
}
