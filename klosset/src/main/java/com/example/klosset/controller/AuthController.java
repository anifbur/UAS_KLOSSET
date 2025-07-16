package com.example.klosset.controller;

import com.example.klosset.model.User;
import com.example.klosset.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

   //@GetMapping("/index")
    public String index() {
        return "index";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user,
                               @RequestParam("retypePassword") String retypePassword,
                               Model model) {

        if (!user.getPassword().equals(retypePassword)) {
            model.addAttribute("error", "Password dan konfirmasi tidak cocok!");
            return "register";
        }

        if (userRepository.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "Username sudah digunakan.");
            return "register";
        }

        if (userRepository.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Email sudah digunakan.");
            return "register";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "redirect:/login?registered";
    }
}
