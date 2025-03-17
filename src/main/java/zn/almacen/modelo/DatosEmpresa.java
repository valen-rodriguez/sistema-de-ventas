package zn.almacen.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatosEmpresa {
    @Id
    private Integer ruc;
    private String nombre;
    private int telefono;
    private String direccion;
    private String razon_social;
}
