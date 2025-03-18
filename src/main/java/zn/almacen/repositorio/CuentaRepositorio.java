package zn.almacen.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zn.almacen.modelo.Cuenta;

import java.util.Optional;

@Repository
public interface CuentaRepositorio extends JpaRepository<Cuenta, Integer> {
    Optional<Cuenta> findCuentaByMail(String mail);
}
