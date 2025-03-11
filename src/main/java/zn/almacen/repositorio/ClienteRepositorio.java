package zn.almacen.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import zn.almacen.modelo.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Integer> {
}
