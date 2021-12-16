package project.carservice.web;

import project.carservice.service.UserService;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

public abstract class CustomErrorController implements ErrorController {

    private final UserService userService;

    protected CustomErrorController(UserService userService) {
        this.userService = userService;
    }

    public String getErrorPath(Model model, ModelAndView modelAndView, @AuthenticationPrincipal Principal principal) {
        modelAndView.addObject("username",principal.getName());
        modelAndView.addObject("isAdmin",userService.isAdmin(principal.getName()));
        return "/error";
    }
}
