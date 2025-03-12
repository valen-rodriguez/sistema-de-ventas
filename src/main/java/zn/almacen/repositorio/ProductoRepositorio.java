package zn.almacen.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import zn.almacen.modelo.Producto;

public interface ProductoRepositorio extends JpaRepository<Producto,Integer> {
}
