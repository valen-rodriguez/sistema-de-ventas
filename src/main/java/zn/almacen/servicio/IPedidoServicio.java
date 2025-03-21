package zn.almacen.servicio;

import zn.almacen.modelo.Pedido;

import java.util.List;

public interface IPedidoServicio {

    List<Pedido> listarPedidos();

    Pedido buscarPedidoPorId(Integer pedidoId);

    void agregarPedido(Pedido pedido);

    void eliminarPedido(Pedido pedido);

}
