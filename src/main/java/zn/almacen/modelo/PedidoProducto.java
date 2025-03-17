package zn.almacen.modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PedidoProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pedido_producto_id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido_id;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto_id;

    private int cantidad;
}
