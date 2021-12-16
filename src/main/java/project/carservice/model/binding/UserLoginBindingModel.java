package project.carservice.model.binding;



import javax.validation.constraints.NotBlank;

public class UserLoginBindingModel {
    private String username;
    private String password;


    public UserLoginBindingModel() {
    }

    @NotBlank(message = "Field must not be blank!")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotBlank(message = "Field must not be blank!")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
