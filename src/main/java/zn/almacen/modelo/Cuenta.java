package zn.almacen.modelo;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Cuenta {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer cuenta_id;
    private String nombre;
    private String mail;
    private String password;
}
