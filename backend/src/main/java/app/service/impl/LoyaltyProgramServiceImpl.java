package app.service.impl;
import app.model.pharmacy.LoyaltyProgram;
import app.repository.LoyaltyProgramRepository;
import app.repository.LoyaltyScaleRepository;
import app.service.LoyaltyProgramService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class LoyaltyProgramServiceImpl  implements LoyaltyProgramService {
    private final LoyaltyProgramRepository loyaltyProgramRepository;

    public LoyaltyProgramServiceImpl(LoyaltyProgramRepository loyaltyProgramRepository) {
        this.loyaltyProgramRepository = loyaltyProgramRepository;
    }

    @Override
    public LoyaltyProgram save(LoyaltyProgram entity)   {
        return loyaltyProgramRepository.save(entity);
    }

    @Override
    public Collection<LoyaltyProgram> read()  {
        return loyaltyProgramRepository.findAll();
    }

    @Override
    public Optional<LoyaltyProgram> read(Long id) { return loyaltyProgramRepository.findById(id);  }

    @Override
    public void delete(Long id) { loyaltyProgramRepository.deleteById(id); }

    @Override
    public boolean existsById(Long id) { return loyaltyProgramRepository.existsById(id); }

    @Override
    public Boolean saveProgram(LoyaltyProgram entity) {
        if(this.read().size()!=0) {
            for (LoyaltyProgram loyaltyProgram : this.read()) {
                    loyaltyProgram.setAppointmentPoints(entity.getAppointmentPoints());
                    loyaltyProgram.setConsultingPoints(entity.getConsultingPoints());
                    this.save(loyaltyProgram);
            }
        }else{
            this.save(entity);
        }
        return true;
    }
}
