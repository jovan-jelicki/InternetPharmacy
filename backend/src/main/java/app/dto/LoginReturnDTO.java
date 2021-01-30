package app.dto;

import app.model.user.UserType;

public class LoginReturnDTO {
    private UserType type;
    private long id;

    public LoginReturnDTO(){}

    public LoginReturnDTO(UserType type, long id) {
        this.type = type;
        this.id = id;
    }

    public UserType getType() { return type; }

    public void setType(UserType type) {this.type = type;}

    public long getId() {return id;}

    public void setId(long id) {this.id = id; }
}
