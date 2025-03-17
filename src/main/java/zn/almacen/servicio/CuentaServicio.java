package zn.almacen.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import zn.almacen.modelo.Cuenta;
import zn.almacen.repositorio.CuentaRepositorio;

public class CuentaServicio implements ICuentaServicio{

    @Autowired
    private CuentaRepositorio cuentaRepositorio;

    @Override
    public Cuenta buscarCuentaPorMail(String mail) {
        return cuentaRepositorio.findCuentaByMail(mail);
    }

    @Override
    public void guardarCuenta(Cuenta cuenta) {
        cuentaRepositorio.save(cuenta);
    }
}
