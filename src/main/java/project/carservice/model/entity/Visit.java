package project.carservice.model.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Entity
@Table(name = "visits")
@Transactional
public class Visit extends BaseEntity{
    private LocalDate date;
    private Progress progress;
    private Double price;
    private UserEntity user;
    private Car car;
    private Mechanic mechanic;
    private String parts;



    public Visit() {
    }
    @Column(name="date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Enumerated(EnumType.STRING)
    public Progress getProgress() {
        return progress;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    @Column(name="price", nullable = false)
    @Min(value = 0, message = "Price can not be a negative number!")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    @ManyToOne
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @ManyToOne
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @ManyToOne
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
