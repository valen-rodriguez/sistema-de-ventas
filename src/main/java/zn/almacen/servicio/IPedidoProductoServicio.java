package zn.almacen.servicio;

import zn.almacen.modelo.PedidoProducto;

import java.util.List;

public interface IPedidoProductoServicio {

    public List<PedidoProducto> listarPedidoProductos();

    public List<PedidoProducto> buscarPorPedido(Integer pedidoId);

    public void agregarPedidoProducto(PedidoProducto pedidoProducto);

    public void eliminarPedidoProducto(PedidoProducto pedidoProducto);


}
