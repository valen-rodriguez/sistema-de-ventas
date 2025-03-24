package zn.almacen.servicio;

import zn.almacen.modelo.Cuenta;

public interface ICuentaServicio {
     Cuenta buscarCuentaPorMail(String mail);

     void guardarCuenta(Cuenta cuenta);

     Cuenta buscarCuentaPorId(Integer id);
}
