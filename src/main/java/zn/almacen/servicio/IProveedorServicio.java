package zn.almacen.servicio;

import org.springframework.stereotype.Repository;
import zn.almacen.modelo.Proveedor;

import java.util.List;

@Repository
public interface IProveedorServicio {

     List<Proveedor> listarProveedores();

     Proveedor buscarProveedorPorId(Integer proveedor_id);

     void agregarProveedor(Proveedor proveedor);

     void eliminarProveedor(Proveedor proveedor);

}
