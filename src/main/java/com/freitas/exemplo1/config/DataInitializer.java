package com.freitas.exemplo1.config;

import com.freitas.exemplo1.model.ServiceType;
import com.freitas.exemplo1.repository.ServiceTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    @Override
    public void run(String... args) {
        // Initialize service types if they don't exist
        for (ServiceType.ServiceTypeEnum type : ServiceType.ServiceTypeEnum.values()) {
            if (serviceTypeRepository.findByType(type).isEmpty()) {
                ServiceType serviceType = new ServiceType();
                serviceType.setType(type);
                serviceType.setName(type.name());
                
                switch (type) {
                    case CONSULTA:
                        serviceType.setDescription("Consulta veterinária de rotina");
                        serviceType.setBasePrice(100.0);
                        serviceType.setDurationMinutes(30);
                        break;
                    case VACINA:
                        serviceType.setDescription("Vacinação");
                        serviceType.setBasePrice(80.0);
                        serviceType.setDurationMinutes(15);
                        break;
                    case BANHO:
                        serviceType.setDescription("Banho e higiene");
                        serviceType.setBasePrice(50.0);
                        serviceType.setDurationMinutes(60);
                        break;
                    case TOSA:
                        serviceType.setDescription("Tosa");
                        serviceType.setBasePrice(70.0);
                        serviceType.setDurationMinutes(60);
                        break;
                    case EXAME:
                        serviceType.setDescription("Exames laboratoriais");
                        serviceType.setBasePrice(150.0);
                        serviceType.setDurationMinutes(45);
                        break;
                    case CIRURGIA:
                        serviceType.setDescription("Procedimentos cirúrgicos");
                        serviceType.setBasePrice(500.0);
                        serviceType.setDurationMinutes(120);
                        break;
                }
                
                serviceTypeRepository.save(serviceType);
            }
        }
    }
}
