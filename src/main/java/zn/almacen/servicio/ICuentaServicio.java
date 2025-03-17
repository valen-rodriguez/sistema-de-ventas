package zn.almacen.servicio;

import zn.almacen.modelo.Cuenta;

public interface ICuentaServicio {
    public Cuenta buscarCuentaPorMail(String mail);

    public void guardarCuenta(Cuenta cuenta);
}
