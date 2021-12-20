package project.carservice.web;

import project.carservice.model.binding.UserLoginBindingModel;
import project.carservice.model.binding.UserRegisterBindingModel;
import project.carservice.model.binding.VisitStatusBindingModel;
import project.carservice.model.service.UserDetailsServiceModel;
import project.carservice.model.service.UserServiceModel;
import project.carservice.model.service.VisitServiceModel;
import project.carservice.model.view.VisitViewModel;
import project.carservice.service.CarService;
import project.carservice.service.UserService;
import project.carservice.service.VisitService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final CarService carService;
    private final VisitService visitService;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, CarService carService, VisitService visitService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.carService = carService;
        this.visitService = visitService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        if (!model.containsAttribute("userRegisterBindingModel")) {
            model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid @ModelAttribute("userRegisterBindingModel") UserRegisterBindingModel userRegisterBindingModel,
                                  BindingResult bindingResult, HttpSession httpSession, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors() || !
                userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.
                    addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel",
                            bindingResult);
            return "register";
        }
        if(userService.existsUser(userRegisterBindingModel.getUsername())){
            bindingResult.rejectValue
                    ("username","error.username","An account with this username already exists.");
            return "register";
        }
        UserServiceModel userServiceModel=this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class);
        this.userService.createAndLoginUser(userServiceModel);


        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        if (!model.containsAttribute("userLoginBindingModel")) {
            model.addAttribute("userLoginBindingModel", new UserLoginBindingModel());
        }
        return "login";
    }

    @PostMapping("/login")
    public String loginConfirm(@Valid @ModelAttribute("userLoginBindingModel")
                                       UserLoginBindingModel userLoginBindingModel,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel",
                    bindingResult);
            return "login";
        }
        UserDetailsServiceModel user=this.userService.findUserByUsername(userLoginBindingModel.getUsername());
        if (user == null) {
            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel",
                    bindingResult);
            return "login";
        }


        this.userService.loginUser(user.getUsername(),userLoginBindingModel.getPassword());
        if(this.userService.isAdmin(user.getUsername())){
            return "redirect:/user/admin";
        }
        else {
            return "redirect:/user/profile";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/";
    }


    @GetMapping("/profile")
    public String getFullStatistics(Model model, @AuthenticationPrincipal Principal principal){
        model.addAttribute("username",principal.getName());
        model.addAttribute("user",this.userService.getUser(principal.getName()));
        model.addAttribute("roles",this.userService.getUser(principal.getName()).getRoles());
        model.addAttribute("carList",this.carService.listOfAllCars(principal.getName()));
        return "profile";
    }

    @GetMapping("/admin")
    public String getFullAdminStatistics(Model model, @AuthenticationPrincipal Principal principal){

        model.addAttribute("username",principal.getName());
        model.addAttribute("user",this.userService.getUser(principal.getName()));
        model.addAttribute("roles",this.userService.getUser(principal.getName()).getRoles());
        model.addAttribute("carList",this.carService.listOfCars(principal.getName()));
        model.addAttribute("visitList", this.visitService.findVisits(principal.getName()));
        model.addAttribute("visitStatusBinding", new VisitStatusBindingModel());
        return "admin";
    }

    @GetMapping("/ongoing-jobs")
    public String getFullAdminJobs(Model model, @AuthenticationPrincipal Principal principal){

        model.addAttribute("username",principal.getName());
        model.addAttribute("user",this.userService.getUser(principal.getName()));
        model.addAttribute("roles",this.userService.getUser(principal.getName()).getRoles());
        model.addAttribute("carList",this.carService.listOfCars(principal.getName()));
        model.addAttribute("visitList", this.visitService.findVisits(principal.getName()));
        model.addAttribute("visitStatusBinding", new VisitStatusBindingModel());
        return "ongoing-jobs";
    }

    @GetMapping("/archive")
    public String getFullAdminArchive(Model model, @AuthenticationPrincipal Principal principal){

        model.addAttribute("username",principal.getName());
        model.addAttribute("user",this.userService.getUser(principal.getName()));
        model.addAttribute("roles",this.userService.getUser(principal.getName()).getRoles());
        model.addAttribute("carList",this.carService.listOfCars(principal.getName()));
        model.addAttribute("visitList", this.visitService.findVisits(principal.getName()));
        model.addAttribute("visitStatusBinding", new VisitStatusBindingModel());
        return "archive";
    }

    @PostMapping("/status-change")
    public String statusChangeConfirm(@RequestParam("id")String id, Model model,
                               @Valid @ModelAttribute("visitStatusBindingModel")
                               VisitStatusBindingModel visitStatusBindingModel,
                               @AuthenticationPrincipal Principal principal){
        VisitServiceModel visitServiceModel = this.visitService.visitStatusChange(id,visitStatusBindingModel);
        VisitViewModel visitViewModel=this.modelMapper.map(visitServiceModel,VisitViewModel.class);

        return "redirect:/user/admin";
    }

    @PostMapping("/status-change-ongoing")
    public String statusChangeConfirmOngoing(@RequestParam("id")String id, Model model,
                               @Valid @ModelAttribute("visitStatusBindingModel")
                                       VisitStatusBindingModel visitStatusBindingModel,
                               @AuthenticationPrincipal Principal principal){
        VisitServiceModel visitServiceModel = this.visitService.visitStatusChange(id,visitStatusBindingModel);
        VisitViewModel visitViewModel=this.modelMapper.map(visitServiceModel,VisitViewModel.class);

        return "redirect:/user/ongoing-jobs";
    }

    @PostMapping("/status-change-archive")
    public String statusChangeConfirmArchive(@RequestParam("id")String id, Model model,
                               @Valid @ModelAttribute("visitStatusBindingModel")
                                       VisitStatusBindingModel visitStatusBindingModel,
                               @AuthenticationPrincipal Principal principal){
        VisitServiceModel visitServiceModel = this.visitService.visitStatusChange(id,visitStatusBindingModel);
        VisitViewModel visitViewModel=this.modelMapper.map(visitServiceModel,VisitViewModel.class);

        return "redirect:/user/archive";
    }
}
