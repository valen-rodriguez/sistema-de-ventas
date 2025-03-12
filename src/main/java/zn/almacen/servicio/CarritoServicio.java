package zn.almacen.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import zn.almacen.modelo.Carrito;
import zn.almacen.modelo.CarritoProducto;
import zn.almacen.modelo.Producto;
import zn.almacen.repositorio.CarritoProductoRepositorio;
import zn.almacen.repositorio.CarritoRepositorio;
import zn.almacen.repositorio.ProductoRepositorio;

import java.util.List;

public class CarritoServicio implements ICarritoServicio{

    @Autowired
    private CarritoRepositorio carritoRepositorio;

    @Autowired
    private CarritoProductoRepositorio carritoProductoRepositorio;

    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Override
    public List<CarritoProducto> listarProductos(Integer carritoId) {
        return carritoProductoRepositorio.findByCarritoId(carritoId);
    }

    @Override
    public Carrito buscarCarritoPorId(Integer id) {
        return carritoRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
    }

    @Override
    public void agregarProducto(Integer carritoId, Integer productoId, int cantidad) {
        Carrito carrito = buscarCarritoPorId(carritoId);
        Producto producto = productoRepositorio.findById(productoId).orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        CarritoProducto carritoProducto = new CarritoProducto();
        carritoProducto.setCarrito(carrito);
        carritoProducto.setProducto(producto);
        carritoProducto.setCantidad(cantidad);

        carritoProductoRepositorio.save(carritoProducto);
    }

    @Override
    public void eliminarProducto(Integer carritoProductoId) {
        carritoProductoRepositorio.deleteById(carritoProductoId);
    }
}
