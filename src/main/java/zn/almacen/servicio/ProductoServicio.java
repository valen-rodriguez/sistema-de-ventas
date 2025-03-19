package zn.almacen.servicio;

import org.springframework.stereotype.Service;
import zn.almacen.modelo.Producto;
import zn.almacen.repositorio.ProductoRepositorio;

import java.util.List;

@Service
public class ProductoServicio implements IProductoServicio{

    private final ProductoRepositorio productoRepositorio;

    public ProductoServicio(ProductoRepositorio productoRepositorio) {
        this.productoRepositorio = productoRepositorio;
    }

    @Override
    public List<Producto> listarProductos() {
        return productoRepositorio.findAll();
    }

    @Override
    public Producto buscarProductoPorCodigo(Integer codigo) {
        Producto producto = productoRepositorio.findProductoByCodigo(codigo).orElse(null);
        return producto;
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
