package app.model.user;

import app.model.time.WorkingHours;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Dermatologist extends User {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dermatologist_generator")
   @SequenceGenerator(name="dermatologist_generator", sequenceName = "dermatologist_seq", allocationSize=50, initialValue = 1000)
   private Long id;

   @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private List<WorkingHours> workingHours;

   public Dermatologist() {
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public List<WorkingHours> getWorkingHours() {
      return workingHours;
   }

   public void setWorkingHours(List<WorkingHours> workingHours) {
      this.workingHours = workingHours;
   }



}