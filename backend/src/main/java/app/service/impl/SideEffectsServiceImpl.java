package app.service.impl;

import app.model.medication.SideEffect;
import app.model.pharmacy.Pharmacy;
import app.repository.SideEffectRepository;
import app.service.SideEffectsService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
@Service
public class SideEffectsServiceImpl implements SideEffectsService {
    private final SideEffectRepository sideEffectRepository;

    public SideEffectsServiceImpl(SideEffectRepository sideEffectRepository) {
        this.sideEffectRepository = sideEffectRepository;
    }

    @Override
    public SideEffect save(SideEffect entity) {
        return sideEffectRepository.save(entity);
    }

    @Override
    public Collection<SideEffect> read() { return sideEffectRepository.findAll(); }

    @Override
    public Optional<SideEffect> read(Long id)  {
        return sideEffectRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        sideEffectRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id)  {
        return sideEffectRepository.existsById(id);
    }
}
