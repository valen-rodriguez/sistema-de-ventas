package zn.almacen.modelo;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pedido_id;
    private Integer cliente_id;
    private Integer cuenta_id;
    private Double precio_total;

    //para ver como se pagó
    private String forma_de_pago;

    private LocalDateTime fecha;
}
