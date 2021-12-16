package project.carservice.service;

import project.carservice.model.service.CarServiceModel;
import project.carservice.model.view.CarViewModel;

import java.util.List;

public interface CarService {
    public CarViewModel addCarToUser(CarServiceModel carServiceModel, String username);
    public CarServiceModel findCarById(String id);
    public List<CarViewModel> listOfAllCars(String username);
    public List<CarViewModel> listOfCars(String username);
}
