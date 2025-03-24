package com.example.demo.repository;

import com.example.demo.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByHostUsername(String username);
    List<Property> findByLocationContaining(String location);
}
