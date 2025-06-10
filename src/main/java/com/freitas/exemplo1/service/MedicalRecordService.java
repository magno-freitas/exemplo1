package com.freitas.exemplo1.service;

import com.freitas.exemplo1.model.MedicalRecord;
import com.freitas.exemplo1.model.Pet;
import com.freitas.exemplo1.model.User;
import com.freitas.exemplo1.repository.MedicalRecordRepository;
import com.freitas.exemplo1.repository.PetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MedicalRecordService {
    
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    
    @Autowired
    private PetRepository petRepository;

    @Transactional
    public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord, Long petId, User currentUser) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new EntityNotFoundException("Pet not found with id: " + petId));
        
        medicalRecord.setPet(pet);
        medicalRecord.setRecordDate(LocalDateTime.now());
        medicalRecord.setCreatedBy(currentUser);
        
        return medicalRecordRepository.save(medicalRecord);
    }

    public MedicalRecord getMedicalRecord(Long id) {
        return medicalRecordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medical record not found with id: " + id));
    }

    public List<MedicalRecord> getPetMedicalHistory(Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new EntityNotFoundException("Pet not found with id: " + petId));
        
        return medicalRecordRepository.findByPetOrderByRecordDateDesc(pet);
    }

    public List<MedicalRecord> getPetMedicalHistoryBetweenDates(Long petId, LocalDateTime startDate, LocalDateTime endDate) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new EntityNotFoundException("Pet not found with id: " + petId));
        
        return medicalRecordRepository.findByPetAndRecordDateBetween(pet, startDate, endDate);
    }

    @Transactional
    public MedicalRecord updateMedicalRecord(Long id, MedicalRecord updatedRecord) {
        MedicalRecord existingRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medical record not found with id: " + id));
        
        existingRecord.setWeight(updatedRecord.getWeight());
        existingRecord.setTemperature(updatedRecord.getTemperature());
        existingRecord.setSymptoms(updatedRecord.getSymptoms());
        existingRecord.setDiagnosis(updatedRecord.getDiagnosis());
        existingRecord.setTreatment(updatedRecord.getTreatment());
        existingRecord.setPrescriptions(updatedRecord.getPrescriptions());
        existingRecord.setNotes(updatedRecord.getNotes());
        
        return medicalRecordRepository.save(existingRecord);
    }

    @Transactional
    public void deleteMedicalRecord(Long id) {
        if (!medicalRecordRepository.existsById(id)) {
            throw new EntityNotFoundException("Medical record not found with id: " + id);
        }
        medicalRecordRepository.deleteById(id);
    }
}
