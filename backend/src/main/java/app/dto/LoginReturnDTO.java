package app.dto;

import app.model.user.UserType;

public class LoginReturnDTO {
    private UserType type;
    private long id;
    private String email;
    private String jwtToken;

    public LoginReturnDTO(){}

    public LoginReturnDTO(UserType type, long id, String email) {
        this.type = type;
        this.id = id;
        this.email = email;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getType() { return type; }

    public void setType(UserType type) {this.type = type;}

    public long getId() {return id;}

    public void setId(long id) {this.id = id; }
}
