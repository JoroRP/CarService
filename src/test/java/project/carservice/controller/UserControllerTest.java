package project.carservice.controller;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.carservice.error.UserNotFoundException;
import project.carservice.model.binding.UserLoginBindingModel;
import project.carservice.model.binding.UserRegisterBindingModel;
import project.carservice.model.binding.VisitStatusBindingModel;
import project.carservice.model.entity.*;
import project.carservice.repository.CarRepository;
import project.carservice.repository.MechanicRepository;
import project.carservice.repository.UserRepository;
import project.carservice.repository.VisitRepository;
import project.carservice.web.UserController;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;


import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
public class UserControllerTest {

    @Autowired
    private UserController userController;
    @Autowired
    private HttpSession httpSession;
    @Mock
    private Principal principal;
    @Mock
    private Model model;
    @Mock
    private BindingResult bindingResult;
    @Mock
    private RedirectAttributes redirectAttributes;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private MechanicRepository mechanicRepository;

    @Test
    public void testRegister(){
        String view=this.userController.register(model);
        Assert.assertEquals("register",view);
    }


    @Test
    public void testRegisterConfirm(){
        UserRegisterBindingModel userRegisterBindingModel=new UserRegisterBindingModel();

        userRegisterBindingModel.setUsername("user");
        userRegisterBindingModel.setPassword("12345");
        userRegisterBindingModel.setConfirmPassword("12345");
        userRegisterBindingModel.setEmail("email@gmail.com");
        userRegisterBindingModel.setFirstName("User");
        userRegisterBindingModel.setLastName("User");


        String view=this.userController.registerConfirm(userRegisterBindingModel,bindingResult,httpSession,redirectAttributes,model);
        Assert.assertEquals("index",view);
    }

    @Test
    public void testRegisterConfirmToHaveAnError(){
        UserRegisterBindingModel userRegisterBindingModel=new UserRegisterBindingModel();
        userRegisterBindingModel.setUsername("user");
        userRegisterBindingModel.setPassword("123");
        userRegisterBindingModel.setConfirmPassword("123456");
        userRegisterBindingModel.setEmail(passwordEncoder.encode("email@gmail.com"));
        userRegisterBindingModel.setFirstName("User");
        userRegisterBindingModel.setLastName("User");


        String view=this.userController.registerConfirm(userRegisterBindingModel,bindingResult,httpSession,redirectAttributes,model);
        Assert.assertEquals("register",view);
    }

    @Test
    public void testLogin(){
        String view=this.userController.login(model);
        Assert.assertEquals("login",view);
    }

    @Test(expected = UserNotFoundException.class)
    public void testLoginConfirm(){

        UserEntity userEntity=new UserEntity();
        userEntity.setEmail("email@gmail.com");
        userEntity.setPassword("12345");
        userEntity.setLastName("User");
        userEntity.setFirstName("User");
        userEntity.setRoles(new ArrayList<>());
        userEntity.setCars(new ArrayList<>());
        userEntity.setUsername("Username");


        UserEntity userEntity1=this.userRepository.save(userEntity);

        UserLoginBindingModel userLoginBindingModel=new UserLoginBindingModel();
        userLoginBindingModel.setPassword(userEntity1.getPassword());
        userLoginBindingModel.setUsername("Not_User");
        String view=this.userController.loginConfirm(userLoginBindingModel,bindingResult,redirectAttributes);
        Assert.assertEquals("redirect:/user/profile",view);
    }

    @Test
    public void testLogout(){
        String view=this.userController.logout(httpSession);
        Assert.assertEquals("redirect:/",view);

    }

    @Test
    public void testProfile(){
        UserEntity userEntity=new UserEntity();
        userEntity.setEmail("email@gmail.com");
        userEntity.setPassword("12345");
        userEntity.setLastName("User");
        userEntity.setFirstName("User");
        userEntity.setRoles(new ArrayList<>());
        userEntity.setCars(new ArrayList<>());
        userEntity.setUsername("Username");


        UserEntity userEntity1=this.userRepository.save(userEntity);
        when(principal.getName()).thenReturn(userEntity1.getUsername());

        String view=this.userController.getFullStatistics(model,principal);
        Assert.assertEquals("profile",view);
    }

    @Test(expected = UserNotFoundException.class)
    public void testProfileToThrow(){
        UserEntity userEntity=new UserEntity();
        userEntity.setEmail("email@gmail.com");
        userEntity.setPassword("12345");
        userEntity.setLastName("User");
        userEntity.setFirstName("User");
        userEntity.setRoles(new ArrayList<>());
        userEntity.setCars(new ArrayList<>());
        userEntity.setUsername("Username");


        UserEntity userEntity1=this.userRepository.save(userEntity);
        when(principal.getName()).thenReturn("User");

        String view=this.userController.getFullStatistics(model,principal);
        Assert.assertEquals("profile",view);
    }

