package zn.almacen.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import zn.almacen.modelo.Producto;

import java.util.List;

public interface ProductoRepositorio extends JpaRepository<Producto,Integer> {
    List<Producto> findByCategoria(String categoria);
}
