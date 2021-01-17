package app.model;

import javax.persistence.*;

@Embeddable
public class Contact {
   private Address address;

   @Column
   private String phoneNumber;

   public Contact() {}

   public Address getAddress() {
      return address;
   }

   public void setAddress(Address address) {
      this.address = address;
   }

   public String getPhoneNumber() {
      return phoneNumber;
   }

   public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
   }
}