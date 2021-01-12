package app.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Dermatologist extends User {

   @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private List<WorkingHours> workingHours;


}