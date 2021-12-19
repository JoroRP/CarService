package project.carservice.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.ModelAndView;
import project.carservice.web.HomeController;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.security.Principal;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
public class HomeControllerTest {

    @Autowired
    private HomeController homeController;
    @Autowired
    private HttpSession httpSession;
    @Mock
    Principal principal;


    @Test
    public void testIndexMethod(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView=this.homeController.index(modelAndView,httpSession);
        Assert.assertEquals("index",modelAndView.getViewName());
    }
    @Test(expected = Exception.class)
    public void testHomeMethod(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView=this.homeController.home(modelAndView,principal);
        Assert.assertEquals("home",modelAndView.getViewName());
    }

}
