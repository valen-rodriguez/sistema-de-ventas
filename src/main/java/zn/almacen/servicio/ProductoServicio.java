package zn.almacen.servicio;

import zn.almacen.modelo.Producto;
import zn.almacen.repositorio.ProductoRepositorio;

import java.util.List;

public class ProductoServicio implements IProductoServicio{

    private ProductoRepositorio productoRepositorio;

    @Override
    public List<Producto> listarProductos() {
        List<Producto> productos = productoRepositorio.findAll();
        return productos;
    }

    @Override
    public Producto buscarProductoPorId(Integer productoId) {
        Producto producto = productoRepositorio.findById(productoId).orElse(null);
        return producto;
    }

    @Override
    public void agregarProducto(Producto producto) {
        productoRepositorio.save(producto);
    }

    @Override
    public void eliminarProducto(Producto producto) {
        productoRepositorio.delete(producto);
    }
}
