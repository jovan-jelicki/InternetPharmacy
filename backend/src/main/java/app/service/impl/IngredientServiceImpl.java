package app.service.impl;

import app.model.Ingredient;
import app.repository.IngredientRepository;
import app.service.IngredientService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Ingredient save(Ingredient entity) {
        return ingredientRepository.save(entity);
    }

    @Override
    public Collection<Ingredient> read() {
        return ingredientRepository.findAll();
    }

    @Override
    public Optional<Ingredient> read(Long id) {
        return ingredientRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        ingredientRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return ingredientRepository.existsById(id);
    }
}
