package zn.almacen.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zn.almacen.modelo.PedidoProducto;

import java.util.List;

@Repository
public interface PedidoProductoRepositorio extends JpaRepository<PedidoProducto,Integer> {
    List<PedidoProducto> findByPedidoId_Id(Integer pedidoId);
}
