package project.carservice.web;


import project.carservice.model.binding.VisitAddBindingModel;
import project.carservice.model.service.VisitServiceModel;
import project.carservice.model.view.VisitViewModel;
import project.carservice.service.CarService;
import project.carservice.service.VisitService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;


@Controller
@RequestMapping("/visit")
public class VisitController {
    private final VisitService visitService;
    private final ModelMapper modelMapper;
    private final CarService carService;

    @Autowired
    public VisitController(VisitService visitService, ModelMapper modelMapper, CarService carService) {
        this.visitService = visitService;
        this.modelMapper = modelMapper;
        this.carService = carService;
    }

    @GetMapping("/add")
    public String addVisit(@RequestParam("id")String id, Model model){
        model.addAttribute("visitAddBindingModel",new VisitAddBindingModel());
        model.addAttribute("id",id);
        return "add-visit";
    }
   @PostMapping("/add")
    public String addVisitConfirm(@RequestParam("id") String id,
                                 @Valid @ModelAttribute("visitAddBindingModel")
                                             VisitAddBindingModel visitAddBindingModel,
                                 BindingResult bindingResult, HttpSession httpSession, RedirectAttributes redirectAttributes, Model model,@AuthenticationPrincipal Principal principal) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("visitAddBindingModel", visitAddBindingModel);
            redirectAttributes.
                    addFlashAttribute("org.springframework.validation.BindingResult.visitAddBindingModel",
                            bindingResult);
            return "add-visit";
        }
        VisitServiceModel visitServiceModel =this.modelMapper.map(visitAddBindingModel, VisitServiceModel.class);
        VisitViewModel visitViewModel=this.visitService.addVisit(visitServiceModel,id,principal.getName());
        model.addAttribute("id",visitViewModel.getId());
        model.addAttribute("carId",visitViewModel.getCarId());
        model.addAttribute("visit",visitViewModel);
        model.addAttribute("srtg",visitViewModel);
        return "view-visit";
    }
     @GetMapping("/view-visit")
     public String viewVisit(@RequestParam("id")String id,Model model){
       VisitServiceModel visitServiceModel = this.visitService.findVisitById(id);
        VisitViewModel visitViewModel=this.modelMapper.map(visitServiceModel,VisitViewModel.class);
        model.addAttribute("date", visitViewModel.getDate());
        model.addAttribute("price",visitViewModel.getPrice());
        model.addAttribute("progress", visitViewModel.getProgress());
        model.addAttribute("model", visitServiceModel.getCar().getModel());
        model.addAttribute("mechanic",visitViewModel.getMechanicName());
        return "view-visit";
    }

    @GetMapping("/view-all-visits")
     public String viewAllVisits(@RequestParam("id")String id,Model model, @AuthenticationPrincipal Principal principal){
        model.addAttribute("username",principal.getName());
        model.addAttribute("visitList",this.visitService.findAllVisits(id));

        return "view-all-visits";
    }

}
