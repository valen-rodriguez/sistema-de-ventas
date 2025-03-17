package zn.almacen.servicio;

import org.springframework.stereotype.Service;
import zn.almacen.modelo.PedidoProducto;
import zn.almacen.repositorio.PedidoProductoRepositorio;

import java.util.List;

@Service
public class PedidoProductoServicio implements IPedidoProductoServicio{

    PedidoProductoRepositorio pedidoProductoRepositorio;

    @Override
    public List<PedidoProducto> listarPedidoProductos() {
        List<PedidoProducto> pedidoProductos = pedidoProductoRepositorio.findAll();
        return pedidoProductos;
    }

    @Override
    public List<PedidoProducto> buscarPorPedido(Integer pedidoId) {
        List<PedidoProducto> pedidoProductos = pedidoProductoRepositorio.findByPedidoId_Id(pedidoId);
        return pedidoProductos;
    }

    @Override
    public void agregarPedidoProducto(PedidoProducto pedidoProducto) {

    }

    @Override
    public void eliminarPedidoProducto(PedidoProducto pedidoProducto) {

    }
}
