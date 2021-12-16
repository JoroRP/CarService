package project.carservice.service.impl;


import project.carservice.model.entity.Mechanic;
import project.carservice.model.service.MechanicServiceModel;
import project.carservice.model.view.MechanicViewModel;
import project.carservice.repository.MechanicRepository;
import project.carservice.service.MechanicService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MechanicServiceImpl implements MechanicService {

    private final ModelMapper modelMapper;
    private final MechanicRepository mechanicRepository;


    @Autowired
    public MechanicServiceImpl(ModelMapper modelMapper, MechanicRepository mechanicRepository) {
        this.modelMapper = modelMapper;
        this.mechanicRepository = mechanicRepository;

    }



    @Override
    public void addMechanicsFromStar() {
        if (this.mechanicRepository.count() == 0) {
            Mechanic mechanic = new Mechanic();
            mechanic.setName("Ivan Stoyanov");
            Mechanic mechanic1 = new Mechanic();
            mechanic1.setName("Dimitar Georgiev");
            Mechanic mechanic2 = new Mechanic();
            mechanic2.setName("Georgi Dimitrov");
            this.mechanicRepository.saveAndFlush(mechanic);
            this.mechanicRepository.saveAndFlush(mechanic1);
            this.mechanicRepository.saveAndFlush(mechanic2);
        }
    }

    @Override
    public List<MechanicViewModel> viewAllMechanics(){
        List<Mechanic> mechanics =this.mechanicRepository.findAll();
        List<MechanicViewModel> mechanicViewModels = new ArrayList<>();
        for (Mechanic mechanic : mechanics) {
            MechanicViewModel mechanicViewModel= new MechanicViewModel();
            mechanicViewModel.setId(mechanic.getId());
            mechanicViewModel.setName(mechanic.getName());
            mechanicViewModels.add(mechanicViewModel);
        }
        return mechanicViewModels;
    }

    @Override
    public MechanicServiceModel searchMechanic(String mechId) {
        Mechanic mechanic = this.mechanicRepository.getOne(mechId);
        MechanicServiceModel mechanicServiceModel =this.modelMapper.map(mechanic,MechanicServiceModel.class);

        return mechanicServiceModel;
    }

    @Override
        public List<Mechanic> getListOfMechanics(){
        List<Mechanic> mechanics =this.mechanicRepository.findAll();
        return mechanics;
    }
}
