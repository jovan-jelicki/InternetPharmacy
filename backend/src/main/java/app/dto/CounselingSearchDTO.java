package app.dto;

import app.model.user.Pharmacist;

public class CounselingSearchDTO {
    private PharmacistPlainDTO pharmacistPlainDTO;
    private PharmacyPlainDTO pharmacyPharmacyDTO;

    public CounselingSearchDTO(Pharmacist pharmacist) {
        pharmacistPlainDTO = new PharmacistPlainDTO(pharmacist);
        pharmacyPharmacyDTO = new PharmacyPlainDTO(pharmacist.getWorkingHours().getPharmacy());
    }

    public CounselingSearchDTO() {}

    public PharmacistPlainDTO getPharmacistPlainDTO() {
        return pharmacistPlainDTO;
    }

    public void setPharmacistPlainDTO(PharmacistPlainDTO pharmacistPlainDTO) {
        this.pharmacistPlainDTO = pharmacistPlainDTO;
    }

    public PharmacyPlainDTO getPharmacyDTO() {
        return pharmacyPharmacyDTO;
    }

    public void setPharmacyDTO(PharmacyPlainDTO pharmacyDTO) {
        this.pharmacyPharmacyDTO = pharmacyDTO;
    }
}
