package com.freitas.exemplo1.controller;

import com.freitas.exemplo1.model.MedicalRecord;
import com.freitas.exemplo1.model.User;
import com.freitas.exemplo1.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/medical-records")
public class MedicalRecordController {
    
    @Autowired
    private MedicalRecordService medicalRecordService;

    @PostMapping("/pet/{petId}")
    public ResponseEntity<MedicalRecord> createMedicalRecord(
            @PathVariable Long petId,
            @RequestBody MedicalRecord medicalRecord,
            @AuthenticationPrincipal User currentUser) {
        MedicalRecord created = medicalRecordService.createMedicalRecord(medicalRecord, petId, currentUser);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecord> getMedicalRecord(@PathVariable Long id) {
        return ResponseEntity.ok(medicalRecordService.getMedicalRecord(id));
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<MedicalRecord>> getPetMedicalHistory(@PathVariable Long petId) {
        return ResponseEntity.ok(medicalRecordService.getPetMedicalHistory(petId));
    }

    @GetMapping("/pet/{petId}/between")
    public ResponseEntity<List<MedicalRecord>> getPetMedicalHistoryBetweenDates(
            @PathVariable Long petId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(medicalRecordService.getPetMedicalHistoryBetweenDates(petId, startDate, endDate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(
            @PathVariable Long id,
            @RequestBody MedicalRecord medicalRecord) {
        return ResponseEntity.ok(medicalRecordService.updateMedicalRecord(id, medicalRecord));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable Long id) {
        medicalRecordService.deleteMedicalRecord(id);
        return ResponseEntity.noContent().build();
    }
}
