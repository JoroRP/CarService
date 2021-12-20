package project.carservice.controller;


import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import project.carservice.model.entity.Car;
import project.carservice.model.entity.UserEntity;
import project.carservice.repository.CarRepository;
import project.carservice.repository.UserRepository;
import project.carservice.web.CarController;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
public class CarControllerTest {

    @Autowired
    private CarController carController;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private UserRepository userRepository;

    @Mock
    private Principal principal;
    @Mock
    private Model model;
    @Mock
    private BindingResult bindingResult;

    @Test
    public void testAdd(){
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("email@gmail.com");
        userEntity.setPassword("12345");
        userEntity.setLastName("User");
        userEntity.setFirstName("User");
        userEntity.setRoles(new ArrayList<>());
        userEntity.setCars(new ArrayList<>());
        userEntity.setUsername("Username");

        UserEntity userEntity1 = this.userRepository.save(userEntity);

        String view=this.carController.addCar(userEntity1.getId(),model);
        Assert.assertEquals("add-car",view);
    }

    @Test
    public void testViewCars() {

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("email@gmail.com");
        userEntity.setPassword("12345");
        userEntity.setLastName("User");
        userEntity.setFirstName("User");
        userEntity.setRoles(new ArrayList<>());
        userEntity.setCars(new ArrayList<>());
        userEntity.setUsername("Username");

        UserEntity userEntity1 = this.userRepository.save(userEntity);

        LocalDate localDate = LocalDate.of(2017,1,13);
        Car car = new Car();
        car.setModel("audi");
        car.setLicensePlate("PB0000CH");
        car.setYear(localDate);
        car.setVisits(new ArrayList<>());
        car.setUser(userEntity1);

        Car carT = this.carRepository.save(car);

        String view = this.carController.viewCars(carT.getId(), model);
        Assert.assertEquals("view-car", view);

    }

}
