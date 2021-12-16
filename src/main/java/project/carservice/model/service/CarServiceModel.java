package project.carservice.model.service;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class CarServiceModel {
    private String id;
    private String model;
    private LocalDate year;
    private String licencePlate;
    private String username;

    public CarServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getYear() {
        return year;
    }

    public void setYear(LocalDate year) {
        this.year = year;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
