package project.carservice.repository;

import project.carservice.model.entity.Mechanic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MechanicRepository extends JpaRepository<Mechanic,String> {
    List<Mechanic> findByName(String name);

}
