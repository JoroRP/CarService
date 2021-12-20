package project.carservice.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import project.carservice.model.entity.*;
import project.carservice.repository.CarRepository;
import project.carservice.repository.MechanicRepository;
import project.carservice.repository.UserRepository;
import project.carservice.repository.VisitRepository;
import project.carservice.web.VisitController;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
public class VisitControllerTest {

    @Autowired
    private VisitController visitController;
    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private MechanicRepository mechanicRepository;

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

        String view = this.visitController.addVisit(userEntity1.getId(),model);
        Assert.assertEquals("add-visit",view);
    }

    @Test
    public void testViewVisit(){

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

        Progress progress = Progress.AWAITING_VEHICLE;

        Visit visit = new Visit();
        visit.setCar(carT);
        visit.setDate(LocalDate.now());
        visit.setPrice(80.8);
        visit.setParts("sdasd");
        visit.setProgress(progress);
        visit.setUser(userEntity1);

        Visit visitT = this.visitRepository.save(visit);

        String view = this.visitController.viewVisit(visitT.getId(),model);
        Assert.assertEquals("view-visit", view);

    }

    @Test
    public void testViewAllVisits(){

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

        Progress progress = Progress.AWAITING_VEHICLE;
        Mechanic mechanic = new Mechanic();
        mechanic.setVisits(new ArrayList<>());
        mechanic.setName("Pesho");

        Mechanic mechanicT = this.mechanicRepository.save(mechanic);


        Visit visit = new Visit();
        visit.setCar(carT);
        visit.setDate(LocalDate.now());
        visit.setPrice(80.8);
        visit.setParts("sdasd");
        visit.setProgress(progress);
        visit.setUser(userEntity1);
        visit.setMechanic(mechanicT);

        Visit visit1 = new Visit();
        visit1.setCar(carT);
        visit1.setDate(LocalDate.now());
        visit1.setPrice(823.8);
        visit1.setParts("asdasdd");
        visit1.setProgress(progress);
        visit1.setUser(userEntity1);
        visit1.setMechanic(mechanicT);

        Visit visitT = this.visitRepository.save(visit);
        Visit visitT1 = this.visitRepository.save(visit1);

        String view = this.visitController.viewAllVisits(carT.getId(),model,principal);
        Assert.assertEquals("view-all-visits", view);

    }
}
