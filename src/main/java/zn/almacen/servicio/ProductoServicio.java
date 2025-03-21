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
        return productoRepositorio.findProductoByCodigo(codigo).orElse(null);
    }

    @Override
    public Producto buscarProductoPorId(Integer productoId) {
        return productoRepositorio.findById(productoId).orElse(null);
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
