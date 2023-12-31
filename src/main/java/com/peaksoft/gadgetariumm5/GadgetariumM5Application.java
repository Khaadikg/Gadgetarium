package com.peaksoft.gadgetariumm5;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@Tag(name = "Hoho",description = "hehe")
public class GadgetariumM5Application {

    public static void main(String[] args) {
        SpringApplication.run(GadgetariumM5Application.class, args);
        System.out.println("Welcome colleagues, project name is Gadgetarium-M5!");
    }
    @GetMapping("/")
    @Operation(summary = "Welcome",description = "Welcome to Gadgetarium-M5 Application!!!")
    public String greetingPage() {
        return "<h1>Welcome to Gadgetarium-M5 Application!!!<h1/>";
    }

}
