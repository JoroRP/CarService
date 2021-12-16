package project.carservice.service;

import project.carservice.model.binding.VisitStatusBindingModel;
import project.carservice.model.service.VisitServiceModel;
import project.carservice.model.view.VisitViewModel;

import java.util.List;

public interface VisitService {
    public VisitViewModel addVisit(VisitServiceModel visitServiceModel, String carId, String userId);
    public VisitServiceModel findVisitById(String id);
    public List<VisitViewModel> findAllVisits(String id);

    public List<VisitViewModel> findVisits(String id);
    public VisitServiceModel visitStatusChange(String id, VisitStatusBindingModel visitStatusBindingModel);
}
