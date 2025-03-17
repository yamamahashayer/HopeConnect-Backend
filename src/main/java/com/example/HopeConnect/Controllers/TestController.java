package com.example.HopeConnect.Controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/W")
public class TestController {






    @GetMapping
    public String home() {
        return "Welcome to AdvanceSoft API!";
    }




}

