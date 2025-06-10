package com.freitas.exemplo1.repository;

import com.freitas.exemplo1.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByEmail(String email);
    Client findByCpf(String cpf);
}
