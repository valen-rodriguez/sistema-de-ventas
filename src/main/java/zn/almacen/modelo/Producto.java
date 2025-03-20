package zn.almacen.modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer producto_id;
    private Integer proveedor_id;
    private Integer codigo;
    private String producto;
    private String descripcion;
    private Integer cantidad;
    private Double precio;

    @Transient
    private Double total;
    @Transient
    private Double cantidadIngresada;
}
