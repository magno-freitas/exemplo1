package com.freitas.exemplo1.repository;

import com.freitas.exemplo1.model.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {
    Optional<ServiceType> findByName(String name);
    Optional<ServiceType> findByType(ServiceType.ServiceTypeEnum type);
}
