package app.service.impl;

import app.model.pharmacy.LoyaltyScale;
import app.repository.LoyaltyScaleRepository;
import app.service.LoyaltyScaleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
@Service
public class LoyaltyScaleServiceImpl implements LoyaltyScaleService {

    private final LoyaltyScaleRepository loyaltyScaleRepository;

    public LoyaltyScaleServiceImpl(LoyaltyScaleRepository loyaltyScaleRepository) {
        this.loyaltyScaleRepository = loyaltyScaleRepository;
    }

    @Transactional(readOnly = false)
    @Override
    public LoyaltyScale save(LoyaltyScale entity)  {

        return loyaltyScaleRepository.save(entity);
    }

    @Transactional(readOnly = false)
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


    @Transactional(readOnly = false)
    @Override
    public void delete(Long id) {
        loyaltyScaleRepository.deleteById(id);
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
    public boolean existsById(Long id)  {
        return loyaltyScaleRepository.existsById(id);
    }
}
