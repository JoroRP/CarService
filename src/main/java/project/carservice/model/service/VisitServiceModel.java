package project.carservice.model.service;

import project.carservice.model.entity.Car;
import project.carservice.model.entity.Mechanic;
import project.carservice.model.entity.Progress;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class VisitServiceModel {
    private LocalDate date;
    private Progress progress;
    private Double price;
    private Car car;
    private Mechanic mechanic;
    private String parts;

    public VisitServiceModel() {
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Progress getProgress() {
        return progress;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public void setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }

    public String getParts() {
        return parts;
    }

    public void setParts(String parts) {
        this.parts = parts;
    }
}
