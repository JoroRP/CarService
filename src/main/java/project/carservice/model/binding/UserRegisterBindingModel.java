package project.carservice.model.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserRegisterBindingModel {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String confirmPassword;
    private String email;


    public UserRegisterBindingModel() {
    }

    @NotBlank(message = "Field must not be blank!")
    @Length(min=4,message = "Username must be more than 4 symbols")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotBlank(message = "Field must not be blank!")
    @Length(min=5,message = "Password must be more than 5 symbols")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @NotBlank(message = "Field must not be blank!")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotBlank(message = "Field must not be blank!")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Email(message = "Enter valid email")
    @NotBlank(message = "Field must not be blank!")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
