package project.carservice.model.entity;


import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;


@Entity
@Table(name = "mechanics")
@Transactional
public class Mechanic extends BaseEntity{
    private String name;
    private List<Visit> visits;

    public Mechanic() {
    }
    @Column(name="name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name="mechanic_id")
    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }
}
