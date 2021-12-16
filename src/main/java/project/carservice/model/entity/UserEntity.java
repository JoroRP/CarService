package project.carservice.model.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "users")
@Transactional
public class UserEntity extends BaseEntity{
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private List<RoleEntity> roles;
    private List<Car> cars;

    public UserEntity() {
    }

    @Column(name="username", nullable = false, unique = true)
    @Length(min=4, message = "The username must be more than 4 symbols.")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @Column(name="first_name", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Column(name="last_name", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @Column(name="password", nullable = false)
    @Length(min=5, message = "The password must be more than 5 symbols.")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Email(message = "Enter valid email")
    @NotNull
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name="user_id")
    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "user_id")
    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
