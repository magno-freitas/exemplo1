package com.freitas.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import com.freitas.model.Produto;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Produto, Long> {
    // Aqui você pode adicionar métodos personalizados, se necessário
    // Exemplo: List<Produto> findByNome(String nome);

}
