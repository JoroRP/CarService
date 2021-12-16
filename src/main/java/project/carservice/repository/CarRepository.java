package project.carservice.repository;

import project.carservice.model.entity.Car;
import project.carservice.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car,String> {

    List<Car> findAllByUser_Username(String username);
    Car findCarByUserAndModel(UserEntity userEntity, String model);

    List<Car> findAll();
}
