package app.model.user;
import javax.persistence.*;

@Entity
public class SystemAdmin extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "system_admin_generator")
    @SequenceGenerator(name="system_admin_generator", sequenceName = "system_admin_seq", allocationSize=50, initialValue = 1000)
    private Long id;

    public SystemAdmin() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
