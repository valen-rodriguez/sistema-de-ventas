package zn.almacen.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import zn.almacen.modelo.Cuenta;

public interface CuentaRepositorio extends JpaRepository<Cuenta, Integer> {
    Cuenta findCuentaByMail(String mail);
}
