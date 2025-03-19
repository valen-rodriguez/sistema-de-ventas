package zn.almacen.servicio;

import zn.almacen.modelo.Producto;

import java.util.List;

public interface IProductoServicio {

    List<Producto> listarProductos();

    Producto buscarProductoPorCodigo(Integer codigo);

    Producto buscarProductoPorId(Integer productoId);

    void agregarProducto(Producto producto);

    void eliminarProducto(Producto producto);

}
