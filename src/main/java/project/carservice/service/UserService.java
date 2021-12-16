package project.carservice.service;

import project.carservice.model.entity.UserEntity;
import project.carservice.model.service.UserDetailsServiceModel;
import project.carservice.model.service.UserServiceModel;
import project.carservice.model.view.UserViewModel;

public interface UserService {

    public boolean isAdmin(String username);
    public void createAdmin();
    public boolean existsUser(String username);
    public UserEntity getOrCreateUser(UserServiceModel userServiceModel);
    public void createAndLoginUser(UserServiceModel userServiceModel);
    public void loginUser(String username,String password);
    public UserDetailsServiceModel findUserByUsername(String username);
    public UserViewModel getUser(String username);
}
