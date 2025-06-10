package com.freitas.exemplo1.service;

import com.freitas.exemplo1.model.ServiceType;
import com.freitas.exemplo1.repository.ServiceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ServiceTypeService {

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    @Transactional(readOnly = true)
    public List<ServiceType> getAllServiceTypes() {
        return serviceTypeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ServiceType getServiceTypeById(Long id) {
        return serviceTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ServiceType not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public ServiceType getServiceTypeByName(String name) {
        return serviceTypeRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("ServiceType not found with name: " + name));
    }

    @Transactional
    public ServiceType createServiceType(ServiceType serviceType) {
        return serviceTypeRepository.save(serviceType);
    }

    @Transactional
    public ServiceType updateServiceType(Long id, ServiceType serviceType) {
        if (!serviceTypeRepository.existsById(id)) {
            throw new EntityNotFoundException("ServiceType not found with id: " + id);
        }
        serviceType.setId(id);
        return serviceTypeRepository.save(serviceType);
    }

    @Transactional
    public void deleteServiceType(Long id) {
        if (!serviceTypeRepository.existsById(id)) {
            throw new EntityNotFoundException("ServiceType not found with id: " + id);
        }
        serviceTypeRepository.deleteById(id);
    }
}
