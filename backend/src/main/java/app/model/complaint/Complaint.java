package app.model.complaint;

import app.model.user.Patient;

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

   public Complaint() {
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Patient getPatient() {
      return patient;
   }

   public void setPatient(Patient patient) {
      this.patient = patient;
   }

   public String getContent() {
      return content;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public ComplaintType getType() {
      return type;
   }

   public void setType(ComplaintType type) {
      this.type = type;
   }

   public Long getComplaineeId() {
      return complaineeId;
   }

   public void setComplaineeId(Long complaineeId) {
      this.complaineeId = complaineeId;
   }
}