    @Test
    public void testAdmin(){
        UserEntity userEntity=new UserEntity();
        userEntity.setEmail("email@gmail.com");
        userEntity.setPassword("12345");
        userEntity.setLastName("User");
        userEntity.setFirstName("User");
        userEntity.setRoles(new ArrayList<>());
        userEntity.setCars(new ArrayList<>());
        userEntity.setUsername("Username");


        UserEntity userEntity1=this.userRepository.save(userEntity);
        when(principal.getName()).thenReturn(userEntity1.getUsername());

        String view=this.userController.getFullAdminStatistics(model,principal);
        Assert.assertEquals("admin",view);
    }

    @Test(expected = UserNotFoundException.class)
    public void testAdminToThrow(){
        UserEntity userEntity=new UserEntity();
        userEntity.setEmail("email@gmail.com");
        userEntity.setPassword("12345");
        userEntity.setLastName("User");
        userEntity.setFirstName("User");
        userEntity.setRoles(new ArrayList<>());
        userEntity.setCars(new ArrayList<>());
        userEntity.setUsername("Username");


        UserEntity userEntity1=this.userRepository.save(userEntity);
        when(principal.getName()).thenReturn("User");

        String view=this.userController.getFullAdminStatistics(model,principal);
        Assert.assertEquals("admin",view);
    }

    @Test
    public void testAdminJobs(){
        UserEntity userEntity=new UserEntity();
        userEntity.setEmail("email@gmail.com");
        userEntity.setPassword("12345");
        userEntity.setLastName("User");
        userEntity.setFirstName("User");
        userEntity.setRoles(new ArrayList<>());
        userEntity.setCars(new ArrayList<>());
        userEntity.setUsername("Username");


        UserEntity userEntity1=this.userRepository.save(userEntity);
        when(principal.getName()).thenReturn(userEntity1.getUsername());

        String view=this.userController.getFullAdminStatistics(model,principal);
        Assert.assertEquals("admin",view);
    }

    @Test(expected = UserNotFoundException.class)
    public void testAdminJobsToThrow(){
        UserEntity userEntity=new UserEntity();
        userEntity.setEmail("email@gmail.com");
        userEntity.setPassword("12345");
        userEntity.setLastName("User");
        userEntity.setFirstName("User");
        userEntity.setRoles(new ArrayList<>());
        userEntity.setCars(new ArrayList<>());
        userEntity.setUsername("Username");


        UserEntity userEntity1=this.userRepository.save(userEntity);
        when(principal.getName()).thenReturn("User");

        String view=this.userController.getFullAdminStatistics(model,principal);
        Assert.assertEquals("admin",view);
    }

    @Test
    public void testAdminArchive(){
        UserEntity userEntity=new UserEntity();
        userEntity.setEmail("email@gmail.com");
        userEntity.setPassword("12345");
        userEntity.setLastName("User");
        userEntity.setFirstName("User");
        userEntity.setRoles(new ArrayList<>());
        userEntity.setCars(new ArrayList<>());
        userEntity.setUsername("Username");


        UserEntity userEntity1=this.userRepository.save(userEntity);
        when(principal.getName()).thenReturn(userEntity1.getUsername());

        String view=this.userController.getFullAdminStatistics(model,principal);
        Assert.assertEquals("admin",view);
    }

    @Test(expected = UserNotFoundException.class)
    public void testAdminArchiveToThrow(){
        UserEntity userEntity=new UserEntity();
        userEntity.setEmail("email@gmail.com");
        userEntity.setPassword("12345");
        userEntity.setLastName("User");
        userEntity.setFirstName("User");
        userEntity.setRoles(new ArrayList<>());
        userEntity.setCars(new ArrayList<>());
        userEntity.setUsername("Username");


        UserEntity userEntity1=this.userRepository.save(userEntity);
        when(principal.getName()).thenReturn("User");

        String view=this.userController.getFullAdminStatistics(model,principal);
        Assert.assertEquals("admin",view);
    }

    @Test
    public void statusChange(){
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

        Visit visitT = this.visitRepository.save(visit);
        VisitStatusBindingModel visitStatusBindingModel = new VisitStatusBindingModel();
        String view = this.userController.statusChangeConfirm(visitT.getId(),model,visitStatusBindingModel,principal);
        Assert.assertEquals("redirect:/user/admin",view);
    }

}
