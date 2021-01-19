package app.repository;

import app.model.medication.SideEffect;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SideEffectRepository extends JpaRepository<SideEffect, Long>{
}
