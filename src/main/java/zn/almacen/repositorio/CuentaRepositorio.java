package zn.almacen.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import zn.almacen.modelo.Cuenta;

import java.util.Optional;

public interface CuentaRepositorio extends JpaRepository<Cuenta, Integer> {
    Optional<Cuenta> findCuentaByMail(String mail);
}
