package zn.almacen.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import zn.almacen.modelo.Producto;
import zn.almacen.modelo.Proveedor;

import java.util.Optional;

public interface ProveedorRepositorio extends JpaRepository<Proveedor, Integer> {
    Optional<Proveedor> findProductoByNombre(String nombre);
    Optional<Proveedor> findProductoByRuc(int ruc);
}
