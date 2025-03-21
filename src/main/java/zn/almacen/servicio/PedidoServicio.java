package zn.almacen.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zn.almacen.modelo.Pedido;
import zn.almacen.repositorio.PedidoRepositorio;

import java.util.List;

@Service
public class PedidoServicio implements IPedidoServicio{

    @Autowired
    private PedidoRepositorio pedidoRepositorio;

    @Override
    public List<Pedido> listarPedidos(){
        return pedidoRepositorio.findAll();
    }

    @Override
    public Pedido buscarPedidoPorId(Integer pedidoId) {
        return pedidoRepositorio.findById(pedidoId).orElse(null);
    }

    @Override
    public void agregarPedido(Pedido pedido) {
        pedidoRepositorio.save(pedido);
    }

    @Override
    public void eliminarPedido(Pedido pedido) {
        pedidoRepositorio.delete(pedido);
    }
}
