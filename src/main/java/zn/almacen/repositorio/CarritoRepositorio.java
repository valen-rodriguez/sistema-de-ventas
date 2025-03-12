package zn.almacen.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import zn.almacen.modelo.Carrito;

public interface CarritoRepositorio extends JpaRepository<Carrito,Integer> {
}
