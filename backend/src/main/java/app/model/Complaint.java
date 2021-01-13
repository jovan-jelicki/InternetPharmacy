package app.model;

import javax.persistence.*;

@Entity
public class Complaint {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne
   @JoinColumn
   private Patient patient;

   @Column
   private String content;

   @Enumerated(EnumType.ORDINAL)
   private ComplaintType type;

   @Column
   private Long complaineeId;

}