//package zn.almacen.modelo;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@EqualsAndHashCode
//@ToString
//public class CarritoProducto {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @ManyToOne
//    @JoinColumn(name = "carrito_id", nullable = false)
//    private Carrito carrito;
//
//    @ManyToOne
//    @JoinColumn(name = "producto_id", nullable = false)
//    private Producto producto;
//
//
//    private int cantidad;
//}
