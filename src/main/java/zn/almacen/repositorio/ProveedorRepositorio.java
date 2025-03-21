package zn.almacen.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import zn.almacen.modelo.Proveedor;

public interface ProveedorRepositorio extends JpaRepository<Proveedor, Integer> {
}
