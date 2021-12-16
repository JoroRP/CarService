package project.carservice.web;


import project.carservice.model.binding.CarAddBindingModel;
import project.carservice.model.service.CarServiceModel;
import project.carservice.model.view.CarViewModel;
import project.carservice.service.CarService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/car")
public class CarController {
    private final CarService carService;
    private final ModelMapper modelMapper;

    @Autowired
    public CarController(CarService carService, ModelMapper modelMapper) {
        this.carService = carService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    String addCar(@RequestParam("username")String username, Model model){
        model.addAttribute("carAddBindingModel",new CarAddBindingModel());
        model.addAttribute("username",username);
        return "add-car";
    }
    @PostMapping("/add")
    ModelAndView addConfirmCar(@RequestParam("username")String username, @Valid @ModelAttribute("carAddBindingModel")
            CarAddBindingModel carAddBindingModel,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes, ModelAndView modelAndView){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("carAddBindingModel",carAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.dayAddBindingModel",
                    bindingResult);
            modelAndView.setViewName("add-car");
            return modelAndView;
        }
        CarViewModel carViewModel=this.carService.addCarToUser(this.modelMapper.map(carAddBindingModel, CarServiceModel.class),username);
        modelAndView.setViewName("view-car");
        modelAndView.addObject("car",carViewModel);

        return modelAndView;
    }
    @GetMapping("/view-car")
    String viewCars(@RequestParam("id")String id,Model model) {
        CarServiceModel carServiceModel=this.carService.findCarById(id);
        CarViewModel carViewModel=this.modelMapper.map(carServiceModel,CarViewModel.class);
        carViewModel.setModel(carServiceModel.getModel());
        carViewModel.setYear(carServiceModel.getYear().toString());
        carViewModel.setLicencePlate(carServiceModel.getLicencePlate());
        model.addAttribute("car",carViewModel);
        return "view-car";
    }
}
