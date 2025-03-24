package zn.almacen.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zn.almacen.modelo.Cuenta;
import zn.almacen.repositorio.CuentaRepositorio;

import java.util.Optional;

@Service
public class CuentaServicio implements ICuentaServicio{

    @Autowired
    private CuentaRepositorio cuentaRepositorio;

    @Override
    public Cuenta buscarCuentaPorMail(String mail) {
        Optional<Cuenta> cuenta = cuentaRepositorio.findCuentaByMail(mail);
        return cuenta.orElse(null);
    }

    @Override
    public void guardarCuenta(Cuenta cuenta) {
        cuentaRepositorio.save(cuenta);
    }

    @Override
    public Cuenta buscarCuentaPorId(Integer id) {
        return cuentaRepositorio.findById(id).orElse(null);
    }
}
