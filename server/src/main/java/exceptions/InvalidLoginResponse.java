package exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidLoginResponse {
    private String username = "Invalid Username";
    private String password = "Invalid Password";

    public InvalidLoginResponse() {
        this.username = username;
        this.password = password;
    }
}
