package project.carservice.model.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "cars")
@Transactional
public class Car extends BaseEntity {
    private String model;
    private LocalDate year;
    private String licensePlate;
    private UserEntity user;
    private List<Visit> visits;

    public Car() {
    }
    @Column(name = "model", nullable = false)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    @Column(name = "year",nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getYear() {
        return year;
    }

    public void setYear(LocalDate year) {
        this.year = year;
    }
    @Column(name = "licence_plate", nullable = false, unique = true)
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
    @ManyToOne
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "car_id")
    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }
}
