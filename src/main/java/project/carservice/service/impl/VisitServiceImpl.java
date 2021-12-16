package project.carservice.service.impl;

import project.carservice.error.VisitNotFoundException;
import project.carservice.model.binding.VisitStatusBindingModel;
import project.carservice.model.entity.*;
import project.carservice.model.service.CarServiceModel;
import project.carservice.model.service.UserDetailsServiceModel;
import project.carservice.model.service.VisitServiceModel;
import project.carservice.model.view.VisitViewModel;
import project.carservice.repository.CarRepository;
import project.carservice.repository.MechanicRepository;
import project.carservice.repository.VisitRepository;
import project.carservice.service.CarService;
import project.carservice.service.MechanicService;
import project.carservice.service.UserService;
import project.carservice.service.VisitService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class VisitServiceImpl implements VisitService {
    private final ModelMapper modelMapper;
    private final VisitRepository visitRepository;
    private final CarService carService;
    private final UserService userService;
    private final CarRepository carRepository;
    private final MechanicRepository mechanicRepository;
    private final MechanicService mechanicService;

    @Autowired
    public VisitServiceImpl(ModelMapper modelMapper, VisitRepository visitRepository, CarService carService, UserService userService, CarRepository carRepository, MechanicRepository mechanicRepository, MechanicService mechanicService) {
        this.modelMapper = modelMapper;
        this.visitRepository = visitRepository;
        this.carService = carService;
        this.userService = userService;
        this.carRepository = carRepository;
        this.mechanicRepository = mechanicRepository;
        this.mechanicService = mechanicService;
    }

    @Override
    public VisitViewModel addVisit(VisitServiceModel visitServiceModel, String carId, String username) {
        Visit visit = new Visit();
        visit = this.modelMapper.map(visitServiceModel, Visit.class);
        Progress progress = Progress.AWAITING_VEHICLE;
        UserDetailsServiceModel userDetailsServiceModel = userService.findUserByUsername(username);
        UserEntity user = this.modelMapper.map(userDetailsServiceModel, UserEntity.class);
        CarServiceModel carServiceModel = this.carService.findCarById(carId);
        Car car = new Car();
        car.setUser(user);
        car.setYear(carServiceModel.getYear());
        car.setLicensePlate(carServiceModel.getLicencePlate());
        car.setModel(carServiceModel.getModel());
        car.setId(carServiceModel.getId());
        visit.setUser(user);
        visit.setCar(car);
        visit.setProgress(progress);
        visit.setPrice(0.00);
        List<Mechanic> mechanics = this.mechanicService.getListOfMechanics();
        Random random = new Random();
        int randomMechanicId = random.nextInt(mechanics.size() - 1);
        Mechanic mechanic = mechanics.get(randomMechanicId);
        visit.setMechanic(mechanic);
        Visit visitToView = this.visitRepository.saveAndFlush(visit);
        VisitViewModel visitViewModel = this.modelMapper.map(visitToView, VisitViewModel.class);

        return visitViewModel;
    }

    @Override
    public VisitServiceModel findVisitById(String id) {
        Visit visit = this.visitRepository.getOne(id);
        if (visit == null) {
            throw new VisitNotFoundException(visit.getId());
        }
        VisitServiceModel visitServiceModel = new VisitServiceModel();

        visitServiceModel.setDate(visit.getDate());
        visitServiceModel.setPrice(visit.getPrice());
        visitServiceModel.setProgress(visit.getProgress());
        visitServiceModel.setCar(visit.getCar());
        visitServiceModel.setPrice(visit.getPrice());
        visitServiceModel.setMechanic(visit.getMechanic());
        return visitServiceModel;
    }

    @Override
    public List<VisitViewModel> findAllVisits(String id) {
        List<Visit> visits = this.visitRepository.findAllByCarId(id);
        List<VisitViewModel> visitViewModels = new ArrayList<>();
        for (Visit visit : visits) {
            VisitViewModel visitViewModel = new VisitViewModel();
            visitViewModel.setId(visit.getId());
            visitViewModel.setDate(visit.getDate().toString());
            visitViewModel.setCarId(visit.getCar().getId());
            visitViewModel.setPrice(visit.getPrice());
            visitViewModel.setProgress(visit.getProgress());
            visitViewModel.setModel(visit.getCar().getModel());
            visitViewModel.setMechanicName(visit.getMechanic().getName());
            visitViewModels.add(visitViewModel);
        }

        return visitViewModels;
    }


    @Override
    public List<VisitViewModel> findVisits(String id) {
        List<Visit> visits = this.visitRepository.findAll();
        List<VisitViewModel> visitViewModels = new ArrayList<>();

        for (Visit visit : visits) {

                VisitViewModel visitViewModel = new VisitViewModel();
                visitViewModel.setId(visit.getId());
                visitViewModel.setDate(visit.getDate().toString());

                visitViewModel.setCarId(visit.getCar().getId());
                visitViewModel.setModel(visit.getCar().getModel());
                visitViewModel.setYear(visit.getCar().getYear());
                visitViewModel.setLicensePlate(visit.getCar().getLicensePlate());

                visitViewModel.setPrice(visit.getPrice());
                visitViewModel.setProgress(visit.getProgress());
                visitViewModel.setModel(visit.getCar().getModel());
                visitViewModel.setMechanicName(visit.getMechanic().getName());
                visitViewModels.add(visitViewModel);


        }

        return visitViewModels;
    }

    @Override
    public VisitServiceModel visitStatusChange(String id, VisitStatusBindingModel visitStatusBindingModel) {
        Visit visit = this.visitRepository.getOne(id);


        if (visit == null) {
            throw new VisitNotFoundException(visit.getId());
        }
        VisitServiceModel visitServiceModel = new VisitServiceModel();

        if(visit.getProgress().toString().equals("AWAITING_VEHICLE")){
            visit.setProgress(Progress.CHECKING_THE_VEHICLE);
            visit.setPrice(0.00);
        }
        else if(visit.getProgress().toString().equals("CHECKING_THE_VEHICLE")){
            visit.setProgress(Progress.WAITING_FOR_PARTS);
           visit.setPrice(visitStatusBindingModel.getPrice());
           visit.setParts(visitStatusBindingModel.getParts());

        }
        else if(visit.getProgress().toString().equals("WAITING_FOR_PARTS")){
            visit.setProgress(Progress.IN_REPAIR);

        }
        else if(visit.getProgress().toString().equals("IN_REPAIR")){
            visit.setProgress(Progress.READY);

        }
        else if(visit.getProgress().toString().equals("READY")){
            visit.setProgress(Progress.FINISHED);

        }

        visitServiceModel.setDate(visit.getDate());
        visitServiceModel.setCar(visit.getCar());
        visitServiceModel.setMechanic(visit.getMechanic());
        visitServiceModel.setProgress(visit.getProgress());
        visitServiceModel.setPrice(visit.getPrice());
        visitServiceModel.setParts(visit.getParts());

        return visitServiceModel;
    }

}

