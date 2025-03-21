package zn.almacen.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zn.almacen.modelo.Proveedor;
import zn.almacen.repositorio.ProveedorRepositorio;

import java.util.List;

@Service
public class ProveedorServicio implements IProveedorServicio{

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;

    @Override
    public List<Proveedor> listarProveedores() {
        return proveedorRepositorio.findAll();
    }

    @Override
    public Proveedor buscarProveedorPorId(Integer proveedor_id) {
        return proveedorRepositorio.findById(proveedor_id).orElse(null);
    }

    @Override
    public Proveedor buscarProveedorPorNombre(String nombre) {
        return proveedorRepositorio.findProductoByNombre(nombre).orElse(null);
    }

    @Override
    public void agregarProveedor(Proveedor proveedor) {
        proveedorRepositorio.save(proveedor);
    }

    @Override
    public void eliminarProveedor(Proveedor proveedor) {
        proveedorRepositorio.delete(proveedor);
    }
}
