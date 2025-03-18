package zn.almacen.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zn.almacen.modelo.Cliente;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Integer> {
}
