package project.carservice.model.binding;

import project.carservice.model.entity.Progress;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

public class VisitStatusBindingModel {
    private LocalDate date;
    private Progress progress;
    private Double price;
    private String parts;


    public VisitStatusBindingModel() {
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

    public String getParts() {
        return parts;
    }

    public void setParts(String parts) {
        this.parts = parts;
    }

}
