package zn.almacen.servicio;

import zn.almacen.modelo.Producto;

import java.util.List;

public interface IProductoServicio {

    public List<Producto> listarProductos();

    public Producto buscarProductoPorId(Integer productoId);

    public void agregarProducto(Producto producto);

    public void eliminarProducto(Producto producto);

}
