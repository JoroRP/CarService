package project.carservice.repository;

import project.carservice.model.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit,String> {

     List<Visit>findAllByCarId(String id);
     List<Visit>findAll();
}
