package app.model.user;

import app.model.time.WorkingHours;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Dermatologist extends User {

   @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private List<WorkingHours> workingHours;

   public Dermatologist() {
   }

   public List<WorkingHours> getWorkingHours() {
      return workingHours;
   }

   public void setWorkingHours(List<WorkingHours> workingHours) {
      this.workingHours = workingHours;
   }
}