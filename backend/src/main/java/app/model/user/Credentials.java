package app.model.user;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Credentials {
    @Column(unique = true, nullable = false, updatable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    public Credentials() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}