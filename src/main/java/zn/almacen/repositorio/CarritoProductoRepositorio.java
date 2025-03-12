package zn.almacen.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import zn.almacen.modelo.CarritoProducto;

import java.util.List;

public interface CarritoProductoRepositorio extends JpaRepository<CarritoProducto,Integer> {
    List<CarritoProducto> findByCarritoId(Integer carritoId);
}
