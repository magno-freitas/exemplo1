package com.freitas.exemplo1.controller;

import com.freitas.exemplo1.model.ServiceType;
import com.freitas.exemplo1.service.ServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceTypeController {

    @Autowired
    private ServiceTypeService serviceTypeService;

    @GetMapping
    public ResponseEntity<List<ServiceType>> getAllServices() {
        return ResponseEntity.ok(serviceTypeService.getAllServiceTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceType> getServiceById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceTypeService.getServiceTypeById(id));
    }

    @PostMapping
    public ResponseEntity<ServiceType> createService(@Valid @RequestBody ServiceType serviceType) {
        return ResponseEntity.ok(serviceTypeService.createServiceType(serviceType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceType> updateService(@PathVariable Long id, @Valid @RequestBody ServiceType serviceType) {
        return ResponseEntity.ok(serviceTypeService.updateServiceType(id, serviceType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        serviceTypeService.deleteServiceType(id);
        return ResponseEntity.ok().build();
    }
}
