package project.carservice.service;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import project.carservice.error.UserNotFoundException;
import project.carservice.repository.UserRepository;

import javax.transaction.Transactional;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test(expected = UserNotFoundException.class)
    public void testFindByUsernameThrowingException(){
        String username="Username";
        userService.findUserByUsername(username);
    }

    @Test
    public void testWhetherUserIsAdminToReturnTrue(){
        boolean isAdmin=this.userService.isAdmin("admin");
        Assert.assertEquals(true,isAdmin);
    }



    @Test
    public void testIfUserExistToReturnTrue(){
        boolean doesExistUser=this.userService.existsUser("admin");
        Assert.assertEquals(true,doesExistUser);
    }
    @Test
    public void testIfUserExistToReturnFalse(){
        boolean doesExistUser=this.userService.existsUser("user");
        Assert.assertEquals(false,doesExistUser);
    }
    @Test
    public void testIfCreateAdminIsInvokedMoreThanOnce(){
        UserService userService=mock(UserService.class);
        verify(userService,times(0)).createAdmin();
    }

    @Test
    public void testCreateAdmin(){

           long count= this.userRepository.count();
            this.userService.createAdmin();

        Assert.assertEquals(count,this.userRepository.count());
    }

}
