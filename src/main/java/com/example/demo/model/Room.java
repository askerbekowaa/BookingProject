package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String amenities;
    private double pricePerNight;
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    public Room() {}

    public Room(String type, String amenities, double pricePerNight, String imagePath, Property property) {
        this.type = type;
        this.amenities = amenities;
        this.pricePerNight = pricePerNight;
        this.imagePath = imagePath;
        this.property = property;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getAmenities() { return amenities; }
    public void setAmenities(String amenities) { this.amenities = amenities; }

    public double getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(double pricePerNight) { this.pricePerNight = pricePerNight; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public Property getProperty() { return property; }
    public void setProperty(Property property) { this.property = property; }
}
