package project.carservice.service.impl;


import project.carservice.model.entity.UserEntity;
import project.carservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private static final Logger LOGGER= LoggerFactory.getLogger(UserDetailsService.class);

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

   /* @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userOpt=userRepository.findByUsername(username);
        LOGGER.debug("Trying to load user {}. Successful? {}",username,userOpt.isPresent());
        return userOpt.map(this::map).orElseThrow(()->new UsernameNotFoundException("No such user "+username));
    }
    private User map(UserEntity user){
        List<GrantedAuthority> authorities=user.getRoles().stream().map(r->new SimpleGrantedAuthority(r.getRole())).
                collect(Collectors.toList());
        User result=new User(user.getUsername(),user.getPassword()!=null?user.getPassword(): "",authorities);
        if(user.getPassword()==null){
            result.eraseCredentials();
        }
        return result;
    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with name" + username + "was not found."));

        return map(userEntity);
    }

    private UserDetails map(UserEntity user){
        List<GrantedAuthority> authorities=user.getRoles().stream().map(r->new SimpleGrantedAuthority(r.getRole())).
                collect(Collectors.toList());
        User result=new User(user.getUsername(),user.getPassword()!=null?user.getPassword(): "",authorities);
        if(user.getPassword()==null){
            result.eraseCredentials();
        }
        return result;
    }
}
