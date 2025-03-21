package zn.almacen.servicio;

import zn.almacen.modelo.PedidoProducto;

import java.util.List;

public interface IPedidoProductoServicio {

    List<PedidoProducto> listarPedidoProductos();

    List<PedidoProducto> buscarPorPedido(Integer pedidoId);

    void agregarPedidoProducto(PedidoProducto pedidoProducto);

    void eliminarPedidoProducto(PedidoProducto pedidoProducto);


}
