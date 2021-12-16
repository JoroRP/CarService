package project.carservice.service.impl;

import project.carservice.model.entity.Car;
import project.carservice.model.entity.UserEntity;
import project.carservice.model.service.CarServiceModel;
import project.carservice.model.service.UserDetailsServiceModel;
import project.carservice.model.view.CarViewModel;
import project.carservice.repository.CarRepository;
import project.carservice.service.CarService;
import project.carservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CarServiceImpl implements CarService {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final CarRepository carRepository;

    @Autowired
    public CarServiceImpl(UserService userService, ModelMapper modelMapper, CarRepository carRepository) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.carRepository = carRepository;
    }

    @Override
    public CarViewModel addCarToUser(CarServiceModel carServiceModel, String username) {
        Car car=this.modelMapper.map(carServiceModel,Car.class);
        UserDetailsServiceModel userDetailsServiceModel=this.userService.findUserByUsername(username);
        UserEntity userEntity=this.modelMapper.map(userDetailsServiceModel,UserEntity.class);
        car.setUser(userEntity);
        car.setModel(carServiceModel.getModel());
        car.setLicensePlate(carServiceModel.getLicencePlate());
        car.setYear(carServiceModel.getYear());
        Car carToView=this.carRepository.saveAndFlush(car);
        CarViewModel carViewModel=this.modelMapper.map(carToView,CarViewModel.class);
        return carViewModel;
    }

    @Override
    public CarServiceModel findCarById(String id) {
        Car car = this.carRepository.getOne(id);
        CarServiceModel carServiceModel=this.modelMapper.map(car,CarServiceModel.class);
        carServiceModel.setModel(car.getModel());
        carServiceModel.setYear(car.getYear());
        carServiceModel.setLicencePlate(car.getLicensePlate());
        carServiceModel.setUsername(car.getUser().getUsername());
        return carServiceModel;
    }

    @Override
    public List<CarViewModel> listOfAllCars(String username) {
        List<CarViewModel>cars=new ArrayList<>();
        List<Car>carsEntity=this.carRepository.findAllByUser_Username(username);
        carsEntity.forEach(car->{
            CarViewModel carViewModel=new CarViewModel();
            carViewModel.setId(car.getId());
            carViewModel.setModel(car.getModel());
            carViewModel.setYear(car.getYear().toString());
            carViewModel.setLicencePlate(car.getLicensePlate());
            cars.add(carViewModel);
        });
        return cars;
    }

    @Override
    public List<CarViewModel> listOfCars(String username) {
        List<CarViewModel>cars=new ArrayList<>();
        List<Car>carsEntity=this.carRepository.findAll();
        carsEntity.forEach(car->{
            CarViewModel carViewModel=new CarViewModel();
            carViewModel.setId(car.getId());
            carViewModel.setModel(car.getModel());
            carViewModel.setYear(car.getYear().toString());
            carViewModel.setLicencePlate(car.getLicensePlate());
            cars.add(carViewModel);
        });
        return cars;
    }
}
