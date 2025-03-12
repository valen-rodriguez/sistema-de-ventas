//package zn.almacen.modelo;
//
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.util.List;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@EqualsAndHashCode
//@ToString
//public class Carrito {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<CarritoProducto> productos;
//}
