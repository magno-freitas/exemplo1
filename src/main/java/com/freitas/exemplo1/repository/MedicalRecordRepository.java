package com.freitas.exemplo1.repository;

import com.freitas.exemplo1.model.MedicalRecord;
import com.freitas.exemplo1.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findByPet(Pet pet);
    List<MedicalRecord> findByPetOrderByRecordDateDesc(Pet pet);
    List<MedicalRecord> findByPetAndRecordDateBetween(Pet pet, LocalDateTime startDate, LocalDateTime endDate);
}
