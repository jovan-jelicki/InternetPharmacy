package app.service.impl;

import app.model.pharmacy.LoyaltyScale;
import app.repository.LoyaltyScaleRepository;
import app.service.LoyaltyScaleService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class LoyaltyScaleServiceImpl implements LoyaltyScaleService {

    private final LoyaltyScaleRepository loyaltyScaleRepository;

    public LoyaltyScaleServiceImpl(LoyaltyScaleRepository loyaltyScaleRepository) {
        this.loyaltyScaleRepository = loyaltyScaleRepository;
    }

    @Override
    public LoyaltyScale save(LoyaltyScale entity)  {

        return loyaltyScaleRepository.save(entity);
    }

    public Boolean editLoyaltyScale(LoyaltyScale entity){
        for(LoyaltyScale loyaltyScale : this.read()){
            if(loyaltyScale.getCategory()==entity.getCategory()){
                loyaltyScale.setMaxPoints(entity.getMaxPoints());
                loyaltyScale.setMinPoints(entity.getMinPoints());
                loyaltyScale.setDiscount(entity.getDiscount());

                this.save(loyaltyScale);
            }
        }

        return true;
    }

    @Override
    public Collection<LoyaltyScale> read()  {
        return loyaltyScaleRepository.findAll();
    }

    @Override
    public Optional<LoyaltyScale> read(Long id)  {
        return loyaltyScaleRepository.findById(id);
    }
    @Override
    public void delete(Long id) {
        loyaltyScaleRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id)  {
        return loyaltyScaleRepository.existsById(id);
    }
}
