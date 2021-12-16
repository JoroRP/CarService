package project.carservice.init;


import project.carservice.repository.RoleRepository;
import project.carservice.service.MechanicService;
import project.carservice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataInit implements CommandLineRunner {
    private final MechanicService mechanicService;
    private final UserService userService;

    public DataInit(MechanicService mechanicService, UserService userService, RoleRepository roleRepository) {
        this.mechanicService = mechanicService;
        this.userService = userService;

    }

    @Override
    public void run(String... args) throws Exception {
        this.userService.createAdmin();
        this.mechanicService.addMechanicsFromStar();

    }
}
