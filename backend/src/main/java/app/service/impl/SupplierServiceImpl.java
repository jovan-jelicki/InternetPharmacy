package app.service.impl;

import app.model.user.Patient;
import app.model.user.Supplier;
import app.repository.PatientRepository;
import app.repository.SupplierRepository;
import app.service.SupplierService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService{
    private SupplierRepository supplierRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Supplier save(Supplier entity)  {
        return supplierRepository.save(entity);
    }

    @Override
    public Collection<Supplier> read()  {
        return supplierRepository.findAll();
    }


    @Override
    public Optional<Supplier> read(Long id){return supplierRepository.findById(id); }
    @Override
    public void delete(Long id) {supplierRepository.deleteById(id);}

    @Override
    public boolean existsById(Long id)  {
        return supplierRepository.existsById(id);
    }

    @Override
    public Supplier findByEmailAndPassword(String email, String password) { return supplierRepository.findByEmailAndPassword(email, password);}

}
