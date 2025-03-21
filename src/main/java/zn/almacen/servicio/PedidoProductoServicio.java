package zn.almacen.servicio;

import org.springframework.stereotype.Service;
import zn.almacen.modelo.PedidoProducto;
import zn.almacen.repositorio.PedidoProductoRepositorio;

import java.util.List;

@Service
public class PedidoProductoServicio implements IPedidoProductoServicio{

    private final PedidoProductoRepositorio pedidoProductoRepositorio;

    public PedidoProductoServicio(PedidoProductoRepositorio pedidoProductoRepositorio){
        this.pedidoProductoRepositorio = pedidoProductoRepositorio;
    }

    @Override
    public List<PedidoProducto> listarPedidoProductos() {
        return pedidoProductoRepositorio.findAll();
    }

    @Override
    public List<PedidoProducto> buscarPorPedido(Integer pedidoId) {
        return pedidoProductoRepositorio.findByPedidoId_Id(pedidoId);
    }

    @Override
    public void agregarPedidoProducto(PedidoProducto pedidoProducto) {
        pedidoProductoRepositorio.save(pedidoProducto);
    }

    @Override
    public void eliminarPedidoProducto(PedidoProducto pedidoProducto) {
        pedidoProductoRepositorio.delete(pedidoProducto);
    }
}
