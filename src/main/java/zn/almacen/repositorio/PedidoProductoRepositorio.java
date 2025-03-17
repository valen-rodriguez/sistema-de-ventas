package zn.almacen.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import zn.almacen.modelo.PedidoProducto;

import java.util.List;

public interface PedidoProductoRepositorio extends JpaRepository<PedidoProducto,Integer> {
    List<PedidoProducto> findByPedidoId_Id(Integer pedidoId);
}
