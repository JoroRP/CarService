package project.carservice.service.impl;

import project.carservice.error.PasswordNotCorrectException;
import project.carservice.error.UserNotFoundException;
import project.carservice.model.entity.RoleEntity;
import project.carservice.model.entity.UserEntity;
import project.carservice.model.service.UserDetailsServiceModel;
import project.carservice.model.service.UserServiceModel;
import project.carservice.model.view.UserViewModel;
import project.carservice.repository.UserRepository;
import project.carservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder, @Qualifier("userDetailsServiceImpl")
                                   UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }


    @Override
    public boolean existsUser(String username) {
        Objects.requireNonNull(username);
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public UserEntity getOrCreateUser(UserServiceModel userServiceModel) {
        Objects.requireNonNull(userServiceModel.getPassword());
        Optional<UserEntity> userEntityOpt = userRepository.findByUsername(userServiceModel.getUsername());
        return userEntityOpt.orElseGet(() -> createUser(userServiceModel));
    }

    @Override
    public void createAndLoginUser(UserServiceModel userServiceModel) {
        UserEntity newUser = createUser(userServiceModel);
        UserDetails userDetails = userDetailsService.loadUserByUsername(newUser.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userServiceModel.getPassword(),
                userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public void loginUser(String username, String password) {
        UserDetailsServiceModel userDetailsServiceModel = this.findUserByUsername(username);
       // if (!passwordEncoder.matches(password,userDetailsServiceModel.getPassword())) {
       //     throw new PasswordNotCorrectException();
       // }
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, password,
                userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }


    @Override
    public boolean isAdmin(String username) {
        List<String> roles = new ArrayList<>();
        UserEntity userEntity = this.userRepository.findByUsername(username).orElse(null);
        if (userEntity == null) {
            throw new UserNotFoundException(username);
        }
        for (RoleEntity role : userEntity.getRoles()) {
            roles.add(role.getRole());
        }
        if (roles.contains("ADMIN")) {
            return true;
        }
        return false;
    }

    @Override
    public void createAdmin() {
        if (this.userRepository.count() == 0) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername("admin");
            userEntity.setPassword(passwordEncoder.encode("admin"));
            List<RoleEntity> roles = new ArrayList<>();
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setRole("ADMIN");
            roles.add(roleEntity);
            userEntity.setRoles(roles);

            userEntity.setFirstName("Admin");
            userEntity.setLastName("Admin");
            userEntity.setEmail("admin@email");

            this.userRepository.saveAndFlush(userEntity);
        }
    }


    private UserEntity createUser(UserServiceModel userServiceModel) {
        UserEntity userEntity = new UserEntity();
        LOGGER.info("Creating a new user with username [GDPR].");
        userEntity = this.createUserWithRoles(userServiceModel, "USER");
        return userRepository.save(userEntity);
    }

    private UserEntity createUserWithRoles(UserServiceModel userServiceModel, String role) {
        UserEntity userEntity = this.modelMapper.map(userServiceModel, UserEntity.class);
        if (userServiceModel.getPassword() != null) {
            userEntity.setPassword(passwordEncoder.encode(userServiceModel.getPassword()));
        } else {
            throw new PasswordNotCorrectException();
        }
        RoleEntity userRole = new RoleEntity();
        userRole.setRole(role);
        userEntity.setRoles(List.of(userRole));
        return userEntity;
    }

    @Override
    public UserDetailsServiceModel findUserByUsername(String username) {
        UserEntity userEntity = this.userRepository.findByUsername(username).orElse(null);
        if (userEntity != null) {
            UserDetailsServiceModel userDetailsServiceModel = this.modelMapper.map(userEntity, UserDetailsServiceModel.class);
            userDetailsServiceModel.setUsername(userEntity.getUsername());
            userDetailsServiceModel.setFirstName(userEntity.getFirstName());
            userDetailsServiceModel.setLastName(userEntity.getLastName());
            userDetailsServiceModel.setEmail(userEntity.getEmail());
            userDetailsServiceModel.setPassword(userEntity.getPassword());
            return userDetailsServiceModel;
        }
        throw new UserNotFoundException(username);
    }

    @Override
    public UserViewModel getUser(String username) {
        UserViewModel userViewModel = new UserViewModel();
        UserEntity userEntity = this.userRepository.findByUsername(username).orElse(null);
        if (userEntity == null) {
            throw new UserNotFoundException(username);
        }
        userViewModel.setUsername(userEntity.getUsername());
        userViewModel.setFirstName(userEntity.getFirstName());
        userViewModel.setLastName(userEntity.getLastName());
        userViewModel.setRoles(userEntity.getRoles());
        return userViewModel;
    }

}
