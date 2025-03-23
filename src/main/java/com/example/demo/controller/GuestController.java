package com.example.demo.controller;

import com.example.demo.model.Booking;
import com.example.demo.repository.BookingRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class GuestController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/guest-dashboard")
    public String guestDashboard(Model model, Authentication authentication) {
        String username = authentication.getName();
        List<Booking> bookings = bookingRepository.findByGuestUsername(username);
        model.addAttribute("bookings", bookings);
        return "guest-dashboard";
    }
}

