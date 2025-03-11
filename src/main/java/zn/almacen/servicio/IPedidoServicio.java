package zn.almacen.servicio;

import zn.almacen.modelo.Pedido;

import java.util.List;

public interface IPedidoServicio {

    public List<Pedido> listarPedidos();

    public Pedido buscarPedidoPorId(Integer pedidoId);

    public void agregarPedido(Pedido pedido);

    public void eliminarPedido(Pedido pedido);

}
