package project.carservice.web;

import project.carservice.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class HomeController {
    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView, HttpSession httpSession){
        modelAndView.setViewName("index");
        modelAndView.addObject("name",httpSession.getAttribute("application-name"));
        return modelAndView;
    }
    @GetMapping("/home")
    public ModelAndView home(ModelAndView modelAndView, @AuthenticationPrincipal Principal principal){
        modelAndView.setViewName("home");
        modelAndView.addObject("username",principal.getName());
        modelAndView.addObject("isAdmin",userService.isAdmin(principal.getName()));
        return modelAndView;
    }
}
