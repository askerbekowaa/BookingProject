package com.example.demo.controller;

import com.example.demo.model.Property;
import com.example.demo.model.Room;
import com.example.demo.repository.PropertyRepository;
import com.example.demo.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    private static final String UPLOAD_DIR = "uploads";

    @GetMapping("/add-room/{propertyId}")
    public String showAddRoomForm(@PathVariable Long propertyId, Model model) {
        model.addAttribute("room", new Room());
        model.addAttribute("propertyId", propertyId);
        return "add-room";
    }
    @GetMapping("/delete-room/{roomId}")
    public String deleteRoom(@PathVariable Long roomId, Authentication authentication) {
        var room = roomRepository.findById(roomId).orElse(null);

        if (room == null || !room.getProperty().getHost().getUsername().equals(authentication.getName())) {
            return "redirect:/error";
        }

        roomRepository.deleteById(roomId);
        return "redirect:/host-dashboard";
    }


    @PostMapping("/add-room/{propertyId}")
    public String addRoom(@PathVariable Long propertyId,
                          @ModelAttribute Room room,
                          @RequestParam("image") MultipartFile image,
                          Model model) {
        try {
            Property property = propertyRepository.findById(propertyId).orElse(null);
            if (property == null) {
                return "redirect:/error";
            }

            if (!image.isEmpty()) {
                String contentType = image.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    model.addAttribute("error", "Загрузите изображение.");
                    return "add-room";
                }

                String projectRoot = System.getProperty("user.dir");
                Path uploadPath = Paths.get(projectRoot, UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);
                image.transferTo(filePath.toFile());
                room.setImagePath("/uploads/" + fileName);
            }

            room.setProperty(property);
            roomRepository.save(room);
            return "redirect:/host-dashboard";
        } catch (IOException e) {
            model.addAttribute("error", "Ошибка загрузки изображения.");
            return "add-room";
        }
    }
}
