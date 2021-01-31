package app.model.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.util.ArrayList;
import java.util.Collection;


@MappedSuperclass
public abstract class User implements UserDetails {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private Credentials credentials;

    private Contact contact;

    @Enumerated(EnumType.ORDINAL)
    private UserType userType;

    public User() {}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<Authority> authorities = new ArrayList<>();
        Authority authority = new Authority();
        authority.setName(this.userType.name());
        authorities.add(authority);
        return authorities;
    }
    @Override
    public String getPassword() {
        return this.getCredentials().getPassword();
    }

    @Override
    public String getUsername() {
        return this.getCredentials().getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Credentials getCredentials() {return credentials; }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}