package app.model.medication;

import javax.persistence.*;

@Entity
public class SideEffect {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "side_effect_generator")
    @SequenceGenerator(name="side_effect_generator", sequenceName = "side_effect_seq", allocationSize=50, initialValue = 1000)
    private Long id;

    @Column
    private String name;

    public SideEffect() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}