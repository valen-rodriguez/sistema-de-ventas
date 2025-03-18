package zn.almacen.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zn.almacen.modelo.Pedido;

@Repository
public interface PedidoRepositorio extends JpaRepository<Pedido, Integer> {
}
