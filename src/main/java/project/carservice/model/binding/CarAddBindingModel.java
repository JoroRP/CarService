package project.carservice.model.binding;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class CarAddBindingModel {
    private String model;
    private String licencePlate;
    private LocalDate year;

    public CarAddBindingModel() {
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getYear() {
        return year;
    }

    public void setYear(LocalDate year) {
        this.year = year;
    }
}
