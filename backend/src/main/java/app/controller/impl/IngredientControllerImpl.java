package app.controller.impl;

import app.controller.IngredientController;
import app.model.medication.Ingredient;
import app.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/ingredients")
public class IngredientControllerImpl implements IngredientController {
    public final IngredientService ingredientService;

    @Autowired
    public IngredientControllerImpl(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @Override
    @PostMapping
    public ResponseEntity<Ingredient> save(@RequestBody Ingredient entity) {
        return new ResponseEntity<>(ingredientService.save(entity), HttpStatus.CREATED);
    }

    @Override
    @PutMapping
    public ResponseEntity<Ingredient> update(@RequestBody Ingredient entity) {
        if(!ingredientService.existsById(entity.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(ingredientService.save(entity), HttpStatus.CREATED);
    }

    @Override
    @GetMapping
    public ResponseEntity<Collection<Ingredient>> read() {
        return new ResponseEntity<>(ingredientService.read(), HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Ingredient>> read(@PathVariable Long id) {
        return new ResponseEntity<>(ingredientService.read(id), HttpStatus.OK);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if(!ingredientService.existsById(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        ingredientService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
