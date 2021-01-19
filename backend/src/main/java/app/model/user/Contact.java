package app.model.user;

import javax.persistence.Column;
import javax.persistence.Embeddable;

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