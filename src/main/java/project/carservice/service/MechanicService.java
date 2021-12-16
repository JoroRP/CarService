package project.carservice.service;

import project.carservice.model.entity.Mechanic;
import project.carservice.model.service.MechanicServiceModel;
import project.carservice.model.view.MechanicViewModel;

import java.util.List;

public interface MechanicService {
    public MechanicServiceModel searchMechanic(String mechId);
    public void addMechanicsFromStar();
    public List<MechanicViewModel> viewAllMechanics();
    public List<Mechanic> getListOfMechanics();
}
