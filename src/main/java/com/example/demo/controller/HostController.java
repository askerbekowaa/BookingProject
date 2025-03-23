package com.example.demo.controller;

import com.example.demo.model.Property;
import com.example.demo.repository.PropertyRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HostController {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/host-dashboard")
    public String hostDashboard(Model model, Authentication authentication) {
        String username = authentication.getName();
        List<Property> properties = propertyRepository.findByHostUsername(username);
        model.addAttribute("properties", properties);
        return "host-dashboard";
    }
}
