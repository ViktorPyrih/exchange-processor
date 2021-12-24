package ua.edu.cdu.vu.exchangeprocessor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/exchangeView")
    public String exchangeView() {
        return "exchange";
    }
}
